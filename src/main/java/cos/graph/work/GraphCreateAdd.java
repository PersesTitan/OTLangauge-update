package cos.graph.work;

import bin.exception.VariableException;
import cos.graph.etc.GraphToken;
import cos.graph.item.GraphItem;
import cos.gui.etc.GuiToken;
import cos.gui.item.FrameItem;
import work.StartWork;

public class GraphCreateAdd extends StartWork {
    public GraphCreateAdd() {
        super(GraphToken.GRAPH, false, GuiToken.GUI);
    }

    @Override
    protected void startItem(Object klassValue, Object[] params) {
        if (params[0] instanceof FrameItem frame) ((GraphItem) klassValue).createAdd(frame);
        else throw VariableException.TYPE_ERROR.getThrow(params[0]);
    }
}
