import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    public TextArea infoTextArea;
    public Button addButton;
    public Button deleteButton;
    public ListView<Receipt> receiptList;
    public TextField receiptSearch;
    private final ReceiptRepository receiptRepository  = Global.receiptRepository;
    public ImageView imageView;
    public Button changePasswordButton;

    public void openPasswordWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("passwordScene.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Change password");
            stage.setScene(new Scene(root, 600, 400));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("addWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New receipt");
            stage.setScene(new Scene(root, 525, 484));
            stage.showAndWait();
            updateFromRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFromRepository() {
        for (var e : receiptRepository.getAll()) {
            var set = new HashSet<>(receiptList.getItems());
            if (set.add(e)) {
                receiptList.getItems().add(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (receiptList != null) {
            updateFromRepository();
        }
    }

    private void popupError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void showInfo() {
        if (receiptList.getSelectionModel().getSelectedItem() != null) {
            try {
                Image img = new Image(new FileInputStream(receiptList.getSelectionModel().getSelectedItem().getScan()));
                imageView.setImage(img);
            }  catch(FileNotFoundException e) {
                Image img = new Image("invalidFile.png");
                imageView.setImage(img);
            }
            infoTextArea.setText(receiptList.getSelectionModel().getSelectedItem().getInfo());
        }
    }

    public void deleteReceipt() {
        receiptRepository.remove(receiptList.getSelectionModel().getSelectedItem());
        receiptList.getItems().remove(receiptList.getSelectionModel().getSelectedItem());
        infoTextArea.clear();
        imageView.setImage(null);
    }

}
