package bin.repository;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.LinkedList;

import static bin.token.Token.ACCESS;

public class AccessList extends LinkedList<TypeMap> {
    public AccessList() {
        this.add(new TypeMap());
    }

    public void remove(String type, String name) {
        super.getFirst().get(type).remove(name);
    }

    public void create(String type, String name, Object value) {
        int accessLevel = this.getAccess(name);
        if (isLevel(accessLevel)) {
            super.get(accessLevel).create(type, name, value);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public void update(String name, Object value) {
        int accessLevel = this.getAccess(name);
        if (isLevel(accessLevel)) {
            super.get(accessLevel).update(name, value);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public void update(String type, String name, Object value) {
        int accessLevel = this.getAccess(name);
        if (isLevel(accessLevel)) {
            super.get(accessLevel).update(type, name, value);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public Object get(String name) {
        int accessLevel = getAccess(name);
        if (isLevel(accessLevel)) {
            return super.get(accessLevel).find(name.substring(accessLevel));
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public Object get(String type, String name) {
        int accessLevel = getAccess(name);
        if (isLevel(accessLevel)) {
            return super.get(accessLevel).find(type, name.substring(accessLevel));
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public HpMap getMap(String name) {
        int accessLevel = getAccess(name);
        if (isLevel(accessLevel)) {
            return super.get(accessLevel).findMap(name.substring(accessLevel));
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public boolean find(String name) {
        int accessLevel = this.getAccess(name);
        if ((name = name.substring(accessLevel)).contains(ACCESS)
                || name.contains(" ")
                || name.contains(Token.PUT)
                || !isLevel(accessLevel)) return false;
        return super.get(accessLevel).findVar(name);
    }

    public boolean find(String type, String name) {
        int accessLevel = this.getAccess(name);
        if ((name = name.substring(accessLevel)).contains(ACCESS)
                || name.contains(" ")
                || !isLevel(accessLevel)) return false;
        return super.get(accessLevel).findVar(type, name);
    }

    private boolean isLevel(int level) {
        return super.size() > level;
    }

    private int getAccess(String name) {
        char access = ACCESS.charAt(0);
        int count = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == access) count++;
            else break;
        }
        return count;
    }
}
