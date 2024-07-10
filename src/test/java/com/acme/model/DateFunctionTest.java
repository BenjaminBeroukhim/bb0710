package com.acme.model;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateFunctionTest {

    // Test calculating weekdays for a range with no weekends
    @Test
    public void testCalcWeekDays_NoWeekends_InGivenRange() {
        LocalDate start = LocalDate.of(2024, 7, 8);
        LocalDate end = LocalDate.of(2024, 7, 13);
        long result = DateFunction.calcWeekDays(start, end);
        assertEquals(5, result);
    }
    
    // Test calculating weekdays for a range that includes weekends
    @Test
    public void testCalcWeekDays_WithWeekendDaysInGivenRange() {
        LocalDate start = LocalDate.of(2024, 7, 7);
        LocalDate end = LocalDate.of(2024, 7, 14);
        long result = DateFunction.calcWeekDays(start, end);
        assertEquals(5, result); 
    }

    // Test calculating holidays between dates in a range with two holidays
    @Test
    public void testCalcHolidaysBetween_WithTwoHolidaysInRange() {
        LocalDate start = LocalDate.of(2024, 7, 1);
        LocalDate end = LocalDate.of(2024, 9, 30);
        int result = DateFunction.calcHolidaysBetween(start, end);
        assertEquals(2, result);
    }
    
    // Test calculating weekdays for a range that starts and ends on a Sunday
    @Test
    public void testCalcWeekDays_RangeStartsAndEndsOnSunday() {
        LocalDate start = LocalDate.of(2024, 7, 7);
        LocalDate end = LocalDate.of(2024, 7, 14);
        long result = DateFunction.calcWeekDays(start, end);
        assertEquals(5, result);
    }
    
    // Test calculating holidays between dates with no holidays in the range
    @Test
    public void testCalcHolidaysBetween_WithNoHolidaysInRange() {
        LocalDate start = LocalDate.of(2024, 8, 1);
        LocalDate end = LocalDate.of(2024, 8, 31);
        int result = DateFunction.calcHolidaysBetween(start, end);
        assertEquals(0, result);
    }

    // Test observing Independence Day when it falls on a weekday
    @Test
    public void testIndependenceDayObserved_WhenOnWeekday() {
        LocalDate result = DateFunction.independenceDayObserved(2024);
        assertEquals(LocalDate.of(2024, 7, 4), result);
    }

    // Test observing Independence Day when it falls on a Saturday
    @Test
    public void testIndependenceDayObserved_WhenOnSaturday() {
        LocalDate result = DateFunction.independenceDayObserved(2025);
        assertEquals(LocalDate.of(2025, 7, 4), result);
    }

    // Test observing Labor Day in 2024
    @Test
    public void testLaborDayObserved_In2024() {
        LocalDate result = DateFunction.laborDayObserved(2024);
        assertEquals(LocalDate.of(2024, 9, 2), result);
    }

    // Test observing Labor Day in 2025
    @Test
    public void testLaborDayObserved_In2025() {
        LocalDate result = DateFunction.laborDayObserved(2025);
        assertEquals(LocalDate.of(2025, 9, 1), result);
    }

    // Test calculating weekdays for a range that crosses months
    @Test
    public void testCalcWeekDays_WithCrossMonthRange() {
        LocalDate start = LocalDate.of(2024, 6, 30);
        LocalDate end = LocalDate.of(2024, 7, 11);
        long result = DateFunction.calcWeekDays(start, end);
        assertEquals(8, result);
    }

    // Test calculating holidays between dates that span different years
    @Test
    public void testCalcHolidaysBetween_WithDifferentYears() {
        LocalDate start = LocalDate.of(2023, 12, 20);
        LocalDate end = LocalDate.of(2024, 1, 10);
        int result = DateFunction.calcHolidaysBetween(start, end);
        assertEquals(0, result);
    }

    // Test calculating weekdays for a long range spanning an entire year
    @Test
    public void testCalcWeekDays_WithLongRange() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        long result = DateFunction.calcWeekDays(start, end);
        assertEquals(261, result);
    }

    // Test calculating holidays in a leap year
    @Test
    public void testCalcHolidaysBetween_WithLeapYear() {
        LocalDate start = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2024, 2, 29);
        int result = DateFunction.calcHolidaysBetween(start, end);
        assertEquals(0, result);
    }

    // Test calculating weekdays for a single day
    @Test
    public void testCalcWeekDays_WithSingleDay() {
        LocalDate date = LocalDate.of(2024, 7, 10);
        long result = DateFunction.calcWeekDays(date, date);
        assertEquals(0, result);
    }
}
