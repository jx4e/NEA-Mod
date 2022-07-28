package com.github.jx4e.minecode.api.lua.library;

import com.github.jx4e.minecode.api.lua.function.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public abstract class LuaLibrary extends TwoArgFunction {
    private String name;
    private LuaFunction[] functions;

    public LuaLibrary(String name, LuaFunction[] functions) {
        this.name = name;
        this.functions = functions;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();

        for (LuaFunction function : functions) {
            library.set(function.getName(), new Function(function));
        }

        env.set(name, library);
        env.get("package").get("loaded").set(name, library);
        return library;
    }

    static class Function extends VarArgFunction {
        private LuaFunction function;

        public Function(LuaFunction function) {
            this.function = function;
        }

        @Override
        public LuaValue call() {
            return function.execute();
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return function.execute(luaValue);
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
            return function.execute(luaValue, luaValue1);
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1, LuaValue luaValue2) {
            return function.execute(luaValue, luaValue1, luaValue2);
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1, LuaValue luaValue2, LuaValue luaValue3) {
            return function.execute(luaValue, luaValue1, luaValue2, luaValue3);
        }
    }
}
