package bin.repository;

import bin.exception.VariableException;
import work.WorkTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkList<W extends WorkTool> {
    private List<W> list = null;
    private W w;

    protected WorkList(W w) {
        this.w = w;
    }

    public W get(int size) {
        if (this.w == null) {
            for (W w : this.list) if (size == w.getSize()) return w;
            throw VariableException.NO_DEFINE_METHOD.getThrow(null);
        } else return this.w;
    }

    public void add(W w) {
        if (this.list == null) {
            if (this.w.getSize() == w.getSize()) throw VariableException.DEFINE_METHOD_LEN.getThrow(w.getSize());
            this.list = new ArrayList<>(Arrays.asList(this.w, w));
            this.w = null;
        } else {
            int size = w.getSize();
            for (W work : this.list) {
                if (work.getSize() == size) throw VariableException.DEFINE_METHOD_LEN.getThrow(size);
            }
            this.list.add(w);
        }
    }
}
