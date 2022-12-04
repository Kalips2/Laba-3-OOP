package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import javafx.fxml.FXML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.FileUtils;

public class HtmlEditorController {
  @FXML
  private HashSet<String> htmlTags = new HashSet<>();
  private Document doc = null;
  String fileText;
  File htmlFile;

  public HtmlEditorController(File htmlFile) throws FileNotFoundException {
    StringBuilder fileTextStringBuilder = new StringBuilder();
    this.htmlFile = htmlFile;
    FileReader fileReader = new FileReader(htmlFile);
    Scanner scanner = new Scanner(fileReader);
    while (scanner.hasNext()) {
      fileTextStringBuilder.append(scanner.nextLine() + "\n");
    }
    fileText = fileTextStringBuilder.toString();

    try {
      doc = Jsoup.parse(htmlFile, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public String getHtmlCode() {
    return fileText;
  }

  public String getlistOfTags() {
    StringBuilder listOfTags = new StringBuilder();
    listOfTags.append("\t\t" + "List of tags: " + "\n");
    htmlTags.clear();
    int i = 1;
    for (Element element : doc.getAllElements()) {
      if (!htmlTags.contains(element.tagName())) {
        htmlTags.add(element.tagName());
        listOfTags.append(i + ") " + element.tagName() + "\n");
        i++;
      }
    }
    return listOfTags.toString();
  }

  public void replaceTag(String previousTag, String newTag) {
    Elements elements = doc.getElementsByTag(previousTag);
    for (Element element : elements) {
      element.tagName(newTag);
    }
    fileText = fileText.replaceAll("<" + previousTag, "<" + newTag);
    fileText = fileText.replaceAll("</" + previousTag + ">", "</" + newTag + ">");
    fileText = fileText.replaceAll("<\\s+" + previousTag, "<" + newTag);
    fileText = fileText.replaceAll("</\\s+" + previousTag + "\\s+>", "</" + newTag + ">");

    FileUtils.rewriteFile(htmlFile, fileText);

  }

}
