package FactoryPattern.ConcreteFactoryB;

import FactoryPattern.OpenFileAbstractFactory;
import FactoryPattern.ConcreteProductB.OpenHtml;
import java.io.File;

public class HtmlFileOpener implements OpenFileAbstractFactory {
  @Override
  public void open(String path) {
    OpenHtml open = new OpenHtml(new File(path));
    open.open();
  }
}
