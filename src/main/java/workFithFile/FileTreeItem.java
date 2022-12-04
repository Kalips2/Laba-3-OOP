package workFithFile;

import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import application.Controller;
import java.io.File;
import java.net.MalformedURLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import FactoryPattern.FileHelper;
import utils.Images;

public class FileTreeItem extends TreeItem<OpenAnotherFile> {
  private Boolean hasLoadedChilds;

  public FileTreeItem(OpenAnotherFile value) {
    super(value);
    hasLoadedChilds = false;
    if (!isLeaf()) {
      if (getValue().getAbsolutePath().charAt(0) == 'D') {
        Controller.DfolderList.put(getValue().getName(), getValue().getAbsolutePath());
      } else {
        Controller.CfolderList.put(getValue().getName(), getValue().getAbsolutePath());
      }
    }
  }

  public FileTreeItem(OpenAnotherFile value, Node graphic) {
    super(value, graphic);
    hasLoadedChilds = false;
    if (!isLeaf()) {
      if (getValue().getAbsolutePath().charAt(0) == 'D') {
        Controller.DfolderList.put(getValue().getName(), getValue().getAbsolutePath());
      } else {
        Controller.CfolderList.put(getValue().getName(), getValue().getAbsolutePath());
      }
    }
  }

  @Override
  public boolean isLeaf() {
    return getValue().isFile();
  }

  @Override
  public ObservableList<TreeItem<OpenAnotherFile>> getChildren() {
    if (!hasLoadedChilds) {
      hasLoadedChilds = true;
      try {
        addChildes();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      try {
        setGraphic(Images.openFolder());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

    }
    return super.getChildren();
  }

  public void addNewChild(OpenAnotherFile file) throws MalformedURLException {
    if (hasLoadedChilds) {
      FileTreeItem newFolderItem = createItem(file);
      super.getChildren().addAll(newFolderItem);
    }
  }

  public void removeChildItem(OpenAnotherFile file) {
    TreeItem<OpenAnotherFile> itemToRemove = null;
    for (TreeItem<OpenAnotherFile> fileExtensionTreeItem : getChildren()) {
      String fileName = fileExtensionTreeItem.getValue().getFile().getName();
      if (file.getName().equals(fileName)) {
        itemToRemove = fileExtensionTreeItem;
        break;
      }
    }

    if (itemToRemove != null) {
      getChildren().removeAll(itemToRemove);
    }
  }

  public void removeThisFromParent() {
    getParent().getChildren().removeAll(this);
  }

  private void addChildes() throws MalformedURLException {
    if (!isLeaf()) {
      File[] childs = getValue().getFile().listFiles();
      ObservableList<TreeItem<OpenAnotherFile>> childList = FXCollections.observableArrayList();

      if (childs != null) {
        for (File child : childs) {
          childList.add(createItem(new OpenAnotherFile(child)));
        }
      }
      super.getChildren().addAll(childList);
    }
  }

  private FileTreeItem createItem(OpenAnotherFile child) throws MalformedURLException {
    ImageView image;
    if (!child.isFile()) {
      image = Images.closedFolder();
    } else {
      image = Images.file();
    }

    FileTreeItem item = new FileTreeItem(child, image);

    item.expandedProperty().addListener((observable, oldValue, newValue) ->
    {

      if (newValue) {
        try {
          item.setGraphic(Images.openFolder());
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      } else {
        try {
          item.setGraphic(Images.closedFolder());
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
    });
    return item;
  }
}