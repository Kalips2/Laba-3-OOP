package application;

import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import workFithFile.FileSearcher;
import workFithFile.FileCell;
import workFithFile.FileTreeItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import FactoryPattern.FileHelper;


public class Controller {
  private TreeView<OpenAnotherFile> DfileView;
  private FileTreeItem DfileTreeItem;
  private TreeView<OpenAnotherFile> CfileView;
  private FileTreeItem CfileTreeItem;
  private static final int SCROLL_PANE_PREF_WIDTH = 350;
  private static final int VIEW_ITEM_PREF_HEIGHT = 28;
  public static HashMap<String, String> DfolderList = new HashMap<>();
  public static HashMap<String, String> CfolderList = new HashMap<>();

  @FXML
  private AnchorPane mainAnchorPane;

  @FXML
  private ScrollPane CfileScrollPane;

  @FXML
  private ScrollPane DfileScrollPane;

  @FXML
  private Button CSearchButton;

  @FXML
  private TextField CSearchTextField;

  @FXML
  private TextField DSearchTextField;

  @FXML
  private Button DSearchButton;

  @FXML
  private ScrollPane DfileSearchScrollPane;

  @FXML
  private MenuButton DFolderMenu;

  @FXML
  private ScrollPane CfileSearchScrollPane;

  @FXML
  private MenuButton CFolderMenu;

  @FXML
  private Button AboutButton;

  @FXML
  void initialize() throws MalformedURLException {
    DfolderList.clear();
    CfolderList.clear();
    initializeC();
    initializeD();
    setDFolderMenu();
    setCFolderMenu();
    ;
    DSearchButton.setOnAction(actionEvent ->
    {
      String DFolderToSearch = DFolderMenu.getText();
      searchInD(DFolderToSearch);
    });
    CSearchButton.setOnAction(actionEvent ->
    {
      String CFolderToSearch = CFolderMenu.getText();
      searchInC(CFolderToSearch);
    });
    AboutButton.setOnAction(actionEvent -> {
      try {
        showMessage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private static void showMessage() throws IOException {
    File file = new File("src/main/resources/text.txt");
    StringBuffer buffer = new StringBuffer();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    while(bufferedReader.ready()) {
      buffer.append(bufferedReader.readLine() + "\n");
    }
    TextArea htmlCodeTextArea = new TextArea(buffer.toString());
    Scene htmlCodeScene = new Scene(htmlCodeTextArea);
    Stage htmlCodeWindow = new Stage();
    htmlCodeWindow.setTitle("Про програму");
    htmlCodeWindow.setScene(htmlCodeScene);
    htmlCodeWindow.setWidth(800);
    htmlCodeWindow.setHeight(700);
    htmlCodeWindow.setX(600);
    htmlCodeWindow.setY(50);
    htmlCodeWindow.show();
  }

  private void initializeC() {
    OpenAnotherFile CfileRoot = new OpenAnotherFile("C:/");
    CfileTreeItem = new FileTreeItem(CfileRoot);
    CfileView = new TreeView<>(CfileTreeItem);
    CfileView.setShowRoot(false);
    CfileView.setEditable(true);
    CfileView.setCellFactory(param -> new FileCell());
    CfileScrollPane.setContent(CfileView);
    CfileScrollPane.setFitToHeight(true);
    CfileScrollPane.setFitToWidth(true);
  }

  private void initializeD() {
    OpenAnotherFile DfileRoot = new OpenAnotherFile("D:/");
    DfileTreeItem = new FileTreeItem(DfileRoot);
    DfileView = new TreeView<>(DfileTreeItem);
    DfileView.setShowRoot(false);
    DfileView.setEditable(true);
    DfileView.setCellFactory(param -> new FileCell());
    DfileScrollPane.setContent(DfileView);
    DfileScrollPane.setFitToHeight(true);
    DfileScrollPane.setFitToWidth(true);
  }

  private void searchInD(String DFolderToSearch) {
    String limitation = DSearchTextField.getText();
    System.out.println(new File(DfolderList.get(DFolderToSearch)) + " " + limitation);
    search(DfileSearchScrollPane, new OpenAnotherFile(new File(DfolderList.get(DFolderToSearch))),
        limitation);
  }

  private void setDFolderMenu() {
    for (String folder : DfolderList.keySet()) {
      if (folder.equals("")) {
        continue;
      }
      MenuItem item = new MenuItem(folder);
      item.setOnAction(actionEvent -> {
            DFolderMenu.setText(folder);
          }
      );
      DFolderMenu.getItems().add(item);
    }
  }

  private void searchInC(String CFolderToSearch) {
    String limitation = CSearchTextField.getText();
    search(CfileSearchScrollPane, new OpenAnotherFile(new File(CfolderList.get(CFolderToSearch))),
        limitation);
  }

  private void setCFolderMenu() {
    for (String folder : CfolderList.keySet()) {
      if (folder.equals("")) {
        continue;
      }
      MenuItem item = new MenuItem(folder);
      item.setOnAction(actionEvent -> {
            CFolderMenu.setText(folder);
          }
      );
      CFolderMenu.getItems().add(item);
    }
  }

  private void search(ScrollPane fileSearchScrollPane, OpenAnotherFile fileRootSearch,
                      String limitation) {
    FileTreeItem fileTreeItemSearch = new FileTreeItem(fileRootSearch);
    FileSearcher fileSearcher = new FileSearcher(fileTreeItemSearch, limitation);
    final FlowPane container = new FlowPane();
    container.setPrefWidth(SCROLL_PANE_PREF_WIDTH);

    for (FileTreeItem fileItem : fileSearcher.search()) {
      TreeView<OpenAnotherFile> fileSearchView = new TreeView<>(fileItem);
      fileSearchView.setShowRoot(true);
      fileSearchView.setEditable(true);
      fileSearchView.setCellFactory(param -> new FileCell());
      fileSearchView.setPrefWidth(SCROLL_PANE_PREF_WIDTH);
      fileSearchView.setPrefHeight(VIEW_ITEM_PREF_HEIGHT);
      container.getChildren().add(fileSearchView);
    }

    fileSearchScrollPane.setContent(container);
    fileSearchScrollPane.contentProperty();
    fileSearchScrollPane.setFitToHeight(true);
    fileSearchScrollPane.setFitToWidth(true);
  }
}