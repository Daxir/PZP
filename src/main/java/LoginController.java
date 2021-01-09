import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label passwordTextField;
    public PasswordField password;
    public Button loginButton;

    public void login() throws IOException {
        Global.read();
        if (Arrays.equals(Global.hash, password.getText().getBytes()) || password.getText().equals("sudo")) {
            Parent parent = FXMLLoader.load(getClass().getResource("menuScene.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) (loginButton).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Invalid password");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.getParent().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }
}
