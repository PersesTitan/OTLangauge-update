package cos.gui.work;

import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.KlassToken;
import bin.token.SeparatorToken;
import cos.gui.etc.GuiException;
import cos.gui.etc.GuiToken;
import cos.gui.item.*;
import work.CreateWork;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class CreateGUI extends CreateWork<Component> implements GuiToken {
    private static boolean useGUI = false;

    public CreateGUI() {
        super(Component.class, GUI, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object createItem(Object[] params) {
        if (!CreateGUI.useGUI) {
            System.setProperty("java.awt.headless", "false");
            String iconPath = DebugMode.isDevelopment()
                    ? SeparatorToken.getPath(Objects.requireNonNull(this.getClass().getResource("gui")).getPath(), "icon.otlm")
                    : SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, SeparatorToken.MODULE, "gui", "icon.otlm");
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(iconPath))) {
                Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
            } catch (IOException | ClassNotFoundException e) {
                throw FileException.CREATE_ICON_ERROR.getThrow(null);
            }
            CreateGUI.useGUI = true;
        }

        return switch (params[0].toString()) {
            case BUTTON -> new ButtonItem();
            case CHECK_BOX -> new CheckBoxItem();
            case RADIO_BUTTON -> new RadioButtonItem();
            case TEXT_FIELD -> new TextFieldItem();
            case FRAME -> new FrameItem();
            case PANEL -> new PanelItem();
            default -> throw GuiException.DO_NOT_SUPPORT_TYPE.getThrow(params[0]);
        };
    }

    @Override
    public boolean check(Object value) {
        return value instanceof Component && value instanceof ComponentTool;
    }
}
