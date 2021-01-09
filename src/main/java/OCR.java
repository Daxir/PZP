import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class OCR {
    public static String temp = OCR.class.getResource("pol.traineddata").getPath().substring(1);
    public static String datapath = temp.substring(0, temp.length() - 16);

    public static String doOCR(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file); // 2
            ITesseract instance = new Tesseract(); // 3
            instance.setDatapath(datapath); // 4
            instance.setLanguage("pol"); // 5
            return instance.doOCR(bufferedImage); // 6
        } catch (Exception e) {
            // TODO Exception handler
            e.printStackTrace();
        }
        return "";
    }
}
