package bin;

import bin.repository.AccessList;
import bin.repository.WorkMap;
import bin.repository.code.CodesMap;
import bin.string.Contains;
import bin.string.Index;
import bin.string.SubString;
import bin.string.regexp.Replace;
import bin.string.regexp.ReplaceAll;
import bin.string.regexp.Split;
import bin.string.regexp.SplitAll;
import bin.string.tocase.ToLowerCase;
import bin.string.tocase.ToUpperCase;
import bin.system.CreateSystem;
import bin.system.Quit;
import bin.system.Sleep;
import bin.system.console.*;
import bin.system.except.Try;
import bin.system.loop.While;
import bin.token.KlassToken;
import bin.token.StringToken;
import bin.variable.Types;
import work.CreateWork;
import work.LoopWork;
import work.ReplaceWork;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;

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
        put(KlassToken.SYSTEM, new CreateSystem());
    }};

    WorkMap<ReplaceWork> replaceWorks = new WorkMap<>() {{
        put(KlassToken.SYSTEM, KlassToken.SCANNER, new Scanner());
        put(KlassToken.STRING_VARIABLE, StringToken.REPLACE, new Replace());
        put(KlassToken.STRING_VARIABLE, StringToken.REPLACE_ALL, new ReplaceAll());
        put(KlassToken.STRING_VARIABLE, StringToken.SPLIT, new Split());
        put(KlassToken.STRING_VARIABLE, StringToken.SPLIT_ALL, new SplitAll());
        put(KlassToken.STRING_VARIABLE, StringToken.CONTAINS, new Contains());
        put(KlassToken.STRING_VARIABLE, StringToken.INDEX, new Index());
        put(KlassToken.STRING_VARIABLE, StringToken.SUBSTRING, new SubString());
        put(KlassToken.STRING_VARIABLE, StringToken.TO_UPPER_CASE, new ToUpperCase());
        put(KlassToken.STRING_VARIABLE, StringToken.TO_LOWER_CASE, new ToLowerCase());
    }};
    WorkMap<StartWork> startWorks = new WorkMap<>() {{
        put(KlassToken.SYSTEM, KlassToken.PRINT, new Print());
        put(KlassToken.SYSTEM, KlassToken.PRINTLN, new Println());
        put(KlassToken.SYSTEM, KlassToken.PRINT_TAB, new PrintTab());
        put(KlassToken.SYSTEM, KlassToken.PRINT_SPACE, new PrintSpace());
        put(KlassToken.SYSTEM, KlassToken.QUIT, new Quit());
        put(KlassToken.SYSTEM, KlassToken.SLEEP, new Sleep());
    }};
    WorkMap<LoopWork> loopWorks = new WorkMap<>() {{
        put(KlassToken.SYSTEM, KlassToken.WHILE, new While());
        put(KlassToken.SYSTEM, KlassToken.TRY, new Try());
    }};
}
