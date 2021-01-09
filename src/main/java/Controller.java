import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Controller implements Initializable {
    public TextArea infoTextArea;
    public Button addButton;
    public Button deleteButton;
    public ListView<Receipt> receiptList;
    public DatePicker searchDatePicker;
    public Button exportButton;
    private final ObservableList<Receipt> olist = FXCollections.observableArrayList(Global.receiptRepository.getAll());
    public TextField receiptSearch;
    private final ReceiptRepository receiptRepository  = Global.receiptRepository;
    public ImageView imageView;
    public Button changePasswordButton;
    public Label spentThisMonth;
    public Label monthlyBudget;
    public Label remainingBudget;

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
            updateLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEditBudgetWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("budgetWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit budget");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 272, 112));
            stage.showAndWait();
            updateLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFromRepository() {
        for (var e : receiptRepository.getAll()) {
            var set = new HashSet<>(olist);
            if (set.add(e)) {
                olist.add(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.budget.updateMonth();
        updateFromRepository();
        updateLabels();
        FilteredList<Receipt> flReceipt = new FilteredList<>(olist, r -> true);
        receiptList.setItems(flReceipt);
        receiptSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            flReceipt.setPredicate(r -> {
                if (!r.getShopName().toLowerCase().contains(newValue.toLowerCase().trim())) {
                    for (var x : r.getTags()) {
                        if (x.toLowerCase().contains(newValue.toLowerCase().trim())) {
                            return true;
                        }
                    }
                    return r.getPurchaseDate().toString().contains(newValue.trim());
                }
                return true;
            });
        });
    }

    private void updateLabels() {
        Global.budget.update();
        spentThisMonth.setText(Double.toString(Global.budget.getCurrentSpendings()));
        monthlyBudget.setText(Double.toString(Global.budget.getMonthlyBudget()));
        remainingBudget.setText(Double.toString(Global.budget.getRemainingSpendings()));
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
        olist.remove(receiptList.getSelectionModel().getSelectedItem());
        infoTextArea.clear();
        imageView.setImage(null);
        updateLabels();
    }

    public void onSearchDatePickerChange() {
        receiptSearch.setText(searchDatePicker.getValue().toString());
    }

    private String chooseFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.setTitle("Choose a file:");
            return (fileChooser.showSaveDialog(stage).getAbsolutePath());
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void exportDatabase() {
        var filepath = chooseFile();
        if (filepath != null && new File("repo").isFile()) {
            try {
                Global.save();
                Files.copy(Paths.get("repo"), Paths.get(filepath));
            } catch (IOException e) {
                popupError("Could not export database!");
            }
        }
    }
}
