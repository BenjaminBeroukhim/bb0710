package com.acme.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class RentalChargeTest {
    private RentalCharge rentalCharge = new RentalCharge();

    // Test adding a valid row to the RentalCharge list
    @Test
    public void testValidAddRow() {
        LuType.Name type = LuType.Name.Chainsaw;
        double dailyCharge = 25;
        LuYesNo.Name weekdayCharge = LuYesNo.Name.Yes;
        LuYesNo.Name weekendCharge = LuYesNo.Name.No;
        LuYesNo.Name holidayCharge = LuYesNo.Name.No;
        rentalCharge.addRow(type, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);

        assertEquals(1, rentalCharge.getRentalChargeList().size());
        assertEquals(1, rentalCharge.getRentalCharge_typeMap().size());
        assertTrue(rentalCharge.getRentalCharge_typeMap().containsKey(type));
    }
    
    // Test adding a duplicate row to the RentalCharge list
    @Test
    public void testDuplicateAddRow() {
        LuType.Name type = LuType.Name.Chainsaw;
        double dailyCharge = 25;
        LuYesNo.Name weekdayCharge = LuYesNo.Name.Yes;
        LuYesNo.Name weekendCharge = LuYesNo.Name.No;
        LuYesNo.Name holidayCharge = LuYesNo.Name.No;
        rentalCharge.addRow(type, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> rentalCharge.addRow(type, dailyCharge, weekdayCharge, weekendCharge, holidayCharge));
        assertEquals("Violation of PRIMARY KEY constraint - Cannot insert duplicate key [" + type + "] in RentalCharge", e.getMessage());
    }

    // Test adding multiple different rows to the RentalCharge list
    @Test
    public void testMultipleAddRows() {
        LuType.Name type_1 = LuType.Name.Chainsaw;
        double dailyCharge_1 = 25;
        LuYesNo.Name weekdayCharge_1 = LuYesNo.Name.Yes;
        LuYesNo.Name weekendCharge_1 = LuYesNo.Name.No;
        LuYesNo.Name holidayCharge_1 = LuYesNo.Name.No;

        LuType.Name type_2 = LuType.Name.Ladder;
        double dailyCharge_2 = 15;
        LuYesNo.Name weekdayCharge_2 = LuYesNo.Name.Yes;
        LuYesNo.Name weekendCharge_2 = LuYesNo.Name.Yes;
        LuYesNo.Name holidayCharge_2 = LuYesNo.Name.No;

        rentalCharge.addRow(type_1, dailyCharge_1, weekdayCharge_1, weekendCharge_1, holidayCharge_1);
        rentalCharge.addRow(type_2, dailyCharge_2, weekdayCharge_2, weekendCharge_2, holidayCharge_2);

        assertEquals(2, rentalCharge.getRentalChargeList().size());
        assertEquals(2, rentalCharge.getRentalCharge_typeMap().size());
        assertTrue(rentalCharge.getRentalCharge_typeMap().containsKey(type_1));
        assertTrue(rentalCharge.getRentalCharge_typeMap().containsKey(type_2));
    }
  
}
