package bin.apply.line;

import bin.Repository;
import work.StartWork;

public class LineStaticItem implements LineTool {
    private final StartWork work;
    private final String PARAMS;

    public LineStaticItem(String KLASS_NAME, String METHOD_NAME, String PARAMS) {
        this.work = Repository.startWorks.get(KLASS_NAME, METHOD_NAME);
        this.PARAMS = PARAMS;
    }

    @Override
    public void startItem(int position) {
        this.work.start(null, this.PARAMS, position);
    }
}
