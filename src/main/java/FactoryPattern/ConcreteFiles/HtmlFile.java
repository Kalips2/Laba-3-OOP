package FactoryPattern.ConcreteFiles;

import FactoryPattern.AbstractFile;

public class HtmlFile extends AbstractFile {

  @Override
  public void Info() {
    System.out.println("It's Html File!");
  }
}
