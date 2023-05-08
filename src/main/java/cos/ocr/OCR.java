package cos.ocr;

import bin.apply.mode.DebugMode;
import bin.token.SeparatorToken;
import cos.ocr.etc.OCRItem;
import org.bytedeco.tesseract.TessBaseAPI;

public class OCR {
    public static void main(String[] args) {
//        OCRItem item = new OCRItem();
//        item.init("src/main/resources/ocr/data", "eng");
//        System.out.println(item.readFile("/Documents/project/test-tessract/images.png"));
        DebugMode.DEVELOPMENT.set();
        OCRItem item = new OCRItem();
        item.init("eng+kor");
        System.out.println(item.readFile(SeparatorToken.SEPARATOR_HOME + "/Documents/project/test-tessract/images.png"));
    }
}
