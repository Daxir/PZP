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
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    private ReceiptRepository receiptRepository = new ReceiptRepository();
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
    public Button addReceiptButton;
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
        var e = new Purchase(productNameTextField.getText(),
                Double.parseDouble(priceTextField.getText()), Integer.parseInt(quantityTextField.getText()));
        purchasesListView.getItems().add(e);
        System.out.println(purchasesListView.getItems().toString());
        purchasesListView.refresh();
    }
}
