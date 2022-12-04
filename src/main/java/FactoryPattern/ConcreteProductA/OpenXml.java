package FactoryPattern.ConcreteProductA;

import FactoryPattern.FileHelper;
import java.io.File;

public class OpenXml extends FileHelper {
  public OpenXml(String filePathName) {
    super(filePathName);
  }

  public OpenXml(File file) {
    super(file);
  }
}
