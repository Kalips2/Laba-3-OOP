package FactoryPattern.ConcreteProductA;

import FactoryPattern.FileHelper;
import java.io.File;

public class OpenTxt extends FileHelper {
  public OpenTxt(String filePathName) {
    super(filePathName);
  }

  public OpenTxt(File file) {
    super(file);
  }
}
