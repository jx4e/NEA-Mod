package com.github.jx4e.minecode.lua;

import org.luaj.vm2.LuaValue;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public class LuaFunction {
    private final String NAME;
    private Class<?>[] params;
    private IFunction<LuaValue> function;

    public LuaFunction(String name, Class<?>[] params, IFunction<LuaValue> function) {
        this.NAME = name;
        this.params = params;
        this.function = function;
    }

    public String getName() {
        return NAME;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public IFunction<LuaValue> getFunction() {
        return function;
    }

    public LuaValue execute(LuaValue... params) {
        if (this.params.length != params.length) System.err.println(params.length);

        Object[] changed = new Object[params.length];
        for (int i = 0; i < this.params.length; i++) {
            if (this.params[i] == String.class) {
                changed[i] = params[i].toString();
            } else if (this.params[i] == Double.class) {
                changed[i] = params[i].todouble();
            }
        }

        return function.executesAndReturns(changed);
    }
}
