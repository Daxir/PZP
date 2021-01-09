import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditBudgetController implements Initializable {
    public Button applyNewBudget;
    public TextField monthlySpendingsText;

    public void apply() {
        try {
            Global.budget.setMonthlyBudget(Double.parseDouble(monthlySpendingsText.getText()));
            ((Stage) applyNewBudget.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error:");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Invalid input!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyNewBudget.getParent().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                applyNewBudget.fire();
            }
        });
    }
}
