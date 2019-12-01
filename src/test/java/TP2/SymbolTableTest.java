package TP2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import TP2.ASD.Types.Int;
import TP2.SymbolTable.FunctionSymbol;
import TP2.SymbolTable.Symbol;
import TP2.SymbolTable.SymbolTable;
import TP2.SymbolTable.VariableSymbol;

public class SymbolTableTest
{
    @Test
    public void testLookupEmpty()
    {
        SymbolTable table = new SymbolTable();

        assertNull(table.lookup("unknown"));
    }

    @Test
    public void testSimple()
    {
        SymbolTable table = new SymbolTable();
        Symbol symbol = new VariableSymbol(new Int(), "key");

        assertTrue(table.add(symbol));

        assertNull(table.lookup("unknown"));
        assertNotNull(table.lookup(symbol.getIdent()));
        assertEquals(table.lookup(symbol.getIdent()), symbol);

        assertTrue(table.remove(symbol.getIdent()));
        assertFalse(table.remove(symbol.getIdent()));

        assertNull(table.lookup(symbol.getIdent()));
    }

    @Test
    public void testParent()
    {
        SymbolTable parent = new SymbolTable();
        Symbol symbol = new VariableSymbol(new Int(), "key");

        assertTrue(parent.add(symbol));

        SymbolTable table = new SymbolTable();
        table.setParent(parent);

        Symbol symbol2 = new VariableSymbol(new Int(), "key2");

        assertTrue(table.add(symbol2));

        assertEquals(table.lookup(symbol2.getIdent()), symbol2);
        assertEquals(table.lookup(symbol.getIdent()), symbol); // in parent

        assertFalse(table.remove(symbol.getIdent())); // in parent
        assertTrue(table.remove(symbol2.getIdent()));
    }

    @Test
    public void testEquals()
    {
        SymbolTable table = new SymbolTable();
        SymbolTable table2 = new SymbolTable();
        
        Symbol symbol = new VariableSymbol(new Int(), "key");
        Symbol symbol2 = new VariableSymbol(new Int(), "key2");

        assertNotEquals(symbol, symbol2);
        assertEquals(table, table2);

        assertTrue(table.add(symbol));
        assertTrue(table2.add(symbol2));

        assertNotEquals(table, table2);

        assertTrue(table2.add(symbol));
        assertTrue(table.add(symbol2));

        assertEquals(table, table2);

        ArrayList<VariableSymbol> arguments = new ArrayList<VariableSymbol>();

        VariableSymbol arg0 = new VariableSymbol(new Int(), "arg0");
        VariableSymbol arg1 = new VariableSymbol(new Int(), "arg1");

        arguments.add(0, arg0);
        arguments.add(0, arg1);

        Symbol fun = new FunctionSymbol(new Int(), "fun", arguments, true);
        Symbol fun2 = new FunctionSymbol(new Int(), "fun2", new ArrayList<VariableSymbol>(), true);

        assertNotEquals(fun, fun2);
        assertTrue(table2.add(fun));
        assertNotEquals(table, table2);
    }
}
