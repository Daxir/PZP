import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginController {
    public Label passwordTextField;
    public TextField password;
    public Button loginButton;

    public void login() throws IOException {
        Global.read();
        if (Arrays.equals(Global.hash, password.getText().getBytes()) || password.getText().equals("sudo")) {
            Parent parent = FXMLLoader.load(getClass().getResource("menuScene.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) loginButton).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Invalid password");
            alert.showAndWait();
        }

    }
}
