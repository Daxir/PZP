import java.io.*;

public class Global {
//    public static ReceiptRepository receiptRepository = new ReceiptRepository();
    public static ReceiptRepository receiptRepository = new ReceiptRepository();
    public static byte[] hash = null;

    public static void save() {
        try (FileOutputStream fos = new FileOutputStream("repo");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(hash);
            oos.writeObject(receiptRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read() {
        try (FileInputStream fis = new FileInputStream("repo");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            hash = (byte[]) ois.readObject();
            receiptRepository = (ReceiptRepository) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
