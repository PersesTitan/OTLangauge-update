package bin.system.loop;

import bin.apply.Read;
import bin.apply.item.IfItem;
import bin.apply.mode.IfMode;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.SystemException;
import bin.repository.code.CodeMap;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import bin.variable.Types;
import work.LoopWork;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class If {
    private static If IF;
    public static If getInstance() {
        if (IF == null) {
            synchronized (If.class) {
                IF = new If();
            }
        }
        return IF;
    }

    private List<IfItem> stack(CodeMap code, int start) {
        final int size = code.size() + 1;
        // ?ㅅ? ㅇㅇ { => 문법 체크
        if (!IfMode.IF.check(code.get(start))) throw MatchException.GRAMMAR_ERROR.getThrow(code.get(start));
        List<IfItem> list = new ArrayList<>();
        int end = LoopMode.next(code, start);
        list.add(new IfItem(IfMode.IF, start, end));

        for (int i = end + 1; i<size; i++) {
            String line = code.get(i);
            if (line.isEmpty()) continue;
            if (IfMode.ELSE_IF.check(line)) {
                list.add(new IfItem(IfMode.ELSE_IF, i, i = LoopMode.next(code, i)));
            } else if (IfMode.ELSE.check(line)) {
                list.add(new IfItem(IfMode.ELSE, i, LoopMode.next(code, i)));
                break;
            } else break;
        }
        return list;
    }

    private boolean haveElse(List<IfItem> list) {
        int size = list.size();
        return size != 1 && list.get(size - 1).mode().equals(IfMode.ELSE);
    }

    public int start(CodeMap code, int start, String repoKlass) {
        List<IfItem> list = stack(code, start);
        int size = list.size();
        int end = list.get(size - 1).end();
        IfItem elseItem = haveElse(list) ? list.remove(size - 1) : null;

        ListIterator<IfItem> iterator = list.listIterator();
        IfItem item;
        if (this.getIf(code, item = iterator.next())) {
            Read.read(code, item.start(), item.end(), repoKlass);
        } else {
            while (iterator.hasNext()) {
                if (this.getElseIf(code, item = iterator.next())) {
                    Read.read(code, item.start(), item.end(), repoKlass);
                    return end;
                }
            }

            if (elseItem != null) {
                Read.read(code, elseItem.start(), elseItem.end(), repoKlass);
            }
        }
        return end + 1;
    }

    private boolean getIf(CodeMap code, IfItem item) {
        if (item.mode().equals(IfMode.IF)) return this.getIf(code.get(item.start()));
        else throw SystemException.SYSTEM_ERROR.getThrow(item.toString());
    }

    private boolean getElseIf(CodeMap code, IfItem item) {
        if (item.mode().equals(IfMode.ELSE_IF)) return this.getElseIf(code.get(item.start()));
        else throw SystemException.SYSTEM_ERROR.getThrow(item.toString());
    }

    private boolean getIf(String line) {
        // line : ㅇㅇ or [ㅇㅇ]
        line = EditToken.bothCut(line, Token.IF.length(), 1).strip();
        if (CheckToken.bothCheck(line, Token.PARAM_S, Token.PARAM_E))
            line = EditToken.bothCut(line).strip();
        return (boolean) Types.BOOLEAN.originCast(line);
    }

    private boolean getElseIf(String line) {
        // line : ㅇㅇ or [ㅇㅇ]
        line = EditToken.bothCut(line, Token.ELSE_IF.length(), 1).strip();
        if (CheckToken.bothCheck(line, Token.PARAM_S, Token.PARAM_E))
            line = EditToken.bothCut(line).strip();
        return (boolean) Types.BOOLEAN.originCast(line);
    }
}
