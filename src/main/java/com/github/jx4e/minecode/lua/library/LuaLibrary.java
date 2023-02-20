package com.github.jx4e.minecode.lua.library;

import com.github.jx4e.minecode.lua.library.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
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
        public Varargs onInvoke(Varargs varargs) {
            return varargsOf(new LuaValue[]{function.execute(varargs)});
        }

        @Override
        public String name() {
            return function.getName();
        }
    }
}
