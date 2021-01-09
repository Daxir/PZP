import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class Controller implements Initializable {
    public TextArea infoTextArea;
    public Button addButton;
    public Button deleteButton;
    public ListView<Receipt> receiptList;
    public TextField receiptSearch;
    public TextField password;
    public Button loginButton;
    private final ReceiptRepository receiptRepository  = Global.receiptRepository;
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
            var lista = new ArrayList<String>(Arrays.asList("jeden", "dwa"));
            var lista2 = new ArrayList<Purchase>(Arrays.asList(new Purchase("a", 1, 1), new Purchase("b", 2, 2)));
            receiptRepository.add(new Receipt("Test", lista, lista2, "obrazek.png", LocalDate.now()));
            updateFromRepository();
        }
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
        List<Purchase> purchases = new ArrayList<>(purchasesListView.getItems());
        String scanPath = scanTextField.getText();
        LocalDate date = dateOfPurchaseDatePicker.getValue();
        try {
            var e = ReceiptFactory.createReceipt(name, tags, purchases, scanPath, date);
            receiptRepository.add(e);
        } catch (IllegalArgumentException e) {
            popupError(e.getMessage());
        }
        ((Stage) doneButton.getScene().getWindow()).close();
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
