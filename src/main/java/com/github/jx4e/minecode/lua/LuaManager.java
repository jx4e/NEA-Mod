package com.github.jx4e.minecode.lua;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import com.github.jx4e.minecode.lua.impl.events.ScriptLoadEvent;
import com.github.jx4e.minecode.lua.impl.libs.LuaColorLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaDrawLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaInputLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaTextLibrary;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class LuaManager {
    private Globals globals;
    private final List<LuaScript> scripts = new LinkedList<>();

    private LuaManager() {}

    /**
     * Initialise the lua env and add all of the libraries
     */
    public void init() {
        globals = JsePlatform.standardGlobals();

        globals.load(new LuaTextLibrary());
        globals.load(new LuaInputLibrary());
        globals.load(new LuaDrawLibrary());
        globals.load(new LuaColorLibrary());

        // create a global lua variable
        getGlobals().set("mc", CoerceJavaToLua.coerce(mc));
    }

    /**
     * Loads a script to the environment
     * @param script
     */
    public void loadScript(LuaScript script) {
        if (!scripts.contains(script)) {
            try {
                scripts.add(script);
                LuaValue lv = getGlobals().load(script.getContent());

                lv.call();

                script.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a script and calls the "Exit" event
     * @param script
     */
    public void unloadScript(LuaScript script) {
        if (scripts.contains(script)) {
            scripts.remove(script);
            script.invoke("Exit", null);
            script.setEnabled(false);
        }
    }

    public void postEvent(LuaEvent event) {
        // Sort the list so we have the lowest priority first
        mergeSort(scripts, scripts.size());
        // Reverse the list so we have the highest priority first
        Collections.reverse(scripts);
        // Invoke the events
        scripts.forEach(script -> {
            script.invoke(event);
        });
    }

    public void postEvent(Object object) {
        // Sort the list so we have the lowest priority first
        mergeSort(scripts, scripts.size());
        // Reverse the list so we have the highest priority first
        Collections.reverse(scripts);
        // Invoke the events
        scripts.forEach(script -> {
            script.invoke(object);
        });
    }

    private static void mergeSort(List<LuaScript> list, int len) {
        // If the list has 1 element no need to sort so we return
        if (len < 2) return;

        int mid = len / 2;
        List<LuaScript> left = new LinkedList<>();
        List<LuaScript> right = new LinkedList<>();

        // First half into left
        for (int i = 0; i < mid; i++) {
            left.set(i, list.get(i));
        }

        // Second half into right
        for (int i = mid; i < len; i++) {
            left.set(i - mid, list.get(i));
        }

        // Recursively sort the split lists
        mergeSort(left, mid);
        mergeSort(right, len - mid);

        // Sort the items
        int l = 0, r = 0, a = 0; // l = left index, r = right index, a = list index
        while (l < mid && r < len - mid) {
            if (left.get(l).getPriority() <= right.get(r).getPriority()) {
                list.set(a++, left.get(l++));
            } else {
                list.set(a++, right.get(r++));
            }
        }

        // Add any remaining on the left
        while (l < mid) {
            list.set(a++, left.get(l++));
        }

        // Add any remaining on the right
        while (r < len - mid) {
            list.set(a++, right.get(r++));
        }
    }

    public Globals getGlobals() {
        return globals;
    }

    public List<LuaScript> getScripts() {
        return scripts;
    }

    private static LuaManager instance;

    public static LuaManager instance() {
        if (instance == null) instance = new LuaManager();

        return instance;
    }
}
