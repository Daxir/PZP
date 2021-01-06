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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public ListView<Receipt> purchasesListView;
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
}
