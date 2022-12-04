package FactoryPattern;

public class ClientFactory {
  OpenFileAbstractFactory factory;

  public ClientFactory(OpenFileAbstractFactory factory, String path) {
    this.factory = factory;
    factory.open(path);
  }

  public ClientFactory() {

  }
}
