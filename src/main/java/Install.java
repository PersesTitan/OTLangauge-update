import bin.Setting;
import bin.exception.FileException;
import bin.token.EditToken;
import bin.token.SeparatorToken;
import bin.token.Token;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Install {
    public static void main(String[] args) {
        // 0: install, 1: downloadName, 2: option
        switch (args.length) {
            case 2 -> new Install(args[1]);
            case 3 -> new Install(args[1], args[2]);
            default -> System.out.println("\033[0;31m파일명을 입력해주세요.\033[0m");
        }
    }

    private Install(String moduleName, String option) {
        switch (moduleName) {
            case "ocr" -> {
                // ex) fileName : eng.traineddata
                String fileName = option.concat(".traineddata");
                // ~/.otl/module/ocr/eng.traineddata
                String module = SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, "module", moduleName, fileName);
                this.downloadPrint("add " + fileName);
                this.download(moduleName, fileName, new File(module));
                this.downloadPrintln("... OK");
            }
            default -> System.out.println("\033[0;31m다운로드 할 수 없는 모듈입니다.\033[0m");
        }
    }

    private Install(String fileName) {
        String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";

        File systemFile = new File(SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, "system.otls"));
        // ~/.otl/analyzer/cos/fileName
        // ~/.otl/module/fileName
        String klassPath = SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, "analyzer", "cos", fileName);
        String modulePath = SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, "module", fileName);
        File klassFile = new File(klassPath);
        File moduleFile = new File(modulePath);

        if (klassFile.exists()) this.removeFile(klassFile); // 폴더가 존재할때 제거 -> 생성
        if (!klassFile.mkdirs()) {
            Setting.errorMessage(klassPath.concat("디렉토리(폴더) 생성에 실패하였습니다."));
            return;
        }

        if (moduleFile.exists()) this.removeFile(moduleFile); // 폴더가 존재할때 제거 -> 생성
        if (!moduleFile.mkdirs()) {
            Setting.errorMessage(modulePath.concat("디렉토리(폴더) 생성에 실패하였습니다."));
            return;
        }

        List<String> jars = new ArrayList<>();
        try (BufferedReader reader = this.readUrl(urlPath)) {
            String type = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if ((line = line.strip()).isEmpty()) continue;
                if (line.endsWith(":")) {
                    // ex) jar, other, class
                    type = EditToken.bothCut(line, 0, 1);
                } else {
                    if (type == null) continue;
                    switch (type) {
                        case "class" -> {
                            String path = line.replace(Token.ACCESS, SeparatorToken.SEPARATOR_FILE);
                            File kf = new File(SeparatorToken.getPath(klassPath, path));
                            // 하위 디렉토리가 존재하지 않을때 생성
                            File dir = kf.getParentFile();
                            if (!(dir.exists() || dir.mkdirs())) {
                                Setting.errorMessage(dir.getPath().concat("디렉토리(폴더) 생성에 실패하였습니다."));
                                return;
                            }
                            this.downloadPrint("add " + kf.getName());
                            this.download(fileName, kf.getName(), kf);
                            this.downloadPrintln("... OK");
                        }
                        case "jar" -> {
                            File jf = new File(SeparatorToken.getPath(modulePath, line));
                            // 하위 디렉토리가 존재하지 않을때 생성
                            File dir = jf.getParentFile();
                            if (!(dir.exists() || dir.mkdirs())) {
                                Setting.errorMessage(dir.getPath().concat("디렉토리(폴더) 생성에 실패하였습니다."));
                                return;
                            }
                            jars.add(jf.getName());
                            this.downloadPrint("add " + jf.getName());
                            this.download(fileName, jf.getName(), jf);
                            this.downloadPrintln("... OK");
                        }
                        case "other" -> {
                            File of = new File(SeparatorToken.getPath(modulePath, line));
                            // 하위 디렉토리가 존재하지 않을때 생성
                            File dir = of.getParentFile();
                            if (!(dir.exists() || dir.createNewFile())) {
                                Setting.errorMessage(dir.getPath().concat("디렉토리(폴더) 생성에 실패하였습니다."));
                                return;
                            }

                            this.downloadPrint("add " + of.getName());
                            this.download(fileName, of.getName(), of);
                            this.downloadPrintln("... OK");
                        }
                    }
                }
            }

            // jar 파일을 가지고 있는 모듈일때
            if (!jars.isEmpty()) {
                final String ty = fileName.concat(":");
                StringBuilder readContent = new StringBuilder();
                // system.otls 파일이 존재할때
                if (systemFile.exists()) {
                    try (BufferedReader r = new BufferedReader(new FileReader(systemFile))) {
                        String l;
                        boolean t = true;
                        while ((l = r.readLine()) != null) {
                            if ((l = l.strip()).isEmpty()) continue;
                            if (l.equals(ty)) {
                                readContent.append(ty).append(SeparatorToken.SEPARATOR_LINE);
                                for (String jar : jars) {
                                    readContent.append(" ".repeat(4))
                                            .append(jar)
                                            .append(SeparatorToken.SEPARATOR_LINE);
                                }
                                t = false;
                            } else if (l.endsWith(":")) {
                                readContent.append(l).append(SeparatorToken.SEPARATOR_LINE);
                                t = true;
                            } else if (t) {
                                readContent.append(" ".repeat(4)).append(l).append(SeparatorToken.SEPARATOR_LINE);
                            }
                        }
                    }
                } else {
                    // 존재하지 않을때 파일 생성
                    if (systemFile.createNewFile()) {
                        readContent.append(ty).append(SeparatorToken.SEPARATOR_LINE);
                        for (String jar : jars) {
                            readContent.append(" ".repeat(4)).append(jar).append(SeparatorToken.SEPARATOR_LINE);
                        }
                    } else {
                        Setting.errorMessage(systemFile.getName().concat("파일 생성에 실패하였습니다."));
                        return;
                    }
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(systemFile))) {
                    writer.write(readContent.toString());
                }
            }
        } catch (IOException e) {
            Setting.errorMessage(fileName.concat("파일을 찾을 수 없습니다."));
        }
    }

    private void removeFile(File file) {
        File[] files = file.listFiles();
        if (files == null) file.delete();
        else {
            for (File f : files) {
                if (f.isFile()) f.delete();
                else if (f.isDirectory()) removeFile(f);
            }
            file.delete();
        }
    }

    private void download(String moduleName, String fileName, File downloadFile) {
        String url = String.format("https://github.com/OTLanguage/module/raw/main/%s/%s", moduleName, fileName);
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
             FileOutputStream fo = new FileOutputStream(downloadFile, false)) {
            fo.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw FileException.DO_NOT_FIND.getThrow(url);
        }
    }

    private void downloadPrint(String message) {
        System.out.print(message);
    }

    private void downloadPrintln(String message) {
        System.out.println(message);
    }

    private BufferedReader readUrl(String urlPath) throws IOException {
        return new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()));
    }
}
