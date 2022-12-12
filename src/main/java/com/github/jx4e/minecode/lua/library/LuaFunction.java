package com.github.jx4e.minecode.lua.library;

import com.github.jx4e.minecode.lua.library.IFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public class LuaFunction {
    private final String name;
    private IFunction<LuaValue> function;

    public LuaFunction(String name, IFunction<LuaValue> function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public LuaValue execute(Varargs varargs) {
        return function.executesAndReturns(varargs);
    }
}
