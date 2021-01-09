import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PasswordController implements Initializable {
    public TextField oldPassText;
    public TextField newPassText;
    public Button acceptButton;

    private void errorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void changePassword() {
        if (Arrays.equals(Global.hash, oldPassText.getText().getBytes())) {
            if (!newPassText.getText().isEmpty()) {
                Global.hash = newPassText.getText().getBytes();
                ((Stage) acceptButton.getScene().getWindow()).close();
            } else {
                errorDialog("New password cannot be empty!");
            }
        } else {
            errorDialog("Invalid password");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acceptButton.getParent().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                acceptButton.fire();
            }
        });
    }
}