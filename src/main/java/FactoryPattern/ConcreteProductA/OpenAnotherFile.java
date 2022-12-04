package FactoryPattern.ConcreteProductA;

import FactoryPattern.FileHelper;
import java.io.File;

public class OpenAnotherFile extends FileHelper {
  public OpenAnotherFile(String filePathName) {
    super(filePathName);
  }

  public OpenAnotherFile(File file) {
    super(file);
  }
}
