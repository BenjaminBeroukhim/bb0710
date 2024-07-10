package com.acme.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class AvailableToolTest {

    // Test adding a valid row to the AvailableTool list
    @Test
    public void testValidAddRow() {
        AvailableTool availableTool = new AvailableTool();
        LuCode.Name code = LuCode.Name.LADW;
        LuType.Name type = LuType.Name.Ladder;
        LuBrand.Name brand = LuBrand.Name.Werner;
        availableTool.addRow(code, type, brand);

        assertEquals(1, availableTool.getAvailableToolList().size());
        assertEquals(1, availableTool.getAvailableTool_codeMap().size());
        assertTrue(availableTool.getAvailableTool_codeMap().containsKey(code));
    }
    
    // Test adding a duplicate row to the AvailableTool list
    @Test
    public void testDuplicateAddRow() {
        AvailableTool availableTool = new AvailableTool();
        LuCode.Name code = LuCode.Name.CHNS;
        LuType.Name type = LuType.Name.Chainsaw;
        LuBrand.Name brand = LuBrand.Name.DeWalt;
        availableTool.addRow(code, type, brand);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> availableTool.addRow(code, type, brand));
        assertEquals("Violation of PRIMARY KEY constraint - Cannot insert duplicate key [" + code + "] in AvailableTool", e.getMessage());
    }

    // Test adding multiple different rows to the AvailableTool list
    @Test
    public void testMultipleAddRows() {
        AvailableTool availableTool = new AvailableTool();
        LuCode.Name code_1 = LuCode.Name.CHNS;
        LuType.Name type_1 = LuType.Name.Chainsaw;
        LuBrand.Name brand_1 = LuBrand.Name.DeWalt;

        LuCode.Name code_2 = LuCode.Name.JAKD;
        LuType.Name type_2 = LuType.Name.Jackhammer;
        LuBrand.Name brand_2 = LuBrand.Name.DeWalt;

        availableTool.addRow(code_1, type_1, brand_1);
        availableTool.addRow(code_2, type_2, brand_2);

        assertEquals(2, availableTool.getAvailableToolList().size());
        assertEquals(2, availableTool.getAvailableTool_codeMap().size());
        assertTrue(availableTool.getAvailableTool_codeMap().containsKey(code_1));
        assertTrue(availableTool.getAvailableTool_codeMap().containsKey(code_2));
    }
}
