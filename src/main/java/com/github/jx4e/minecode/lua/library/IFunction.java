package com.github.jx4e.minecode.lua.library;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/**
 * An interface which recieves an argument, performs a function then returns a value of type T
 * @author Jake
 * @since 02/03/2022
 **/

public interface IFunction<T> {
    T executesAndReturns(Varargs varargs);
}
