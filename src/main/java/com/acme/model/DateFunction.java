package com.acme.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * DateFunction Class defines date functions for the app. The class is defined as part of Model layer, so it
 * can be updated external to the application. It is Analogous to functions in Database.
 * For simplicity the methods are defined as static and belong to the class. The class is not instantiated.
 */
public class DateFunction {
    //Calculate number of days betweeen two days
    public static long calcWeekDays(final LocalDate start, final LocalDate end) {
        final DayOfWeek startW = start.getDayOfWeek();
        final DayOfWeek endW = end.getDayOfWeek();

        final long days = ChronoUnit.DAYS.between(start, end);
        final long daysWithoutWeekends = days - 2 * ((days + startW.getValue())/7);

        //adjust for starting and ending on a Sunday:
        return daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (endW == DayOfWeek.SUNDAY ? 1 : 0);
    }

    //Calculate number of holidays between two days
    public static int calcHolidaysBetween(LocalDate startDate, LocalDate endDate) {

        LocalDate independenceDay;
        LocalDate laborDay;
        int holidays = 0;
        LocalDate currDate = startDate;
        while (!currDate.isAfter(endDate)) {

            //Calculate the Independence day and labor day for each year and check if it is between startDate and endDate
            int year = currDate.getYear();
            independenceDay = independenceDayObserved(year);
            laborDay = laborDayObserved(year);
            if (independenceDay.isEqual(currDate) || (independenceDay.isAfter(startDate) &&
                    independenceDay.isBefore(endDate))) {
                holidays++;
            }
            if (laborDay.isEqual(currDate) || (laborDay.isAfter(startDate) &&
                    laborDay.isBefore(endDate))) {
                holidays++;
            }
            //Move to next year
            currDate = currDate.plusYears(1);

        }
        return holidays;
    }

    //Calulate the Independence Day Observed
    //Independence Day, July 4th - If falls on weekend, it is observed on the closest weekday (if Sat,
    //then Friday before, if Sunday, then Monday after)
    public static LocalDate independenceDayObserved (int nYear)
    {
        int nMonth = 7; // July
        LocalDate dtD = LocalDate.of(nYear, nMonth, 4);
        DayOfWeek nX = dtD.getDayOfWeek();
        switch(nX)
        {
            case SUNDAY:
                return  LocalDate.of(nYear, nMonth, 5);
            case MONDAY :
            case TUESDAY :
            case WEDNESDAY:
            case THURSDAY :
            case FRIDAY :
                return LocalDate.of(nYear, nMonth, 4);
            default :
                // Saturday
                return LocalDate.of(nYear, nMonth, 3);
        }
    }

    // calculate Labor Day Observed
    // Labor Day - First Monday in September
    public static LocalDate laborDayObserved (int nYear)
    {
        // The first Monday in September
        int nMonth = 9; // September
        LocalDate dtD = LocalDate.of(nYear, 9, 1);
        DayOfWeek nX = dtD.getDayOfWeek();
        switch(nX)
        {
            case SUNDAY :
                return  LocalDate.of(nYear, nMonth, 2);
            case MONDAY :
                return LocalDate.of(nYear, nMonth, 1);
            case TUESDAY :
                return LocalDate.of(nYear, nMonth, 7);
            case WEDNESDAY :
                return LocalDate.of(nYear, nMonth, 6);
            case THURSDAY :
                return LocalDate.of(nYear, nMonth, 5);
            case FRIDAY :
                return LocalDate.of(nYear, nMonth, 4);
            default :
                // Saturday
                return LocalDate.of(nYear, nMonth, 3);
        }
    }
}
