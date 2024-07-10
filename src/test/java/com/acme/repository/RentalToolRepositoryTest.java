package com.acme.repository;

import com.acme.dto.CodeRentalCharge;
import com.acme.model.LuBrand;
import com.acme.model.LuCode;
import com.acme.model.LuType;
import com.acme.model.LuYesNo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RentalToolRepositoryTest {

    private RentalToolRepository rtr;

    @Before
    public void setUp() throws Exception {
        rtr = new RentalToolRepositoryImpl();
    }

    // Test finding a valid code and ensuring all fields are retrieved
    @Test
    public void testValidFindByCode() {
        LuCode.Name code = LuCode.Name.CHNS;

        CodeRentalCharge crc = rtr.findByCode(code);
        assertEquals(1.49, crc.getDailyCharge(), 0.01);
        assertEquals(LuYesNo.Name.Yes, crc.getHolidayCharge());
        assertEquals(LuBrand.Name.Stihl, crc.getToolBrand());
        assertEquals(LuType.Name.Chainsaw, crc.getToolType());
        assertEquals(LuYesNo.Name.Yes, crc.getWeekdayCharge());
        assertEquals(LuYesNo.Name.No, crc.getWeekendCharge());
    }

   
}
