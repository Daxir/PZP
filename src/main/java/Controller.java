import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    public TextArea infoTextArea;
    public Button batonktoryznika;
    public Button addButton;
    public Button deleteButton;
    public ListView<Receipt> receiptList;
    public TextField receiptSearch;
    public TextField password;
    public Button loginButton;
    private ReceiptRepository receiptRepository = new ReceiptRepository();
    public ImageView imageView;

    public void zniknijbaton() {
        System.out.println("welcome");
        ((Pane) this.batonktoryznika.getParent()).getChildren().remove(batonktoryznika); //usuń.mnie();
    }

    public void showList() {
        ObservableList<Receipt> receipts = FXCollections.observableArrayList(receiptRepository.getAll());
        receiptList = new ListView<>(receipts);
    }

    public void setAddButton() {
        addButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
        });

    }
}
