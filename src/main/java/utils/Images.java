package utils;

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
  private static final String CLOSED_FOLDER = "src/main/resources/icons/closeFolder.png";
  private static final String OPEN_FOLDER = "src/main/resources/icons/openFolder.png";
  private static final String FILE_IMAGE = "src/main/resources/icons/file.png";
  private static final int SIZE_OF_ICON = 40;

  public static ImageView closedFolder() throws MalformedURLException {
    return getImageView(CLOSED_FOLDER);
  }

  public static ImageView file() throws MalformedURLException {
    return getImageView(FILE_IMAGE);
  }

  public static ImageView openFolder() throws MalformedURLException {
    return getImageView(OPEN_FOLDER);
  }

  private static ImageView getImageView(String fileWay) throws MalformedURLException {
    File file = new File(fileWay);
    String localUrl = file.toURI().toURL().toString();
    Image image = new Image(localUrl);

    ImageView view = new ImageView(image);
    view.setFitWidth(SIZE_OF_ICON);
    view.setFitHeight(SIZE_OF_ICON);

    return view;
  }
}