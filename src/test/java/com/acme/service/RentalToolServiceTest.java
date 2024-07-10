package com.acme.service;

import com.acme.dto.CodeRentalCharge;
import com.acme.dto.RentalAgreement;
import com.acme.model.LuBrand;
import com.acme.model.LuCode;
import com.acme.model.LuType;
import com.acme.model.LuYesNo;
import com.acme.repository.RentalToolRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentalToolServiceTest {

    private RentalToolRepositoryImpl rentalToolRepository;
    private RentalToolService rentalToolService;
    private CodeRentalCharge rentalCharge;
    private RentalAgreement expectedRental;
    int rentalDays;
    int discounts;
    LocalDate checkOutDate;
    String toolCode;

    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Before
    public void setUp() {
        rentalToolRepository = mock(RentalToolRepositoryImpl.class);
        rentalToolService = new RentalToolServiceImpl(rentalToolRepository);

        LocalDate now = LocalDate.now();
        String nowStr = now.format(formatters);
        LocalDate nowPlusSixDays = now.plusDays(6);
        String nowPlusSixDaysStr = nowPlusSixDays.format(formatters);

        rentalCharge = new CodeRentalCharge();
        expectedRental = new RentalAgreement();
        toolCode = "JAKD";
        rentalDays = 5;
        discounts = 10;
        checkOutDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
        
        LuCode.Name code = LuCode.Name.JAKD;        
        rentalCharge.setToolCode(code);
        rentalCharge.setToolType(LuType.Name.Ladder);
        rentalCharge.setToolBrand(LuBrand.Name.DeWalt);
        rentalCharge.setDailyCharge(2.99);
        rentalCharge.setWeekdayCharge(LuYesNo.Name.Yes);
        rentalCharge.setWeekendCharge(LuYesNo.Name.Yes);
        rentalCharge.setHolidayCharge(LuYesNo.Name.No);
        when(rentalToolRepository.findByCode(code)).thenReturn(rentalCharge);
        
        expectedRental.setToolCode(toolCode);
        expectedRental.setToolType("Ladder");
        expectedRental.setToolBrand("DeWalt");
        expectedRental.setRentalDays("5");
        expectedRental.setCheckOutDate(nowStr);
        expectedRental.setDueDate(nowPlusSixDaysStr);
        expectedRental.setDailyRentalCharge("2.99");
        expectedRental.setChargeDays("5");
        expectedRental.setPreDiscountCharge("14.95");
        expectedRental.setDiscountPercent("10");
        expectedRental.setDiscountAmount("1.50");
        expectedRental.setFinalCharge("13.45");
        expectedRental.setHolidayCharge(false);
        expectedRental.setWeekendCharge(true);
    }
    
    // Test generating a valid rental agreement for a given code
    @Test
    public void testValidGenerateRentalAgreementByCode() {
        
        RentalAgreement result = rentalToolService.generateRentalAgreementByCode(toolCode, rentalDays, discounts, checkOutDate);

        Assert.assertEquals(expectedRental, result);
    }

    // Test generating a rental agreement with 100% discount
    @Test
    public void testGenerateRentalAgreementWithFullDiscount() {
        discounts = 100;
        RentalAgreement result = rentalToolService.generateRentalAgreementByCode(toolCode, rentalDays, discounts, checkOutDate);
        expectedRental.setDiscountPercent("100");
        expectedRental.setDiscountAmount("14.95");
        expectedRental.setFinalCharge("0.00");
        Assert.assertEquals(expectedRental, result);
    }
}
