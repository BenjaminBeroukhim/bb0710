package com.acme;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import junit.framework.TestCase;
import org.junit.Test;


public class MainTest extends TestCase {
    String promptRental = "";
    String promptDisc = "";
    
    @Override
    protected void setUp() throws Exception {
        promptRental = "Please Enter Rental Day Count: ";
        promptDisc = "Please Enter Discount Percent (Value between 0 and 100): ";
    }
    
    // Test for valid input when includeZero is false
    @Test
    public void testValidRentalInputNextIntNotNegative_noIncludeZero() {
        int expectedValue = 3;
        ByteArrayInputStream byteStream = new ByteArrayInputStream("3".getBytes());
        System.setIn(byteStream);

        int result = Main.nextIntNotNegative(promptRental, false);
        assertEquals(expectedValue, result);
    }
    
    // Test for valid input when includeZero is true
    @Test
    public void testValidRentalInputNextIntNotNegative_withIncludeZero() {
        int expectedValue = 0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream("0".getBytes());
        System.setIn(byteStream);

        int result = Main.nextIntNotNegative(promptRental, true);
        assertEquals(expectedValue, result);
    }
    
    // Test for valid input when includeZero is false
    @Test
    public void testValidDiscInputNextIntNotNegative_noIncludeZero() {
        int expectedValue = 6;
        ByteArrayInputStream byteStream = new ByteArrayInputStream("6".getBytes());
        System.setIn(byteStream);

        int result = Main.nextIntNotNegative(promptDisc, false);
        assertEquals(expectedValue, result);
    }
    
    // Test for valid input when includeZero is true
    @Test
    public void testValidDiscInputNextIntNotNegative_withIncludeZero() {
        int expectedValue = 0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream("0".getBytes());
        System.setIn(byteStream);

        int result = Main.nextIntNotNegative(promptDisc, true);
        assertEquals(expectedValue, result);
    }
    
    // Test for valid date input
    @Test
    public void testValidDateInput() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate nowPlusTenDays = LocalDate.now();
        String nowPlusTenDaysStr = nowPlusTenDays.format(formatters);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(nowPlusTenDaysStr.getBytes());
        System.setIn(byteStream);

        LocalDate expectedDate = LocalDate.of(nowPlusTenDays.getYear(), nowPlusTenDays.getMonth(), nowPlusTenDays.getDayOfMonth());
        LocalDate result = Main.nextStringValidDate("Please Enter Check out date: (example: today's date " + expectedDate + ")");
        assertEquals(expectedDate, result);
    }
}
