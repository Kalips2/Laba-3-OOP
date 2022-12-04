package FactoryPattern.ConcreteFiles;

import FactoryPattern.AbstractFile;

public class AnotherFile extends AbstractFile {
  @Override
  public void Info() {
    System.out.println("I'm another File!");
  }
}
