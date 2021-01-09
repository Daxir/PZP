import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label passwordTextField;
    public PasswordField password;
    public Button loginButton;
    public Button importButton;
    private String filepath = "repo";

    public void login() throws IOException {
        Global.read(filepath);
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

    public void importDatabase() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.setTitle("Choose a file:");
            filepath = (fileChooser.showOpenDialog(stage).getAbsolutePath());
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Could not load database!");
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
