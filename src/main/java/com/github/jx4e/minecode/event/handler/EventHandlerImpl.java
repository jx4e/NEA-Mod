package com.github.jx4e.minecode.event.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventHandlerImpl implements EventHandler {
    private final Map<Class<?>, Map<Object, List<Method>>> subscriptionMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Map<Class<?>, List<Method>>> syntheticSubscriptionMap = new ConcurrentHashMap<>();

    private final List<Object> registeredObjectList = new ArrayList<>();
    private final List<Class<?>> registeredClassList = new ArrayList<>();

    private final List<EventHandler> attachedHandlerList = new ArrayList<>();

    @Override
    public void register(Object object) {
        if (!registeredObjectList.contains(object)) {
            Map<Class<?>, List<Method>> methodListMap = new ConcurrentHashMap<>();

            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Listener.class) && method.getParameterCount() == 1 && method.getReturnType().isAssignableFrom(void.class) && !Modifier.isStatic(method.getModifiers())) {
                    methodListMap.putIfAbsent(method.getParameterTypes()[0], new ArrayList<>());

                    method.setAccessible(true);

                    methodListMap.get(method.getParameterTypes()[0]).add(method);
                }
            }

            for (Map.Entry<Class<?>, List<Method>> entry : methodListMap.entrySet()) {
                subscriptionMap.putIfAbsent(entry.getKey(), new ConcurrentHashMap<>());

                entry.setValue(entry.getValue().stream().sorted(Comparator.comparing(method -> -method.getDeclaredAnnotation(Listener.class).priority())).collect(Collectors.toList()));

                subscriptionMap.get(entry.getKey()).put(object, entry.getValue());
            }

            registeredObjectList.add(object);
        }
    }

    @Override
    public void unregister(Object object) {
        if (registeredObjectList.contains(object)) {
            for (Class<?> clazz : subscriptionMap.keySet())
                subscriptionMap.get(clazz).remove(object);

            registeredObjectList.remove(object);
        }
    }

    @Override
    public void register(Class<?> clazz) {
        if (!registeredClassList.contains(clazz)) {
            Map<Class<?>, List<Method>> methodListMap = new ConcurrentHashMap<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Listener.class) && method.getParameterCount() == 1 && method.getReturnType().isAssignableFrom(void.class) && Modifier.isStatic(method.getModifiers())) {
                    methodListMap.putIfAbsent(method.getParameterTypes()[0], new ArrayList<>());

                    method.setAccessible(true);

                    methodListMap.get(method.getParameterTypes()[0]).add(method);
                }
            }

            for (Map.Entry<Class<?>, List<Method>> entry : methodListMap.entrySet()) {
                syntheticSubscriptionMap.putIfAbsent(entry.getKey(), new ConcurrentHashMap<>());

                entry.setValue(entry.getValue().stream().sorted(Comparator.comparing(method -> -method.getDeclaredAnnotation(Listener.class).priority())).collect(Collectors.toList()));

                syntheticSubscriptionMap.get(entry.getKey()).put(clazz, entry.getValue());
            }

            registeredClassList.add(clazz);
        }
    }

    @Override
    public void unregister(Class<?> clazz) {}

    @Override
    public boolean isRegistered(Object object) {
        return registeredObjectList.contains(object);
    }

    @Override
    public boolean isRegistered(Class<?> clazz) {
        return false;
    }

    @Override
    public void attach(EventHandler handler) {
        if (!attachedHandlerList.contains(handler)) attachedHandlerList.add(handler);
    }

    @Override
    public void detach(EventHandler handler) {
        attachedHandlerList.remove(handler);
    }

    @Override
    public boolean isAttached(EventHandler handler) {
        return attachedHandlerList.contains(handler);
    }

    @Override
    public <T> void post(T object) {
        if (subscriptionMap.containsKey(object.getClass())) {
            for (Map.Entry<Object, List<Method>> entry : subscriptionMap.get(object.getClass()).entrySet()) {
                for (Method method : entry.getValue()) {
                    try {
                        method.invoke(entry.getKey(), object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (syntheticSubscriptionMap.containsKey(object.getClass())) {
            for (Map.Entry<Class<?>, List<Method>> entry : syntheticSubscriptionMap.get(object.getClass()).entrySet()) {
                for (Method method : entry.getValue()) {
                    try {
                        method.invoke(null, object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!attachedHandlerList.isEmpty()) attachedHandlerList.forEach(handler -> handler.post(object));
    }
}
