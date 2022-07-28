/**
 * @author Jake (github.com/jx4e)
 * @since 10/06/2022
 **/

public class LuaUtil {
    public Enum<?> enumVal(Enum<?> target, String value) {
        Enum<?>[] enumArray = target.getDeclaringClass().getEnumConstants();

        for (Enum<?> e : enumArray) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }

        return target;
    }
}
