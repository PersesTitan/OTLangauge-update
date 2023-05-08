package cos.ocr.etc;

import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.SeparatorToken;
import bin.token.Token;
import bin.variable.Types;
import bin.variable.custom.CustomSet;
import lombok.Getter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.leptonica;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Getter
public class OCRItem extends TessBaseAPI {
    private final CustomSet<String> languages = new CustomSet<>(Types.STRING);
    private boolean isInit = false;
    private String getLanguage() {
        return String.join("+", this.languages);
    }

    public void init(String path) {
        if (super.Init(path, getLanguage()) == -1) {
            throw OCRException.DO_NOT_FIND_LANGUAGE.getThrow(getLanguage());
        }
        this.isInit = true;
    }

    public void init() {
        this.init(getLanguage(), false);
    }

    public void addLanguage(String language) {
        if (OCRItem.haveFile(language)) this.languages.addStr(language);
    }

    public void removeLanguage(String language) {
        this.languages.remove(language);
    }

    private static final String MAIN_PATH = DebugMode.isDevelopment()
            ? SeparatorToken.getPath("src", "main", "resources", "ocr", "data")
            : SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, "module", "ocr");
    public void init(String language, boolean isURL) {
        if (isURL) {
            File file = this.createTemp(language);
            String fileName = file.getName();
            this.languages.addStr(fileName.substring(0, fileName.lastIndexOf('.')));
            this.init(file.getParent());
            file.deleteOnExit();
        } else this.init(OCRItem.MAIN_PATH);
    }

    public String readFile(String file) {
        if (this.languages.isEmpty()) throw OCRException.NOT_HAVE_LANGUAGE.getThrow(null);
        if (!this.isInit) throw OCRException.NOT_INTI.getThrow(null);
        PIX image = leptonica.pixRead(file.replace(Token.ACCESS, SeparatorToken.SEPARATOR_LINE));
        super.SetImage(image);
        BytePointer outText = super.GetUTF8Text();
        try {
            return outText.getString();
        } finally {
            super.End();
            outText.deallocate();
            leptonica.pixDestroy(image);
        }
    }

    public File createTemp(String language) {
        File file;
        String url = String.join("https://github.com/OTLanguage/module/raw/main/ocr/%s.traineddata", language);
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
             FileOutputStream fo = new FileOutputStream((file = File.createTempFile(language, ".traineddata")))) {
            fo.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return file;
        } catch (IOException e) {
            if (DebugMode.isDevelopment()) e.printStackTrace();
            throw FileException.DO_NOT_FIND.getThrow(null);
        }
    }

    // 파일이 존재하는지 확인하는 로직
    public static boolean haveFile(String language) {
        File file = new File(MAIN_PATH + SeparatorToken.SEPARATOR_FILE + language + ".traineddata");
        if (file.exists() && file.isFile()) return true;
        else throw FileException.DO_NOT_FIND.getThrow(language.concat(" 언어파일"));
    }
}
