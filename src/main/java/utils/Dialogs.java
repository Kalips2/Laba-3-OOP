package utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class Dialogs {

  public static void errorDialog(String headMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(headMessage);
    alert.setHeaderText("Помилка");
    alert.setContentText("Немає доступу!");

    alert.showAndWait();
  }

  public static Character choiceIfExistsFile() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Неможливо зробити цю дію з файлом!");
    alert.setHeaderText("Нове ім'я співпадає зі старим");
    alert.setContentText("Ваш вибір:");

    ButtonType buttonTypeOne = new ButtonType("Пропустити");
    ButtonType buttonTypeTwo = new ButtonType("Переназвати");
    ButtonType buttonTypeCancel = new ButtonType("Відміна", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == buttonTypeOne) {
      return 'p';
    } else if (result.get() == buttonTypeTwo) {
      return 'n';
    } else {
      return 'c';
    }
  }
}