package cos.gui.etc;

import lombok.RequiredArgsConstructor;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class EventItem {
    private final ActionEvent actionEvent;

    public Object getSource() {
        return this.actionEvent.getSource();
    }
}
