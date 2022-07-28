package com.github.jx4e.minecode.api.event.handler;

/**
 * @author yagel15637
 *
 * Handles events. {@link EventHandlerImpl} for a default implementation.
 */
public interface EventHandler {
    /**
     * Will register a non synthetic listener object.
     * @param object said object.
     */
    void register(Object object);

    /**
     * Will remove a non synthetic listener object.
     * @param object said object.
     */
    void unregister(Object object);

    /**
     * Will register a synthetic listener class.
     * @param clazz said class.
     */
    void register(Class<?> clazz);

    /**
     * Will remove a synthetic listener class.
     * @param clazz said class.
     */
    void unregister(Class<?> clazz);

    /**
     * @param object a non synthetic listener object to test whether is registered or not.
     * @return object is registered.
     */
    boolean isRegistered(Object object);

    /**
     * @param clazz a synthetic listener class to test whether is registered or not.
     * @return class is registered.
     */
    boolean isRegistered(Class<?> clazz);

    /**
     * Attaches another {@code IEventHandler} to this one, to call {@link #post(Object)} on it when it is called.
     * @param handler said {@code IEventHandler}
     */
    void attach(EventHandler handler);

    /**
     * Detach an {@code IEventHandler} from this one.
     * @param handler said {@code IEventHandler}
     */
    void detach(EventHandler handler);

    /**
     * @param handler {@code IEventHandler} to sample.
     * @return whether said {@code IEventHandler} is attached to this one or not.
     */
    boolean isAttached(EventHandler handler);

    /**
     * Posts an object through all registered objects.
     * @param object said object.
     * @param <T> the object's type.
     */
    <T> void post(T object);
}
