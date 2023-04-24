package cos.gui.etc;

import cos.color.item.ColorToken;

public interface GuiToken {
    String GUI = "ㄱㅁㄱ";
    String GROUP = GUI + "-ㄱㄹㄱ";
    String EVENT = GUI + "-ㅇㅃㅇ";

    String COLOR = ColorToken.COLOR;

    String FRAME = "ㅍㄹㅍ";
    String PANEL = "ㅍㅇㅍ";

    String BUTTON           = "ㅂㅇㅂ";
    String CHECK_BOX        = "ㅊㅋㅊ";
    String RADIO_BUTTON     = "ㄹㅂㄹ";
    String TEXT_FIELD       = "ㅌㅍㅌ";
    String PASSWORD_FILED   = "ㅍㅍㅍ";
    String TEXT_AREA        = "ㅌㅇㅌ";

    String COMBO_BOX_INT       = GUI + "ㅋㅈㅋ";
    String COMBO_BOX_LONG      = GUI + "ㅋㅉㅋ";
    String COMBO_BOX_BOOL      = GUI + "ㅋㅂㅋ";
    String COMBO_BOX_STRING    = GUI + "ㅋㅁㅋ";
    String COMBO_BOX_CHARACTER = GUI + "ㅋㄱㅋ";
    String COMBO_BOX_FLOAT     = GUI + "ㅋㅅㅋ";
    String COMBO_BOX_DOUBLE    = GUI + "ㅋㅆㅋ";

    // METHOD
    String ADD = "<<";
    String ADD_EVENT = "<ㅇㅃㅇ";
    String SET_MODEL = "<ㅁㄷㅁ";
    String ADD_KEY = "<ㅋㅂㅋ";
    String ADD_MOUSE = "<ㅁㅇㅁ";

    String SET_VISIBLE = "<ㅂㅈㅂ";
    String GET_VISIBLE = ">ㅂㅈㅂ";
    String SET_ENABLE = "<ㅎㅅㅎ";
    String GET_ENABLE = ">ㅎㅅㅎ";

    String GET_LOCATION = ">ㄹㅋㄹ";

    String SET_BG = "<ㅂㄱㅂ";
    String GET_BG = ">ㅂㄱㅂ";

    String SET_SIZE = "<ㅆㅈㅆ";
    String GET_SIZE = ">ㅆㅈㅆ";
    String GET_WIDTH = ">ㄴㅂㄴ";  // 너비
    String GET_HEIGHT = ">ㄴㅍㄴ"; // 높이
    String SET_WIDTH = "<ㄴㅂㄴ";  // 너비
    String SET_HEIGHT = "<ㄴㅍㄴ"; // 높이

    String GET_X = ">ㄱㄹㄱ";      // 가로 (X)
    String GET_Y = ">ㅅㄹㅅ";      // 세로 (Y)
    String SET_X = "<ㄱㄹㄱ";      // 가로
    String SET_Y = "<ㅅㄹㅅ";      // 세로

    String SET_TEXT = "<ㅌㅅㅌ";
    String GET_TEXT = ">ㅌㅅㅌ";
    String SET_NAME = "<ㅇㄹㅇ";
    String GET_NAME = ">ㅇㄹㅇ";

}
