package workFithFile;

import FactoryPattern.ClientFactory;
import FactoryPattern.ConcreteFactoryA.AnotherFileOpener;
import FactoryPattern.ConcreteFactoryA.TxtFileOpener;
import FactoryPattern.ConcreteFactoryA.XmlFileOpener;
import FactoryPattern.ConcreteFactoryB.HtmlFileOpener;
import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import FactoryPattern.FileHelper;
import application.Controller;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import utils.Dialogs;
import utils.FileUtils;

public class FileCell extends TreeCell<OpenAnotherFile> {
  private static FileTreeItem itemToMove = null;

  private static Boolean isCopy = null;

  private final MenuItem pasteItem = new MenuItem("Paste");
  private final MenuItem copyItem = new MenuItem("Copy");
  private final MenuItem deleteItem = new MenuItem("Delete");
  private final MenuItem newFileItem = new MenuItem("New File");
  private final MenuItem openFileItem = new MenuItem("Open");

  private final ContextMenu contextMenu = new ContextMenu();
  private TextField textField;

  public FileCell() {
    super();
    newFileItem.setOnAction(event -> createNewFile());
    copyItem.setOnAction(event -> setupForMovingFile(true));
    deleteItem.setOnAction(event -> deleteFile());
    pasteItem.setOnAction(event ->
    {
      try {
        moveFile();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    });
    openFileItem.setOnAction(event -> {
      try {
        openFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    contextMenu.getItems().addAll(copyItem, deleteItem);
  }

  private void moveFile() throws MalformedURLException {
    OpenAnotherFile fileToMove = itemToMove.getValue();
    OpenAnotherFile folderToMove = getTreeItem().getValue();
    OpenAnotherFile destinationFile = FileUtils.getResultOfMovement(fileToMove, folderToMove);

    if (destinationFile.isExist()) {
      Character answer = Dialogs.choiceIfExistsFile();
      switch (answer) {
        case 'n' -> {
          destinationFile =
              FileUtils.getFileWithSuffix(destinationFile.getAbsolutePath());
        }
        default -> {
          return;
        }
      }
    }

    if (isCopy) {
      try {
        fileToMove.copy(fileToMove, destinationFile);
      } catch (IOException e) {
        error(e, "moving");
        return;
      }
    } else {
      itemToMove.removeThisFromParent();
      try {
        fileToMove.cut(destinationFile);
      } catch (IOException e) {
        error(e, "moving");
        return;
      }
      itemToMove = null;
      isCopy = null;
    }

    FileTreeItem item = (FileTreeItem) getTreeItem();
    item.addNewChild(destinationFile);
    updateItem();
  }

  private void error(IOException e, String operation) {
    Dialogs.errorDialog("Error with " + operation + " file");
    e.printStackTrace();
  }

  private void deleteFile() {
    FileHelper fileToRemove = getItem();

    if (itemToMove != null && fileToRemove.isEqualOrParent(itemToMove.getValue())) {
      itemToMove = null;
      isCopy = null;
    }

    try {
      if (!fileToRemove.isFile()) {
        if (fileToRemove.getFile().getAbsolutePath().charAt(0) == 'D') {
          if (Controller.DfolderList.containsKey(fileToRemove.getFile().getName())) {
            Controller.DfolderList.remove(fileToRemove.getFile().getName());
          }
        } else {
          if (Controller.CfolderList.containsKey(fileToRemove.getFile().getName())) {
            Controller.CfolderList.remove(fileToRemove.getFile().getName());
          }
        }
      }
      fileToRemove.delete();
      FileTreeItem item = (FileTreeItem) getTreeItem();
      item.removeThisFromParent();
    } catch (IOException e) {
      error(e, "deleting");
    }
  }

  private void openFile() throws Exception {
    FileHelper currentFile = getTreeItem().getValue();
    String extension = FileUtils.getFileExtension(currentFile.getFile());

    ClientFactory clientFactory;
    String path = currentFile.getAbsolutePath();
    switch (extension) {
      case "html" -> {
        clientFactory = new ClientFactory(new HtmlFileOpener(), path);
      }
      case "xml" -> {
        clientFactory = new ClientFactory(new XmlFileOpener(), path);
      }
      case "txt" -> {
        clientFactory = new ClientFactory(new TxtFileOpener(), path);
      }
      default -> {
        clientFactory = new ClientFactory(new AnotherFileOpener(), path);
      }
    }
  }

  private void createNewFolder() {
    OpenAnotherFile currentFolder = getTreeItem().getValue();
    try {
      OpenAnotherFile newFolder = currentFolder.newFolder();
      FileTreeItem treeItem = (FileTreeItem) getTreeItem();
      treeItem.addNewChild(newFolder);
      treeItem.setExpanded(true);
    } catch (IOException e) {
    }
  }

  private void createNewFile() {
    OpenAnotherFile currentFolder = getTreeItem().getValue();
    try {
      OpenAnotherFile newFile = currentFolder.newFile();
      FileTreeItem treeItem = (FileTreeItem) getTreeItem();
      treeItem.addNewChild(newFile);
      treeItem.setExpanded(true);
    } catch (IOException e) {
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();

    if (textField == null) {
      createTextField();
    } else {
      textField.setText(getText());
    }

    setText(null);
    setGraphic(textField);
    textField.selectAll();
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    updateItem(getItem(), isEmpty());
  }


  private void commitEdit(String newValue) {
    OpenAnotherFile fileExtension = getItem();
    if (newValue == null || newValue.isEmpty()) {
      cancelEdit();
    } else {
      OpenAnotherFile destinationFile = FileUtils.resultOfRename(fileExtension, newValue);
      if (destinationFile.isExist()) {
        Character answer = Dialogs.choiceIfExistsFile();
        switch (answer) {
          case 'p' -> {
            try {
              destinationFile.delete();
            } catch (IOException e) {
              error(e, "replacing");
              return;
            }
          }
          case 'n' -> {
            destinationFile = FileUtils.getFileWithSuffix(destinationFile.getAbsolutePath());
          }
          default -> {
            return;
          }
        }
      }

      try {
        String previousName = fileExtension.getName();
        fileExtension.rename(destinationFile);
        updateItem();
        super.commitEdit(fileExtension);
      } catch (IOException e) {
        error(e, "renaming");
      }

    }

  }

  @Override
  protected void updateItem(OpenAnotherFile item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      setText(getTreeItem().getValue().toString());
      setGraphic(getTreeItem().getGraphic());
      setupMenu();
    }
  }


  private void setupMenu() {
    File file = getTreeItem().getValue().getFile();
    ObservableList<MenuItem> menuItems = contextMenu.getItems();

    if (!file.isFile()) {
      if (menuItems.size() == 2) {
        menuItems.remove(openFileItem);
        menuItems.add(newFileItem);
      }
//      if (menuItems.size() == 2) {
//        menuItems.add(newFileItem);
//      }

      if (menuItems.size() == 3 || menuItems.size() == 4) {
        menuItems.removeAll(pasteItem);
      }

      if (itemToMove != null) {
        pasteItem.setText("Paste (" + itemToMove.getValue().toString() + ")");
        menuItems.add(pasteItem);
      }
    } else {
      if (menuItems.size() < 3) {
        menuItems.add(openFileItem);
      }
      if (menuItems.size() > 2) {
        menuItems.removeAll(pasteItem, newFileItem);
      }
    }

    setContextMenu(contextMenu);
  }

  private void updateScreen() {
    updateTreeView(getTreeView());
  }

  private void updateItem() {
    updateTreeItem(getTreeItem());
  }

  private void setupForMovingFile(Boolean isCopy) {
    FileCell.isCopy = isCopy;
    itemToMove = (FileTreeItem) getTreeItem();
    updateScreen();
  }

  private void createTextField() {
    textField = new TextField(getText());
    textField.setOnKeyReleased(t ->
    {
      if (t.getCode() == KeyCode.ENTER) {
        commitEdit(textField.getText());
      } else if (t.getCode() == KeyCode.ESCAPE) {
        cancelEdit();
      }
    });
  }

}
