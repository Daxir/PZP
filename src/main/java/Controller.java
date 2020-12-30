import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {
    public TextArea infoTextArea;
    public Button batonktoryznika;

    public void zniknijbaton() {
        System.out.println("welcome");
        ((Pane) this.batonktoryznika.getParent()).getChildren().remove(batonktoryznika);
    }
}
