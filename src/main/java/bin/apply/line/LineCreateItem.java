package bin.apply.line;

import bin.Repository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LineCreateItem implements LineTool {
    private final String TYPE;
    private final String VARIABLE_NAME;
    private final String VARIABLE_VALUE;

    @Override
    public void startItem(int i) {
        Repository.repositoryArray.create(this.TYPE, this.VARIABLE_NAME, this.VARIABLE_VALUE);
    }
}
