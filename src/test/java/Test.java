import com.github.jx4e.minecode.api.lua.LuaApi;
import com.github.jx4e.minecode.api.lua.script.LuaScript;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author Jake (github.com/jx4e)
 * @since 10/06/2022
 **/

public class Test {
    public static void main(String[] args) {
        LuaApi.init();
        LuaApi.getGlobals().set("util", CoerceJavaToLua.coerce(new LuaUtil()));
        LuaScript script = new LuaScript("/enum.lua");
        LuaApi.loadScript(script);
        EnumTest event = new EnumTest();
        LuaApi.postEvent(event);
        System.out.println(event.value);
    }
}
