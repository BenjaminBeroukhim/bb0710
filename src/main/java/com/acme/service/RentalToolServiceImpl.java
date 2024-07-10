package com.acme.service;

import com.acme.dto.CodeRentalCharge;
import com.acme.dto.RentalAgreement;
import com.acme.model.DateFunction;
import com.acme.model.LuCode;
import com.acme.model.LuYesNo;
import com.acme.repository.RentalToolRepositoryImpl;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * RentalToolService encapsulates the App's business logic into a single place. It uses the Repository to access
 * data to implement the business logic. It is called by Main Class.
 */
@Data
public class RentalToolServiceImpl implements RentalToolService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private RentalToolRepositoryImpl rentalToolRepository;

    public RentalToolServiceImpl() {}

    //Main acting as IOC injects the reference to the Repository layer
    public RentalToolServiceImpl(RentalToolRepositoryImpl rentalToolRepository) {
        this.rentalToolRepository = rentalToolRepository;
    }

    //Generate the RentalAgreement logic by accessing the Repository.
    @Override
    public RentalAgreement generateRentalAgreementByCode(String toolCode, int rentalDayCount, int discountPercent,
                                                         LocalDate checkOutDate) {

        //get the result of the join of the tables from the Repository layer.
        LuCode.Name codeName = LuCode.Name.valueOf(toolCode);
        CodeRentalCharge codeRentalCharge = rentalToolRepository.findByCode(codeName);

        //Date definitions
        //LocalDate today = LocalDate.of(2024, 7, 7);
        //LocalDate today = LocalDate.now();
        LocalDate startDate = checkOutDate.plusDays(1);
        LocalDate endDate = checkOutDate.plusDays(rentalDayCount+1);

        // Calculate different days counts
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
        long weekDays = DateFunction.calcWeekDays(startDate, endDate);
        long weekends = rentalDays - weekDays;
        long holidays =  DateFunction.calcHolidaysBetween(startDate, endDate);

        //Populate the rentalAgreement object
        RentalAgreement rentalAgreement  = new RentalAgreement();
        rentalAgreement.setToolCode(toolCode);
        rentalAgreement.setToolType(codeRentalCharge.getToolType().name());
        rentalAgreement.setToolBrand(codeRentalCharge.getToolBrand().name());
        rentalAgreement.setRentalDays(Long.toString(rentalDays));
        rentalAgreement.setCheckOutDate(checkOutDate.format(formatter));

        //Calculate the Charge by looking at different charge definitions.
        rentalAgreement.setDueDate(endDate.format(formatter));
        rentalAgreement.setDailyRentalCharge(String.format("%.2f",codeRentalCharge.getDailyCharge()));
        long chargeDays = 0;
        rentalAgreement.setHolidayCharge(true);
        rentalAgreement.setWeekendCharge(false);
        if (codeRentalCharge.getWeekdayCharge() == LuYesNo.Name.Yes) {
            chargeDays = weekDays;
            rentalAgreement.setHolidayCharge(false);
        }
        if (codeRentalCharge.getHolidayCharge() == LuYesNo.Name.No) {
            chargeDays -=  holidays;
            rentalAgreement.setWeekendCharge(true);
        }
        if (codeRentalCharge.getWeekendCharge() == LuYesNo.Name.Yes) {
            chargeDays += weekends;
        }
        rentalAgreement.setChargeDays(Long.toString(chargeDays));

        //Calculate the discountAmouont and the finalCharge
        Double preDiscountCharge = codeRentalCharge.getDailyCharge() * chargeDays;
        rentalAgreement.setPreDiscountCharge(String.format("%.2f",preDiscountCharge));

        rentalAgreement.setDiscountPercent(Integer.toString(discountPercent));

        Double discountAmount = (Double)(discountPercent/100.0) * preDiscountCharge;
        discountAmount = (int)(Math.round(discountAmount * 100))/100.0;
        rentalAgreement.setDiscountAmount(String.format("%.2f", discountAmount));
        Double finalCharge = (int)(Math.round((preDiscountCharge - discountAmount) * 100))/100.0;
        rentalAgreement.setFinalCharge(String.format("%.2f",finalCharge));

        return rentalAgreement;
    }
}
