package FactoryPattern.ConcreteFactoryA;

import FactoryPattern.ConcreteProductA.OpenXml;
import FactoryPattern.OpenFileAbstractFactory;
import java.awt.Desktop;
import java.io.IOException;

public class XmlFileOpener implements OpenFileAbstractFactory {

  @Override
  public void open(String path) {
    OpenXml currentFile = new OpenXml(path);
    Desktop desktop = null;
    if (Desktop.isDesktopSupported()) {
      desktop = Desktop.getDesktop();
    }
    try {
      desktop.open(currentFile.getFile());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
