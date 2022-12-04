package utils;

import FactoryPattern.ConcreteProductA.OpenAnotherFile;
import FactoryPattern.FileHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

  public static OpenAnotherFile getResultOfMovement(FileHelper source,
                                                    FileHelper folderDestination) {
    String fileName = source.getName();
    String wayToNewFile = folderDestination.getAbsolutePath() + "/" + fileName;
    return new OpenAnotherFile(new File(wayToNewFile));
  }

  public static OpenAnotherFile getFileWithSuffix(String filePath) {
    File newFile = new File(filePath);
    newFile =
        new File(newFile.getName().split("\\.")[0] + "-copy." + newFile.getName().split("\\.")[1]);
    try {
      newFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new OpenAnotherFile(newFile);
  }

  public static OpenAnotherFile resultOfRename(FileHelper source, String newName) {
    String path = source.getAbsolutePath();
    String newPath = path.substring(0, path.length() - source.getName().length()) + newName;
    return new OpenAnotherFile(new File(newPath));
  }

  public static String getFileExtension(File file) {
    String fileName = file.getName();
    if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
      return fileName.substring(fileName.lastIndexOf(".") + 1);
    } else {
      return "";
    }
  }

  public static void rewriteFile(File file, String newFileText) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(file);
      fileWriter.write(newFileText);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}