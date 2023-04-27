package cos.java.controller;

import bin.Repository;
import bin.repository.TypeMap;
import bin.repository.code.CodeMap;
import bin.token.SeparatorToken;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class ShellItem {
    public void start(String path, int start, int end) {
        this.start(Repository.codes.get(path), start, end);
    }

    public void start(CodeMap code, int start, int end) {
        Binding binding = new Binding();
        TypeMap map = Repository.repositoryArray.getFirst();
        map.values().forEach(hp -> hp.forEach(binding::setVariable));
        GroovyShell shell = new GroovyShell(binding);

        StringBuilder builder = new StringBuilder();
        for (int i = start; i<end; i++) builder.append(code.get(i)).append(SeparatorToken.SEPARATOR_LINE);
        shell.evaluate(builder.toString());
    }

    private void makeKlass() {
        String item =
                """
                public class %s {
                    %s
                }
                """;
    }

    private String makeMethod(String returnType, String methodName, String[] param, String item) {
        return String.format(
                """
                public %s %s(%s) {
                    %s
                }
                """, returnType, methodName, String.join(", ", param), item);
    }
}
