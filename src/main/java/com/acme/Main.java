package com.acme;
import com.acme.dto.RentalAgreement;
import com.acme.model.LuCode;
import com.acme.repository.RentalToolRepository;
import com.acme.repository.RentalToolRepositoryImpl;
import com.acme.service.RentalToolService;
import com.acme.service.RentalToolServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main class acts
 *  1) As IOC to instantiate the Service and Repository layers. It injects the Repository reference to the Service class.
 *  2) It is the GUI which retrieves the user data and calls Service class to present the results to the user.
 */
public class Main
{
    public static void main( String[] args )
    {
        //Instantiate the Service and Repository classes
        RentalToolRepositoryImpl rentalToolRepository = new RentalToolRepositoryImpl();
        RentalToolServiceImpl rentalToolService = new RentalToolServiceImpl(rentalToolRepository);

        //Start of GUI to get the values for ToolCode, RentalDayCount, DiscountPercent and CheckOutDate,
        System.out.println("***************************** TOOL RENTAL APPLICATION ******************************");
        boolean isExit = false;
        while (!isExit) {
            try {
                System.out.println("Please choose a Tool Code!");
                int i = 1;
                for (LuCode.Name code : LuCode.Name.values()) {
                    System.out.println(i + " -> " + code);
                    i++;
                }
                System.out.println("0 -> Exit");
                System.out.println("****************");

                Scanner input = new Scanner(System.in);
                int chosen = input.nextInt();
                if (chosen > LuCode.Name.values().length) {
                    System.out.println("Invalid selection, Please try again");
                    continue;
                }

                if (chosen == 0) {
                    isExit = true;
                }

                //Read ToolCode
                String toolCode = LuCode.Name.values()[chosen - 1].name();

                //Read RentalDayCount
                String prompt = "Please Enter Rental Day Count: ";
                int rentalDayCount = nextIntNotNegative(prompt, false);

                //Read DiscountPercent
                int discountPercent = -1;
                for(;;) {
                    prompt = "Please Enter Discount Percent (Value between 0 and 100): ";
                    discountPercent = nextIntNotNegative(prompt, true);
                    if (discountPercent > 100) {
                        System.out.println("Error: value greater than 100.");
                        continue;
                    }
                    break;
                }

                //Read CheckOutDate
                LocalDate today = LocalDate.now();
                String todayStr = today.format(DateTimeFormatter.ofPattern("M/d/yy" ));
                prompt = "Please Enter Check out date: (example: today's date " + todayStr + ")";
                LocalDate checkOutDate = nextStringValidDate(prompt);

                /*
                String toolCode = "JAKD";
                int rentalDayCount = 100 + 365;
                int discountPercent = 20 ;
                */
                //call the generateRentalAgreementByCode provided by Service
                RentalAgreement rentalAgreement = rentalToolService.generateRentalAgreementByCode(toolCode, rentalDayCount,
                    discountPercent, checkOutDate);

                System.out.println("***** RENTAL AGREEMENT *****");
                System.out.println("ToolCode: \"" + rentalAgreement.getToolCode() + "\"");
                System.out.println("ToolType: \"" + rentalAgreement.getToolType() + "\"");
                System.out.println("ToolBrand: \"" + rentalAgreement.getToolBrand() + "\"");
                System.out.println("RentalDays: " + rentalAgreement.getRentalDays());
                System.out.println("CheckOutDate: " + rentalAgreement.getCheckOutDate());
                System.out.println("DueDate: " + rentalAgreement.getDueDate());
                System.out.println("DailyRentalCharge: $" + rentalAgreement.getDailyRentalCharge());
                System.out.println("ChargeDays: " + rentalAgreement.getChargeDays());
                if (rentalAgreement.isWeekendCharge() == true && rentalAgreement.isHolidayCharge() == true) {
                    System.out.println("Info: Weekend and Holiday are charged");
                }
                if (rentalAgreement.isWeekendCharge() == true && rentalAgreement.isHolidayCharge() == false) {
                    System.out.println("Info: Weekends are charged but Holidays are not charged");
                }
                if (rentalAgreement.isWeekendCharge() == false && rentalAgreement.isHolidayCharge() == true) {
                    System.out.println("Info: Weekends are not charged but Holidays are charged");
                }
                if (rentalAgreement.isWeekendCharge() == false && rentalAgreement.isHolidayCharge() == false) {
                    System.out.println("Info: Weekends and Holidays are not charged");
                }
                System.out.println("PreDiscountCharge: $" + rentalAgreement.getPreDiscountCharge());
                System.out.println("DiscountPercent: " + rentalAgreement.getDiscountPercent());
                System.out.println("DiscountAmount: $" + rentalAgreement.getDiscountAmount());
                System.out.println("FinalCharge: $" + rentalAgreement.getFinalCharge());
            } //try
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        } //while
    }

    /*
        Read a positive Integer.
        prompt: prompt to the user
        includeZero: if zero is acceptable
     */
    public static int nextIntNotNegative(String prompt, boolean includeZero) {

        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                int n = Integer.parseInt(input.nextLine().trim());
                if (includeZero) {
                    if (n >= 0)
                        return n;
                    else
                        throw new IllegalArgumentException();
                }
                else {
                    if (n > 0)
                        return n;
                    else
                        throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid integer.");
            } catch (IllegalArgumentException iae) {
                System.out.println("Error: integer must be a positive and greater than 0.");
            }
        }
    }
    /*
        Read a valid date.
        prompt: prompt to the user
     */
    public static LocalDate nextStringValidDate(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                Scanner scanner = new Scanner(System.in);
                String dateStr = scanner.next();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                LocalDate date = LocalDate.parse(dateStr, formatter);
                LocalDate today = LocalDate.now();
                int compareValue = today.compareTo(date);
                if (compareValue > 0) {
                    System.out.println("Error: Checkout Date must be grater than today");
                } else {
                    return date;
                }
            }
            catch (DateTimeParseException e) {
                System.out.println("Error: Date must be format of dd/MM/yy");
            }
        }
    }
}
/*
            LocalDate IndependenceDay0 = Util.IndependenceDayObserved(2023);
            LocalDate IndependenceDay = Util.IndependenceDayObserved(2024);
            LocalDate IndependenceDay2 = Util.IndependenceDayObserved(2025);
            LocalDate IndependenceDay3 = Util.IndependenceDayObserved(2026);

            LocalDate laborDayObserved0 = Util.laborDayObserved(2023);
            LocalDate laborDayObserved1 = Util.laborDayObserved(2024);
            LocalDate laborDayObserved2 = Util.laborDayObserved(2025);
            LocalDate laborDayObserved3 = Util.laborDayObserved(2026);

*/

