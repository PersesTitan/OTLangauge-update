import bin.Setting;
import bin.exception.FileException;
import bin.token.EditToken;
import bin.token.SeparatorToken;
import bin.token.Token;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class Integrity implements SeparatorToken {
    public static void main(String[] args) {
        new Integrity();
    }

    enum Type {
        CLASS,
        JAR,
        OTHER;

        public static Type getType(String type) {
            return switch (type) {
                case "class:" -> CLASS;
                case "jar:" -> JAR;
                case "other:" -> OTHER;
                default -> null;
            };
        }
    }

    private final List<String> errorList = new ArrayList<>();
    private final Set<String> errorModule = new HashSet<>();

    private Integrity() {
        Map<String, List<String>> systemMap = new HashMap<>();
        File systemFile = new File(SeparatorToken.getPath(INSTALL_PATH, SYSTEM_FILE_NAME));
        File[] moduleFile = new File(SeparatorToken.getPath(INSTALL_PATH, MODULE)).listFiles();
        File[] klassFile = new File(SeparatorToken.getPath(INSTALL_PATH, "analyzer", "cos")).listFiles();

        try (BufferedReader reader = new BufferedReader(new FileReader(systemFile))) {
            AtomicReference<String> mode = new AtomicReference<>();
            reader.lines()
                    .map(String::strip)
                    .filter(Predicate.not(String::isEmpty))
                    .forEach(v -> {
                        if (v.endsWith(":")) {
                            mode.set(EditToken.bothCut(v, 0, 1));
                            systemMap.put(mode.get(), new ArrayList<>());
                        } else if (mode.get() != null) systemMap.get(mode.get()).add(v);
                    });
        } catch (IOException e) {
            this.errorList.add(systemFile.getAbsolutePath() + "파일이 존재하지 않습니다.");
        }

        final List<File> moduleList = moduleFile == null
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(moduleFile));
        final List<File> klassList = klassFile == null
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(klassFile));

        Map<String, Map<Type, List<String>>> types = new HashMap<>() {{
            systemMap.keySet().forEach(v -> {
                Map<Type, List<String>> map = new HashMap<>() {{
                    for (Type type : Type.values()) this.put(type, new ArrayList<>());
                }};
                readURL(map, v);
                this.put(v, map);
            });
        }};

        // 등록된 모듈 겟수가 있는지 확인
        int systemSize = systemMap.size();
        if (systemSize != moduleList.size()) this.errorList.add(systemMap.values() + " != " + moduleList);
        if (systemSize != klassList.size()) this.errorList.add(systemMap.values() + " != " + klassList);
        moduleList.stream()
                .map(File::getName)
                .filter(Predicate.not(systemMap::containsKey))
                .forEach(v -> this.errorList.add("system.otls에 해당 모듈이 존재하지 않습니다." + v));
        klassList.stream()
                .map(File::getName)
                .filter(Predicate.not(systemMap::containsKey))
                .forEach(v -> this.errorList.add("system.otls에 해당 모듈이 존재하지 않습니다." + v));

        // 모듈이 있는지 확인
        types.forEach((name, map) -> map.forEach((type, list) -> {
            switch (type) {
                case CLASS -> {
                    String filePath = SeparatorToken.getPath(INSTALL_PATH, "analyzer", "cos", name);
                    File file = new File(filePath);
                    if (file.exists() || !file.isDirectory()) {
                        this.errorModule.add(name);
                        this.errorList.add(filePath + " 폴더가 존재하지 않습니다.");
                    } else list.stream()
                                .map(v -> v.replace(Token.ACCESS, SEPARATOR_FILE))
                                .map(v -> filePath.concat(SEPARATOR_FILE).concat(v))
                                .map(File::new)
                                .filter(File::exists)
                                .forEach(v -> {
                                    this.errorModule.add(name);
                                    this.errorList.add(v.getName() + " 파일이 존재하지 않습니다.");
                                });
                }
                case JAR, OTHER -> {
                    String filePath = SeparatorToken.getPath(INSTALL_PATH, "module", name);
                    File file = new File(filePath);
                    if (file.exists() || !file.isDirectory()) {
                        this.errorModule.add(name);
                        this.errorList.add(filePath + " 폴더가 존재하지 않습니다.");
                    } else list.stream()
                            .map(v -> filePath.concat(SEPARATOR_FILE).concat(v))
                            .map(File::new)
                            .filter(File::exists)
                            .forEach(v -> {
                                this.errorModule.add(name);
                                this.errorList.add(v.getName() + " 파일이 존재하지 않습니다.");
                            });
                }
            }
        }));

        try (Scanner scanner = new Scanner(System.in)) {
            if (this.errorList.isEmpty()) {
                Setting.infoMessage("문제가 발생하지 않았습니다.");
            } else {
                Setting.errorMessage("다음과 같은 문제가 존재합니다.");
                this.errorList.forEach(System.out::println);
                System.out.print("발생한 문제들을 해결하시겠습니까? (y/n) : ");
                if (scanner.nextLine().equals("y")) {

                } else System.out.println("취소하였습니다.");
            }
        }
    }

    private void readURL(Map<Type, List<String>> map, String fileName) {
        String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()))) {
            AtomicReference<Type> reference = new AtomicReference<>();
            reader.lines()
                    .map(String::strip)
                    .filter(Predicate.not(String::isEmpty))
                    .forEach(v -> {
                        if (v.endsWith(":")) reference.set(Type.getType(v));
                        else if (reference.get() != null) map.get(reference.get()).add(v);
                    });
        } catch (IOException e) {
            this.errorList.add(fileName + "는 다운로드 할 수 없는 모듈입니다.");
        }

    }
}
