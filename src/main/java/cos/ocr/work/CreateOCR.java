package cos.ocr.work;

import cos.ocr.etc.OCRItem;
import cos.ocr.etc.OCRToken;
import work.CreateWork;

public class CreateOCR extends CreateWork<OCRItem> {
    public CreateOCR() {
        super(OCRItem.class, OCRToken.OCR);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new OCRItem();
    }
}
