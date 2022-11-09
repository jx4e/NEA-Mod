package com.github.jx4e.minecode.lua.api;

import org.luaj.vm2.LuaValue;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public interface IFunction<T> {
    T executesAndReturns(LuaValue... params);
}
