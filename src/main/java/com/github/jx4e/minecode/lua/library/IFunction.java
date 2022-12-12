package com.github.jx4e.minecode.lua.library;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public interface IFunction<T> {
    T executesAndReturns(Varargs varargs);
}
