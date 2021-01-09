import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddReceiptController {

    public TextField shopName;
    public Button chooseScanButton;
    public TextField scanTextField;
    public DatePicker dateOfPurchaseDatePicker;
    public TextArea tagsTextArea;
    public ListView purchasesListView;
    public TextField productNameTextField;
    public TextField priceTextField;
    public TextField quantityTextField;
    public Button addToPurchasesButton;
    public Button doneButton;
    public Button deletePurchaseButton;

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
            priceTextField.setText("");
            productNameTextField.setText("");
            quantityTextField.setText("");
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
            Global.receiptRepository.add(e);
        } catch (IllegalArgumentException e) {
            popupError(e.getMessage());
        }
        ((Stage) doneButton.getScene().getWindow()).close();
    }

    private List<String> tokenizeTags() {
        return Arrays.asList(tagsTextArea.getText().replace(", ", ",").split(","));
    }
}
