package FactoryPattern.ConcreteFactoryA;

import FactoryPattern.ConcreteProductA.OpenTxt;
import FactoryPattern.OpenFileAbstractFactory;
import java.awt.Desktop;
import java.io.IOException;

public class TxtFileOpener implements OpenFileAbstractFactory {
  @Override
  public void open(String path) {
    OpenTxt currentFile = new OpenTxt(path);
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
