package bin.apply.line;

import bin.Repository;
import bin.repository.HpMap;
import work.StartWork;

public class LineStartItem implements LineTool {
    private final StartWork work;
    private final String PARAMS;
    private final HpMap REPOSITORY;
    private final String VARIABLE_NAME;

    public LineStartItem(String KLASS_NAME, String METHOD_NAME, String PARAMS,
                         HpMap REPOSITORY, String VARIABLE_NAME) {
        this.work = Repository.startWorks.get(KLASS_NAME, METHOD_NAME);
        this.PARAMS = PARAMS;
        this.REPOSITORY = REPOSITORY;
        this.VARIABLE_NAME = VARIABLE_NAME;
    }

    @Override
    public void startItem(int position) {
        this.work.start(this.REPOSITORY.get(this.VARIABLE_NAME), this.PARAMS, position);
    }
}
