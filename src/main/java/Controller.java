import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextArea infoTextArea;
    public Button addButton;
    public Button deleteButton;
    public ListView<Receipt> receiptList;
    public TextField receiptSearch;
    public TextField password;
    public Button loginButton;
    private ReceiptRepository receiptRepository;
    public ImageView imageView;
    public TextField shopName;
    public Button chooseScanButton;
    public TextField scanTextField;
    public DatePicker dateOfPurchaseDatePicker;
    public TextArea tagsTextArea;
    public ListView<Purchase> purchasesListView;
    public Button addToPurchasesButton;
    public TextField productNameTextField;
    public TextField priceTextField;
    public TextField quantityTextField;
    public Button doneButton;

    public void openAddWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("addWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New receipt");
            stage.setScene(new Scene(root, 525, 484));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        receiptRepository = new ReceiptRepository();
//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Can you kindly log in?");
//            stage.setScene(new Scene(root, 799, 600));
//            stage.show();
//            imageView.getScene().getWindow().hide();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void showList() {
        ObservableList<Receipt> receipts = FXCollections.observableArrayList(receiptRepository.getAll());
        receiptList = new ListView<>(receipts);
    }

    public void chooseScan() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        try {
            scanTextField.setText(fileChooser.showOpenDialog(stage).getAbsolutePath());
            fileChooser.setTitle("Choose a file:");
        } catch (RuntimeException e) {
            scanTextField.setText("");
        }
    }

//    public void doTheThing() {
//        var theActualThing = dateOfPurchaseDatePicker.getValue();
//    }

    private List<String> tokenizeTags() {
        return Arrays.asList(tagsTextArea.getText().split(","));
    }

    public void addToPurchases() {
        String name = productNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();
        if (name.length() == 0 || price.length() == 0 || quantity.length() == 0) {
            popupError("At the very least one of the required text fields is empty!");
            return;
        }
        try {
            var e = new Purchase(name, Double.parseDouble(price), Integer.parseInt(quantity));
            purchasesListView.getItems().add(e);
        } catch (NumberFormatException e) {
            popupError("Invalid input");
        }

    }

    public void deletePurchaseFromPurchaseList() {
        purchasesListView.getItems().remove(purchasesListView.getSelectionModel().getSelectedItem());
    }

    private void popupError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error:");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(new Image(getClass().getResource("faworytkaicon.png").toExternalForm()));
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addReceipt() {
        if (dateOfPurchaseDatePicker.getValue() == null) {
            popupError("No date selected!");
            return;
        }
        String name = shopName.getText();
        List<String> tags = tokenizeTags();
        List<Purchase> purchases = purchasesListView.getItems();
        String scanPath = scanTextField.getText();
        LocalDate date = dateOfPurchaseDatePicker.getValue();
        try {
            receiptRepository.add(ReceiptFactory.createReceipt(name, tags, purchases, scanPath, date));
        } catch (IllegalArgumentException e) {
            popupError(e.getMessage());
        }
        ((Stage) doneButton.getScene().getWindow()).close();
    }
}
