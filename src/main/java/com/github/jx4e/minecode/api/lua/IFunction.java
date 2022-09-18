package com.github.jx4e.minecode.api.lua;

/**
 * @author Jake
 * @since 02/03/2022
 **/

public interface IFunction<T> {
    T executesAndReturns(Object... params);
}
