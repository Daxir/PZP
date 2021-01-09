import java.io.*;

public class Global {
//    public static ReceiptRepository receiptRepository = new ReceiptRepository();
    public static ReceiptRepository receiptRepository = read();

    public static void save() {
        try (FileOutputStream fos = new FileOutputStream("repo");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(receiptRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ReceiptRepository read() {
        try (FileInputStream fis = new FileInputStream("repo");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (ReceiptRepository) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
