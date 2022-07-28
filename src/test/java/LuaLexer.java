import org.luaj.vm2.parser.Token;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 10/06/2022
 **/

public class LuaLexer {
    public void parse(String input) {
        StringBuilder output = new StringBuilder();
        int activeChar = 0;
        LuaToken activeToken = null;

        while (activeChar < input.length()) {
            char c = input.charAt(activeChar);


            activeChar++;
        }
    }

    private LuaToken findToken(char c) {
        if (c == '"') return LuaToken.DoubleQuotation;
        return null;
    }

    private enum LuaToken {
        DoubleQuotation,
        SingleQuotation
    }
}
