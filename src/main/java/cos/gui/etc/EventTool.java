package cos.gui.etc;

import bin.Repository;
import bin.apply.loop.Loop;
import bin.apply.loop.LoopFunction;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.TypeMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public interface EventTool {
    void addEvent(String endLine, LoopFunction function);
    void setSize(int width, int height);
    void setText(String text);
    void setVisible(boolean aFlags);
    void setEnabled(boolean enabled);
    String getText();
    boolean isVisible();
    boolean isEnabled();
    int getWidth();
    int getHeight();
    int getX();
    int getY();

    void addKeyListener(KeyListener l);
    void addMouseListener(MouseListener l);

    default void addEvent(AbstractButton button, String endLine, LoopFunction function) {
        if (LoopMode.PUT.check(endLine)) {
            String[] tokens = LoopMode.PUT.getToken(endLine).split("\\s", 2);
            String type = tokens[0].strip();
            String name = tokens[1].strip();
            if (!GuiToken.ACTION_EVENT.equals(type))
                throw VariableException.TYPE_ERROR.getThrow(tokens[0].strip());
            button.addActionListener(e -> this.add(e, type, name, function));
        } else throw MatchException.ZONE_MATCH_ERROR.getThrow(endLine);
    }

    default void addEvent(JTextField field, String endLine, LoopFunction function) {
        if (LoopMode.PUT.check(endLine)) {
            String[] tokens = LoopMode.PUT.getToken(endLine).split("\\s", 2);
            String type = tokens[0].strip();
            String name = tokens[1].strip();
            if (!GuiToken.ACTION_EVENT.equals(type))
                throw VariableException.TYPE_ERROR.getThrow(tokens[0].strip());
            field.addActionListener(e -> this.add(e, type, name, function));
        } else throw MatchException.ZONE_MATCH_ERROR.getThrow(endLine);
    }

    private void add(ActionEvent e, String type, String name, LoopFunction function) {
        TypeMap repository = new TypeMap();
        repository.create(type, name, new EventItem(e));
        try {
            Repository.repositoryArray.addFirst(repository);
            function.run();
        } finally {
            Loop.check(repository, Repository.repositoryArray.removeFirst());
        }
    }
}
