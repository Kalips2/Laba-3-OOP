package controllers;

import FactoryPattern.OpenFileAbstractFactory;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class OpenHtml implements OpenFileAbstractFactory {
  private File file;

  OpenHtml(File file) {
    this.file = file;
  }

  public void open() {
    HtmlEditorController htmlEditorController = null;
    try {
      htmlEditorController = new HtmlEditorController(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    TextArea htmlCodeTextArea = CodeWindowSetUp(htmlEditorController);
    TextArea listOfTagsTextArea = TagsWindowSetUp(htmlEditorController);
    replacementWindowSetUp(htmlEditorController, htmlCodeTextArea, listOfTagsTextArea);
  }

  private TextArea CodeWindowSetUp(HtmlEditorController htmlEditorController) {
    TextArea htmlCodeTextArea = new TextArea(htmlEditorController.getHtmlCode());
    Scene htmlCodeScene = new Scene(htmlCodeTextArea);
    Stage htmlCodeWindow = new Stage();
    htmlCodeWindow.setTitle(file.getName());
    htmlCodeWindow.setScene(htmlCodeScene);
    htmlCodeWindow.setWidth(800);
    htmlCodeWindow.setHeight(700);
    htmlCodeWindow.setX(600);
    htmlCodeWindow.setY(50);
    htmlCodeWindow.show();
    return htmlCodeTextArea;
  }

  private static TextArea TagsWindowSetUp(HtmlEditorController htmlEditorController) {
    TextArea listOfTagsTextArea = new TextArea(htmlEditorController.getlistOfTags());
    Scene listOfTagsScene = new Scene(listOfTagsTextArea);
    Stage listOfTagsWindow = new Stage();
    listOfTagsWindow.setTitle("Список тегів");
    listOfTagsWindow.setScene(listOfTagsScene);
    listOfTagsWindow.setWidth(500);
    listOfTagsWindow.setHeight(400);
    listOfTagsWindow.setX(100);
    listOfTagsWindow.setY(150);
    listOfTagsWindow.show();
    return listOfTagsTextArea;
  }

  private static void replacementWindowSetUp(HtmlEditorController htmlEditorController,
                                             TextArea htmlCodeTextArea,
                                             TextArea listOfTagsTextArea) {
    TextField previousTag = new TextField();
    previousTag.setPromptText("теперешній тег");
    previousTag.setEditable(true);
    previousTag.setPrefWidth(200);
    TextField newTag = new TextField();
    newTag.setPromptText("новий тег");
    newTag.setPrefWidth(200);
    newTag.setEditable(true);
    Button changeTagName = new Button();
    changeTagName.setText("Змінити на");
    HtmlEditorController finalHtmlEditorController = htmlEditorController;
    changeTagName.setOnAction(actionEvent ->
    {
      String previousTagText = previousTag.getText();
      String newTagText = newTag.getText();
      finalHtmlEditorController.replaceTag(previousTagText, newTagText);
      htmlCodeTextArea.setText(finalHtmlEditorController.fileText);
      listOfTagsTextArea.setText(finalHtmlEditorController.getlistOfTags());
    });

    FlowPane changeTagsAnchorPane = new FlowPane();
    changeTagsAnchorPane.getChildren().add(previousTag);
    changeTagsAnchorPane.getChildren().add(changeTagName);
    changeTagsAnchorPane.getChildren().add(newTag);
    Scene changeScene = new Scene(changeTagsAnchorPane);
    Stage changeWindow = new Stage();
    changeWindow.setTitle("Заміна тегів");
    changeWindow.setScene(changeScene);
    changeWindow.setWidth(500);
    changeWindow.setHeight(80);
    changeWindow.setX(100);
    changeWindow.setY(50);
    changeWindow.show();
  }

}
