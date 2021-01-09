import javafx.scene.control.Label;

import java.io.*;

public class Global {
    public static ReceiptRepository receiptRepository = new ReceiptRepository();
    public static byte[] hash = null;
    public static Budget budget = new Budget(receiptRepository);

    public static void save() {
        try (FileOutputStream fos = new FileOutputStream("repo");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(hash);
            oos.writeObject(receiptRepository);
            oos.writeObject(budget);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            hash = (byte[]) ois.readObject();
            receiptRepository = (ReceiptRepository) ois.readObject();
            budget = (Budget) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
