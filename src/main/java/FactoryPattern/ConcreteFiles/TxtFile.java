package FactoryPattern.ConcreteFiles;

import FactoryPattern.AbstractFile;

public class TxtFile extends AbstractFile {

  @Override
  public void Info() {
    System.out.println("It's TxtFile!");
  }
}
