import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstLaunchController implements Initializable {
    public TextField passwordText;
    public TextField passwordRepeatText;
    public Button commitButton;

    public void setPassword() throws IOException {
        if (passwordText.getText().equals(passwordRepeatText.getText())) {
            Global.hash = passwordText.getText().getBytes();
            Parent parent = FXMLLoader.load(getClass().getResource("menuScene.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) (commitButton).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commitButton.getParent().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                commitButton.fire();
            }
        });
    }
}
