import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddReceiptController implements Initializable {

    public TextField shopName;
    public Button chooseScanButton;
    public TextField scanTextField;
    public DatePicker dateOfPurchaseDatePicker;
    public TextArea tagsTextArea;
    public ListView<Purchase> purchasesListView;
    public TextField productNameTextField;
    public TextField priceTextField;
    public TextField quantityTextField;
    public Button addToPurchasesButton;
    public Button doneButton;
    public Button deletePurchaseButton;
    public Label detectedPriceLabel;
    public CheckBox ocrToggle;
    public Label totalPriceLabel;
    public Label remainingPriceLabel;
    private double sum;
    private double parsedTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Global.tempReceipt != null) {
            loadFromReceipt();
            doneButton.setOnAction(e -> {
                addReceipt();
                Global.editedFlag = true;
                ((Stage) doneButton.getScene().getWindow()).close();
            });
        }
    }

    private void loadFromReceipt() {
        var r = Global.tempReceipt;
        toggleOCR();
        shopName.setText(r.getShopName());
        scanTextField.setText(r.getScan());
        dateOfPurchaseDatePicker.setValue(r.getPurchaseDate());
        tagsTextArea.setText(String.join(", ", r.getTags()));
        purchasesListView.setItems(FXCollections.observableList(r.getPurchases()));
        updateTotalLabel();
    }

    public void toggleOCR() {

        if (!ocrToggle.isSelected()) {
            detectedPriceLabel.setText("");
            remainingPriceLabel.setText("");
        } else {
            detectedPriceLabel.setText("Detected price:");
            remainingPriceLabel.setText("Remaining:");
        }
    }

    private void updateTotalLabel() {
        if (purchasesListView.getItems() != null) {
            double sum = 0;
            for (var x : purchasesListView.getItems()) {
                sum += x.getTotalPurchaseValue();
            }
            totalPriceLabel.setText("Total price: " + sum);
            this.sum = sum;
            if (ocrToggle.isSelected()) {
                remainingPriceLabel.setText("Remaining: " + String.format("%.2f", parsedTotal - this.sum));
            }
        } else {
            totalPriceLabel.setText("Total price: 0");
        }
    }

    private String readPriceFromImage() {
        String[] lines = OCR.doOCR(new File(scanTextField.getText())).split("\n");
        for (String s : lines) {
            System.out.println(s);
            if ((s.toLowerCase().replace("ą", "a").contains("suma")
                    && !s.toLowerCase().contains("ptu")) || s.toLowerCase().contains("pln")) {
                try {
                    this.parsedTotal = Double.parseDouble(s.replace(",", ".")
                            .replaceAll("[^\\d.]", "")
                            .trim());
                } catch (NumberFormatException e) {
                    continue;
                }
                updateTotalLabel();
                return "Detected price: " + parsedTotal;
            }
        }
        return "Could not detect total price";
    }

    public void chooseScan() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        try {
            scanTextField.setText(fileChooser.showOpenDialog(stage).getAbsolutePath());
            fileChooser.setTitle("Choose a file:");
            if (ocrToggle.isSelected()) {
                detectedPriceLabel.setText(readPriceFromImage());
            }
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
            updateTotalLabel();
            priceTextField.setText("");
            productNameTextField.setText("");
            quantityTextField.setText("");
        } catch (NumberFormatException e) {
            popupError("Invalid input");
        }

    }

    public void deletePurchaseFromPurchaseList() {
        purchasesListView.getItems().remove(purchasesListView.getSelectionModel().getSelectedItem());
        updateTotalLabel();
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
        System.out.println(name);
        List<String> tags = tokenizeTags();
        System.out.println(tags.toString());
        List<Purchase> purchases = new ArrayList<>(purchasesListView.getItems());
        System.out.println(purchases.toString());
        String scanPath = scanTextField.getText();
        System.out.println(scanPath);
        LocalDate date = dateOfPurchaseDatePicker.getValue();
        System.out.println(date);
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
