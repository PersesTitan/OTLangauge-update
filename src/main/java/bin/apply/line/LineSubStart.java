package bin.apply.line;

import bin.Setting;
import bin.apply.tool.ApplyTool;
import bin.apply.Start;

public class LineSubStart implements LineTool {
    private final String VARIABLE;
    private final String SUB_TOKEN;

    public LineSubStart(String LINE) {
        // 변수명<<값 => [변수명, <<값]
        String[] tokens = ApplyTool.cutSub(LINE);
        if (tokens.length == 1) {
            this.VARIABLE = null;
            this.SUB_TOKEN = LINE;
        } else {
            this.VARIABLE = tokens[0].strip();
            this.SUB_TOKEN = tokens[1].strip();
        }
    }

    @Override
    public void startItem(int start) {
        if (this.VARIABLE == null) Setting.runMessage(this.SUB_TOKEN, start);
        else Start.subStart(this.VARIABLE, this.SUB_TOKEN, start);
    }
}
