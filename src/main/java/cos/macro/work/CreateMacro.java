package cos.macro.work;

import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.SeparatorToken;
import cos.macro.etc.MacroToken;
import cos.macro.item.MacroItem;
import work.CreateWork;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class CreateMacro extends CreateWork<MacroItem> {
    private static boolean useMacro = false;

    public CreateMacro() {
        super(MacroItem.class, MacroToken.MACRO);
    }

    @Override
    protected Object createItem(Object[] params) {
        if (!CreateMacro.useMacro) {
            System.setProperty("java.awt.headless", "false");
            String iconPath = DebugMode.isDevelopment()
                    ? SeparatorToken.getPath(Objects.requireNonNull(this.getClass().getResource("gui")).getPath(), "icon.otlm")
                    : SeparatorToken.getPath(SeparatorToken.INSTALL_PATH, SeparatorToken.MODULE, "gui", "icon.otlm");
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(iconPath))) {
                Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
            } catch (IOException | ClassNotFoundException e) {
                throw FileException.CREATE_ICON_ERROR.getThrow(null);
            }
            CreateMacro.useMacro = true;
        }
        return new MacroItem();
    }
}
