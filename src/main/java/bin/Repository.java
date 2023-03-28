package bin;

import bin.apply.casting.LoadCasting;
import bin.list.ListForEach;
import bin.list.MapKeyForEach;
import bin.list.MapValueForEach;
import bin.list.SetForEach;
import bin.repository.AccessList;
import bin.repository.ReplaceMap;
import bin.repository.WorkMap;
import bin.repository.code.CodesMap;
import bin.string.Contains;
import bin.string.Index;
import bin.string.SubString;
import bin.string.regexp.ReplaceString;
import bin.string.regexp.ReplaceAll;
import bin.string.regexp.Split;
import bin.string.regexp.SplitAll;
import bin.string.tocase.ToLowerCase;
import bin.string.tocase.ToUpperCase;
import bin.system.CreateSystem;
import bin.system.console.out.Print;
import bin.system.console.out.PrintSpace;
import bin.system.console.out.PrintTab;
import bin.system.console.out.Println;
import bin.system.console.replace.NextLine;
import bin.system.console.replace.Space;
import bin.system.console.replace.Tab;
import bin.system.etc.GetType;
import bin.system.etc.Quit;
import bin.system.etc.Sleep;
import bin.system.console.*;
import bin.system.except.Try;
import bin.system.loop.While;
import bin.token.StringToken;
import bin.token.Token;
import bin.variable.Types;
import work.CreateWork;
import work.LoopWork;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;

import static bin.token.KlassToken.*;

public interface Repository {
    CodesMap codes = new CodesMap();
    AccessList repositoryArray = new AccessList();

    Map<String, CreateWork<?>> createWorks = new HashMap<>() {{
        for (Types types : Types.values()) {
            put(types.getOriginType(), types.getCreateWork());
            put(types.getSetType(), types.getSetWork());
            put(types.getListType(), types.getListWork());
            put(types.getMapType(), types.getMapWork());
        }
        put(SYSTEM, new CreateSystem());
    }};

    ReplaceMap replaceWorks = new ReplaceMap() {{
        put(STRING_VARIABLE, SYSTEM, SCANNER, new Scanner());
        put(STRING_VARIABLE, SYSTEM, GET_TYPE, new GetType());
        put(STRING_VARIABLE, SYSTEM, PRINTLN, new NextLine());
        put(STRING_VARIABLE, SYSTEM, PRINT_TAB, new Tab());
        put(STRING_VARIABLE, SYSTEM, PRINT_SPACE, new Space());
        put(STRING_VARIABLE, STRING_VARIABLE, StringToken.REPLACE, new ReplaceString());
        put(STRING_VARIABLE, STRING_VARIABLE, StringToken.REPLACE_ALL, new ReplaceAll());

        put(STRING_VARIABLE, STRING_VARIABLE, StringToken.SUBSTRING, new SubString());
        put(STRING_VARIABLE, STRING_VARIABLE, StringToken.TO_UPPER_CASE, new ToUpperCase());
        put(STRING_VARIABLE, STRING_VARIABLE, StringToken.TO_LOWER_CASE, new ToLowerCase());

        put(LIST_STRING, STRING_VARIABLE, StringToken.SPLIT, new Split());
        put(LIST_STRING, STRING_VARIABLE, StringToken.SPLIT_ALL, new SplitAll());
        put(BOOL_VARIABLE, STRING_VARIABLE, StringToken.CONTAINS, new Contains());
        put(INT_VARIABLE, STRING_VARIABLE, StringToken.INDEX, new Index());

        putAll(new LoadCasting().load());
    }};

    WorkMap<StartWork> startWorks = new WorkMap<>() {{
        put(SYSTEM, PRINT, new Print());
        put(SYSTEM, PRINTLN, new Println());
        put(SYSTEM, PRINT_TAB, new PrintTab());
        put(SYSTEM, PRINT_SPACE, new PrintSpace());
        put(SYSTEM, QUIT, new Quit());
        put(SYSTEM, SLEEP, new Sleep());
    }};
    WorkMap<LoopWork> loopWorks = new WorkMap<>() {{
        put(SYSTEM, WHILE, new While());
        put(SYSTEM, TRY, new Try());

        put(SET_INTEGER, Token.LOOP, new SetForEach(SET_INTEGER));
        put(SET_LONG, Token.LOOP, new SetForEach(SET_LONG));
        put(SET_BOOLEAN, Token.LOOP, new SetForEach(SET_BOOLEAN));
        put(SET_STRING, Token.LOOP, new SetForEach(SET_STRING));
        put(SET_CHARACTER, Token.LOOP, new SetForEach(SET_CHARACTER));
        put(SET_FLOAT, Token.LOOP, new SetForEach(SET_FLOAT));
        put(SET_DOUBLE, Token.LOOP, new SetForEach(SET_DOUBLE));

        put(LIST_INTEGER, Token.LOOP, new ListForEach(LIST_INTEGER));
        put(LIST_LONG, Token.LOOP, new ListForEach(LIST_LONG));
        put(LIST_BOOLEAN, Token.LOOP, new ListForEach(LIST_BOOLEAN));
        put(LIST_STRING, Token.LOOP, new ListForEach(LIST_STRING));
        put(LIST_CHARACTER, Token.LOOP, new ListForEach(LIST_CHARACTER));
        put(LIST_FLOAT, Token.LOOP, new ListForEach(LIST_FLOAT));
        put(LIST_DOUBLE, Token.LOOP, new ListForEach(LIST_DOUBLE));

        put(MAP_INTEGER, Token.LOOP_K, new MapKeyForEach(MAP_INTEGER));
        put(MAP_LONG, Token.LOOP_K, new MapKeyForEach(MAP_LONG));
        put(MAP_BOOLEAN, Token.LOOP_K, new MapKeyForEach(MAP_BOOLEAN));
        put(MAP_STRING, Token.LOOP_K, new MapKeyForEach(MAP_STRING));
        put(MAP_CHARACTER, Token.LOOP_K, new MapKeyForEach(MAP_CHARACTER));
        put(MAP_FLOAT, Token.LOOP_K, new MapKeyForEach(MAP_FLOAT));
        put(MAP_DOUBLE, Token.LOOP_K, new MapKeyForEach(MAP_DOUBLE));

        put(MAP_INTEGER, Token.LOOP_V, new MapValueForEach(MAP_INTEGER));
        put(MAP_LONG, Token.LOOP_V, new MapValueForEach(MAP_LONG));
        put(MAP_BOOLEAN, Token.LOOP_V, new MapValueForEach(MAP_BOOLEAN));
        put(MAP_STRING, Token.LOOP_V, new MapValueForEach(MAP_STRING));
        put(MAP_CHARACTER, Token.LOOP_V, new MapValueForEach(MAP_CHARACTER));
        put(MAP_FLOAT, Token.LOOP_V, new MapValueForEach(MAP_FLOAT));
        put(MAP_DOUBLE, Token.LOOP_V, new MapValueForEach(MAP_DOUBLE));
    }};
}
