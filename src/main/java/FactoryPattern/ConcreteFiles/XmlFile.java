package FactoryPattern.ConcreteFiles;

import FactoryPattern.AbstractFile;

public class XmlFile extends AbstractFile {
  @Override
  public void Info() {
    System.out.println("It's XmlFile!");
  }
}
