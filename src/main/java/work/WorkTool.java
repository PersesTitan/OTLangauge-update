package work;

import bin.Repository;
import bin.exception.FileException;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.variable.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public interface WorkTool extends Serializable {
    void reset();

    default void checkType(CreateWork<?> createWork, Object klassValue) {
        if (createWork.check(klassValue))
            throw VariableException.VALUE_ERROR.getThrow(Types.toString(klassValue));
    }

    @Serial
    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            reset();
        } catch (IOException | ClassNotFoundException e) {
            throw FileException.DO_NOT_INCLUDE.getThrow(e.getMessage());
        }
    }
}
