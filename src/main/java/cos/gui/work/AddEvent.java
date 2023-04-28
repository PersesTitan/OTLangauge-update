package cos.gui.work;

import bin.Repository;
import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.code.CodeMap;
import cos.gui.etc.GuiToken;
import cos.gui.item.ComponentTool;
import work.LoopWork;

public class AddEvent extends LoopWork {
    public AddEvent() {
        super(
                new LoopMode[] {LoopMode.NONE, LoopMode.PUT},
                GuiToken.GUI, false
        );
    }

    @Override
    protected int loopItem(int start, int end, LoopMode mode, CodeMap code, String repoKlass,
                           Object klassValue, Object[] params) {
        ComponentTool component = (ComponentTool) klassValue;
        switch (mode) {
            case NONE -> component.addActionListener(l -> Read.read(code, start, end, repoKlass));
            case PUT -> {
                String endLine = code.get(end);
                String[] tokens = mode.getToken(endLine).strip().split("\\s", 2);
                if (tokens.length != 2) throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
                String type = tokens[0].strip();
                String name = tokens[1].strip();
                if (!type.equals(GuiToken.EVENT))
                    throw VariableException.TYPE_ERROR.getThrow(GuiToken.EVENT + " != " + type);
                component.addActionListener(l -> {
                    try {
                        Repository.repositoryArray.create(type, name, l);
                        Read.read(code, start, end, null);
                    } finally {
                        Repository.repositoryArray.remove(type, name);
                    }
                });
            }
            default -> throw VariableException.TYPE_ERROR.getThrow(code.get(end));
        }
        return end;
    }
}
