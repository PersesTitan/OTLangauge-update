package cos.poison.work.method;

import bin.Repository;
import bin.apply.Read;
import bin.apply.item.VarPrimaryItem;
import bin.apply.item.VariableItem;
import bin.apply.loop.Loop;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.repository.TypeMap;
import bin.repository.code.CodeMap;
import bin.token.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.controller.HttpServerManager;
import cos.poison.etc.PoisonToken;
import cos.poison.etc.RexMap;
import cos.poison.item.PoisonItem;
import cos.poison.mode.DataType;
import cos.poison.mode.MethodMode;
import cos.poison.mode.ResponseType;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

@RequiredArgsConstructor
public class PoisonMethod extends PoisonWork {
    private final MethodMode methodMode;

    // [/a][html][ㅇㅁㅇ 변수명1][ㅇㅁㅇ 변수명2]
    @Override
    protected int loopItem(int start, int end, LoopMode mode, CodeMap code, String repoKlass,
                           Object klassValue, Object[] params) {
        PoisonItem poisonItem = (PoisonItem) klassValue;
        HttpServerManager server = poisonItem.getManager();
        final int len = params.length;
        if (len > 0) {
            int paramPos = -1;
            String path = params[0].toString();
            ResponseType responseType = poisonItem.getResponseType();
            DataType dataType = poisonItem.getDataType();
            for (int i = 1; i<len; i++) {
                String param = params[i].toString();    // ㅇㅁㅇ 변수명 or >HTML, <JSON
                if (CheckToken.startWith(param, PoisonToken.RESPONSE_TYPE)) {
                    // >HTML => HTML
                    String type = param.substring(1).toUpperCase(Locale.ROOT);
                    responseType = ResponseType.getType(type);
                } else if (CheckToken.startWith(param, PoisonToken.DATA_TYPE)) {
                    // <JSON => JSON
                    String type = param.substring(1).toUpperCase(Locale.ROOT);
                    dataType = DataType.getType(type);
                } else paramPos = i;
            }

            // responseData : (변수명, index.html, ...)
            String responseData = mode.getToken(code.get(end));
            // 데이터 타입 확인
            responseType.checkType(responseData);

            Types types = new Types(dataType, responseType, responseData);
            Codes codes = new Codes(path, code, start, end);
            if (paramPos != -1) {
                Set<String> set = new HashSet<>();

                List<VariableItem> variableItems = new ArrayList<>();
                List<VarPrimaryItem> varPrimaryItems = new ArrayList<>();
                for (int i = paramPos; i < len; i++) {
                    String[] token = params[i].toString().split("\\s", 2);
                    if (token.length != 2) throw MatchException.GRAMMAR_ERROR.getThrow(params[i].toString());
                    String type = token[0].strip();
                    String name = token[1].strip();
                    // 변수명이 중복되었는지 확인
                    if (set.contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
                    else set.add(name);

                    if (name.contains(Token.PUT)) {
                        String[] tokens = EditToken.split(name, Token.PUT);
                        varPrimaryItems.add(new VarPrimaryItem(type, tokens[0].strip(), tokens[1].strip()));
                    } else variableItems.add(new VariableItem(type, name));
                }

                VariableItem[] vars = variableItems.toArray(VariableItem[]::new);
                VarPrimaryItem[] vps = varPrimaryItems.toArray(VarPrimaryItem[]::new);
                Matcher matcher = RexMap.makeMather(path, vars);
                server.putRex(methodMode, matcher, createFunction(vars, vps, matcher, types, codes));
            } else {
                server.putMethod(methodMode, path, createFunction(null, null, null, types, codes));
            }
        } else throw MatchException.PARAM_COUNT_ERROR.getThrow(len);


//        ResponseType responseType = switch (params.length) {
//            case 0 -> throw MatchException.PARAM_COUNT_ERROR.getThrow(params.length);
//            case 1 -> poisonItem.getResponseType();
//            default -> ResponseType.getType(params[1].toString());
//        };
//        String path = Types.toString(params[0]);
//        DataType dataType = poisonItem.getDataType();
//        // responseData : (변수명, index.html, ...)
//        String responseData = mode.getToken(code.get(end));
//        // 데이터 타입 확인
//        responseType.checkType(responseData);
//
//        int len = params.length;
//        if (len > 2) {
//            Set<String> set = new HashSet<>();
//            VariableItem[] vars = new VariableItem[len-2];
//            for (int i = 2; i < len; i++) {
//                String[] token = params[i].toString().split("\\s", 2);
//                String type = token[0].strip();
//                String name = token[1].strip();
//                // 타입이 유효한지 확인
//                if (!CheckToken.isKlass(type)) throw VariableException.TYPE_ERROR.getThrow(type);
//                // 변수명이 중복되었는지 확인
//                if (set.contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
//                else set.add(name);
//                vars[i-2] = new VariableItem(type, name);
//            }
//
//            Matcher matcher = RexMap.makeMather(path, vars);
//            String[] hangle = Arrays.stream(vars)
//                    .map(VariableItem::name)
//                    .map(RexMap::castingHangle)
//                    .toArray(String[]::new);
//            server.putRex(this.methodMode, matcher, ((exchange, status) -> {
//                TypeMap repository = new TypeMap();
//                Map<String, Object> map = this.methodMode.getParams(exchange, dataType);
//                int size = hangle.length;
//                for (int i = 0; i < size; i++) repository.create(vars[i], matcher.group(hangle[i]));
//                try {
//                    Repository.repositoryArray.addFirst(repository);
//                    Read.read(code, start, end, null);
//                } finally {
//                    Loop.check(repository, Repository.repositoryArray.removeFirst());
//                }
//
//                exchange.getResponseHeaders().add("Content-Type", responseType.getMime());
//                this.methodMode.print(exchange.getRequestURI().getPath(), map.isEmpty() ? "" : map.toString());
//                return responseType.getBody(responseData);
//            }));
//        } else {
//            server.putMethod(this.methodMode, path, (exchange, statusCode) -> {
//                TypeMap repository = new TypeMap();
//                Map<String, Object> map = this.methodMode.getParams(exchange, dataType);
//                try {
//                    Repository.repositoryArray.addFirst(repository);
//                    Read.read(code, start, end, null);
//                } finally {
//                    Loop.check(repository, Repository.repositoryArray.removeFirst());
//                }
//
//                exchange.getResponseHeaders().add("Content-Type", responseType.getMime());
//                this.methodMode.print(path, map.isEmpty() ? "" : map.toString());
//                return responseType.getBody(responseData);
//            });
//        }

        return end;
    }

    private record Types(DataType dataType, ResponseType responseType, String responseData) {}
    private record Codes(String path, CodeMap code, int start, int end) {}

    private BiFunction<HttpExchange, AtomicInteger, Object> createFunction(
            VariableItem[] vars, VarPrimaryItem[] vps, Matcher matcher, Types types, Codes codes) {
        DataType dataType = types.dataType;
        ResponseType responseType = types.responseType;
        String responseData = types.responseData;
        CodeMap code = codes.code;
        String path = codes.path;
        int start = codes.start;
        int end = codes.end;
        if (vars == null) {
            return (exchange, status) -> {
                TypeMap repository = new TypeMap();
                Map<String, Object> map = this.methodMode.getParams(exchange, dataType);
                try {
                    Repository.repositoryArray.addFirst(repository);
                    Read.read(code, start, end, null);
                } finally {
                    Loop.check(repository, Repository.repositoryArray.removeFirst());
                }
                exchange.getResponseHeaders().add("Content-Type", responseType.getMime());
                this.methodMode.print(path, map.isEmpty() ? "" : map.toString());
                return responseType.getBody(responseData);
            };
        } else {
            String[] hangle = Arrays.stream(vars)
                    .map(VariableItem::name)
                    .map(RexMap::castingHangle)
                    .toArray(String[]::new);
            int size = hangle.length;
            return (exchange, status) -> {
                TypeMap repository = new TypeMap();
                Map<String, Object> map = this.methodMode.getParams(exchange, dataType);
                for (int i = 0; i < size; i++) repository.create(vars[i], matcher.group(hangle[i]));
                for (VarPrimaryItem vp : vps) {
                    String name = vp.name();
                    if (map.containsKey(name)) repository.create(vp.type(), name, map.get(name));
                    else repository.create(vp.type(), name, vp.primary());
                }
                try {
                    Repository.repositoryArray.addFirst(repository);
                    Read.read(code, start, end, null);
                } finally {
                    Loop.check(repository, Repository.repositoryArray.removeFirst());
                }

                exchange.getResponseHeaders().add("Content-Type", responseType.getMime());
                this.methodMode.print(exchange.getRequestURI().getPath(), map.isEmpty() ? "" : map.toString());
                return responseType.getBody(responseData);
            };
        }
    }
}
