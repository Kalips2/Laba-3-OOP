package FactoryPattern.ConcreteFactoryA;

import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import FactoryPattern.OpenFileAbstractFactory;
import java.awt.Desktop;
import java.io.IOException;

public class AnotherFileOpener implements OpenFileAbstractFactory {
  @Override
  public void open(String path) {
    OpenAnotherFile currentFile = new OpenAnotherFile(path);
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
