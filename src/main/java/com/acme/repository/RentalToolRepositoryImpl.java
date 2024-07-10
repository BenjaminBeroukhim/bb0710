package com.acme.repository;

import com.acme.dto.CodeRentalCharge;
import com.acme.model.*;

/**
 * RentalToolRepository Class provides a level of abstraction over data access. It instantiates the Data tables
 * and populates each table. Method findBuCode() joins the two tables availableTool and rentalCharge by Column ToolType.
 * It uses the Tables' Indexes for fast access.
 */
public class RentalToolRepositoryImpl implements RentalToolRepository {

    private AvailableTool availableTool;
    private RentalCharge rentalCharge;
    public RentalToolRepositoryImpl() {
        //Instantiate the Data tables
        availableTool = new AvailableTool();
        rentalCharge =  new RentalCharge();

        //Populate the Data tables
        availableTool.addRow(LuCode.Name.CHNS, LuType.Name.Chainsaw, LuBrand.Name.Stihl);
        availableTool.addRow(LuCode.Name.LADW, LuType.Name.Ladder, LuBrand.Name.Werner);
        availableTool.addRow(LuCode.Name.JAKD, LuType.Name.Jackhammer, LuBrand.Name.DeWalt);
        availableTool.addRow(LuCode.Name.JAKR, LuType.Name.Jackhammer, LuBrand.Name.Ridgid);

        rentalCharge.addRow(LuType.Name.Ladder, 1.99, LuYesNo.Name.Yes, LuYesNo.Name.Yes, LuYesNo.Name.No);
        rentalCharge.addRow(LuType.Name.Chainsaw, 1.49, LuYesNo.Name.Yes, LuYesNo.Name.No, LuYesNo.Name.Yes);
        rentalCharge.addRow(LuType.Name.Jackhammer, 2.99, LuYesNo.Name.Yes, LuYesNo.Name.No, LuYesNo.Name.No);
    }

    //Join the two tables by ToolType.
    @Override
    public CodeRentalCharge findByCode(LuCode.Name code) {

        CodeRentalCharge codeRentalCharge = new CodeRentalCharge();

        //Find the row by Code
        int row = availableTool.getAvailableTool_codeMap().get(code);

        //Retrieve the row and get the TypeCode
        AvailableTool availableToolRow = availableTool.getAvailableToolList().get(row);
        LuType.Name luType = availableToolRow.getToolType();

        //Find the Row by the TypeCode
        row = rentalCharge.getRentalCharge_typeMap().get(luType);

        //Retrieve the row
        RentalCharge rentalChargeRow = rentalCharge.getRentalChargeList().get(row);

        //Populate the codeRentalCharge by the result of the Join
        codeRentalCharge.setToolCode(availableToolRow.getToolCode());
        codeRentalCharge.setToolType(availableToolRow.getToolType());
        codeRentalCharge.setToolBrand(availableToolRow.getToolBrand());
        codeRentalCharge.setDailyCharge(rentalChargeRow.getDailyCharge());
        codeRentalCharge.setWeekdayCharge(rentalChargeRow.getWeekdayCharge());
        codeRentalCharge.setWeekendCharge(rentalChargeRow.getWeekendCharge());
        codeRentalCharge.setHolidayCharge(rentalChargeRow.getHolidayCharge());

        return codeRentalCharge;
    }
}