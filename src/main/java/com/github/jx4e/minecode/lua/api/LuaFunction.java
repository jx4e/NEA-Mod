package com.github.jx4e.minecode.lua.api;

import org.luaj.vm2.LuaValue;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public class LuaFunction {
    private final String name;
    private Class<?>[] params;
    private IFunction<LuaValue> function;

    public LuaFunction(String name, Class<?>[] params, IFunction<LuaValue> function) {
        this.name = name;
        this.params = params;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public IFunction<LuaValue> getFunction() {
        return function;
    }

    public LuaValue execute(LuaValue... params) {
        if (this.params.length != params.length) System.err.println("Invalid No of Params, has: " + params.length + " needs: " + this.params.length);

        return function.executesAndReturns(params);
    }
}
