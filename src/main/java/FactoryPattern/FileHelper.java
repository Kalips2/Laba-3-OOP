package FactoryPattern;

import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import utils.FileUtils;

public abstract class FileHelper {
  protected File file;

  public FileHelper(String filePathName) {
    file = new File(filePathName);
  }

  public FileHelper(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return file.toPath().getFileName().toString();
  }

  public File getFile() {
    return file;
  }

  public Boolean isEqualOrParent(OpenAnotherFile other) {
    String wayToFile = getFile().getAbsolutePath();
    String wayToOther = other.file.getAbsolutePath();
    if (wayToFile.length() <= wayToOther.length()) {
      String line = wayToOther.substring(0, wayToFile.length());
      if (line.equals(wayToFile)) {
        return true;
      }
    }

    return false;
  }

  public void delete() throws IOException {
    if (!file.delete()) {
      throw new IOException();
    }
  }

  public void copy(OpenAnotherFile fileToMove, OpenAnotherFile destination) throws IOException {
    try (FileChannel sourceChannel = new FileInputStream(fileToMove.file).getChannel();
         FileChannel destChannel = new FileOutputStream(destination.file).getChannel()) {
      destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void cut(OpenAnotherFile destination) throws IOException {
    File dest = destination.file;
    if (!file.renameTo(dest)) {
      throw new IOException();
    }
  }

  public void rename(OpenAnotherFile to) throws IOException {
    cut(to);
    file = to.file;
  }

  public OpenAnotherFile newFolder() throws IOException {
    String fileway = file.getAbsolutePath();
    fileway += "/" + "NewFolder";
    OpenAnotherFile newFolder = FileUtils.getFileWithSuffix(fileway);

    System.out.println(newFolder.file.getName());
    newFolder.mkdir();

    return newFolder;
  }

  public OpenAnotherFile newFile() throws IOException {
    String fileway = file.getAbsolutePath();
    fileway += "\\" + "NewFile.txt";
    OpenAnotherFile newFile = FileUtils.getFileWithSuffix(fileway);
    return newFile;
  }


  public Boolean isFile() {
    return file.isFile();
  }

  public Boolean mkdir() {
    return file.mkdir();
  }

  public String getName() {
    return file.getName();
  }

  public String getAbsolutePath() {
    return file.getAbsolutePath();
  }

  public Boolean isExist() {
    return file.exists();
  }

}