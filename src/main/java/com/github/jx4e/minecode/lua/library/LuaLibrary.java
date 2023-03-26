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

public class LuaLibrary extends TwoArgFunction {
    /**
     * Name of the library
     */
    private String name;

    /**
     * The functions the library has
     */
    private LuaFunction[] functions;

    public LuaLibrary(String name, LuaFunction[] functions) {
        this.name = name;
        this.functions = functions;
    }

    /**
     * This is called when the library is imported
     * @param modname
     * @param env - the environment
     * @return this library with all the functions
     */
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

    /**
     * The function class which will be called
     */
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
