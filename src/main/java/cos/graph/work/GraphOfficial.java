package cos.graph.work;

import bin.Repository;
import bin.apply.Read;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.code.CodeMap;
import bin.token.KlassToken;
import cos.graph.controller.plotting.ContinuousFunctionPlotter;
import cos.graph.etc.GraphToken;
import cos.graph.item.GraphItem;
import work.LoopWork;

import java.util.function.DoubleFunction;

public class GraphOfficial extends LoopWork {
    public GraphOfficial() {
        super(LoopMode.PUT, GraphToken.GRAPH, false);
    }

    @Override
    protected int loopItem(int start, int end, LoopMode mode, CodeMap code,
                           String repoKlass, Object klassValue, Object[] params) {
        GraphItem item = (GraphItem) klassValue;

        String endLine = code.get(end).strip();
        String[] tokens = mode.getToken(endLine).split("\\s", 2);
        if (tokens.length != 2) throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
        String type = tokens[0].strip();
        String name = tokens[1].strip();
        if (!type.equals(KlassToken.DOUBLE_VARIABLE)) throw VariableException.TYPE_ERROR.getThrow(type);
        DoubleFunction<Double> function = x -> {
            try {
                Repository.repositoryArray.create(type, name, x);
                Read.read(code, start, end, null);
                return (double) Repository.repositoryArray.get(type, name);
            } finally {
                Repository.repositoryArray.remove(type, name);
            }
        };

        item.addOfficial(new ContinuousFunctionPlotter() {
            @Override
            public double getY(double x) {
                return function.apply(x);
            }

            @Override
            public String getName() {
                return "";
            }
        });
        return end;
    }
}
