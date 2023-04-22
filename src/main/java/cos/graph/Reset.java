package cos.graph;

import bin.Repository;
import cos.graph.etc.GraphToken;
import cos.graph.item.GraphItem;
import cos.graph.work.CreateGraph;
import cos.graph.work.GraphCreateAdd;
import cos.graph.work.GraphOfficial;
import work.ResetWork;

public class Reset implements ResetWork, GraphToken {
    @Override
    public void reset() {
        checkModuleError("gui", "color");

        Repository.createWorks.put(GRAPH, new CreateGraph());
        Repository.startWorks.put(GRAPH, CREATE_ADD, new GraphCreateAdd());
        Repository.loopWorks.put(GRAPH, ADD, new GraphOfficial());

        AddWork<GraphItem> addWork = new AddWork<>(GRAPH);
//        addWork.addS(ADD, s, GraphItem::addOfficial);
        addWork.addS(SET_TEXT, s, GraphItem::setTitle);
        addWork.addS(SET_X, d, GraphItem::setGridSpacingX);
        addWork.addS(SET_Y, d, GraphItem::setGridSpacingY);
        addWork.addS(SET_SIZE, d, d, GraphItem::setGridSpacing);
    }
}
