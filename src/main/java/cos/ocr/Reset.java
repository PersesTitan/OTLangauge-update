package cos.ocr;

import bin.Repository;
import bin.token.KlassToken;
import cos.ocr.etc.OCRItem;
import cos.ocr.work.*;
import cos.ocr.etc.OCRToken;
import work.ResetWork;

public class Reset implements ResetWork, OCRToken {
    @Override
    public void reset() {
        Repository.createWorks.put(OCR, new CreateOCR());

        AddWork<OCRItem> addWork = new AddWork<>(OCR);

        addWork.addR(KlassToken.SET_STRING, GET_LANGUAGE, OCRItem::getLanguages);
        addWork.addR(KlassToken.STRING_VARIABLE, START, KlassToken.STRING_VARIABLE, OCRItem::readFile);

        addWork.addS(SET_LANGUAGE, KlassToken.STRING_VARIABLE, OCRItem::addLanguage);
        addWork.addS(REMOVE_LANGUAGE, KlassToken.STRING_VARIABLE, OCRItem::removeLanguage);
        addWork.addS(INIT_LANGUAGE, OCRItem::init);
    }
}
