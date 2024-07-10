package com.acme.model;
import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RentalCharge Class defines the Charge definitions for each Tool Type. It is Data table. List<RentalCharge> holds the row data;
 * rentalCharge_typeMap is the index which maps a PK ToolType to row number. Method addRowData checks for PK constraint
 */
@Data
public class RentalCharge {
    private LuType.Name toolType;
    private Double dailyCharge;
    private LuYesNo.Name weekdayCharge;
    private LuYesNo.Name weekendCharge;
    private LuYesNo.Name holidayCharge;

    private List<RentalCharge> rentalChargeList  = new ArrayList<>();

    private int row = 0;
    private Map<LuType.Name, Integer> rentalCharge_typeMap = new HashMap<>();

    public RentalCharge() {}

    public RentalCharge(LuType.Name toolType, double dailyCharge, LuYesNo.Name weekdayCharge, LuYesNo.Name weekendCharge, LuYesNo.Name holidayCharge ) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public void addRow(LuType.Name toolType, double dailyCharge, LuYesNo.Name weekdayCharge, LuYesNo.Name weekendCharge, LuYesNo.Name holidayCharge ) {

        //Check PK constraint
        if (rentalCharge_typeMap.containsKey(toolType)) {
            throw new IllegalArgumentException("Violation of PRIMARY KEY constraint - Cannot insert duplicate key [" + toolType + "] in RentalCharge");
        }

        //Add to Data
        rentalCharge_typeMap.put(toolType, row);

        row++;

        //Add to Index
        rentalChargeList.add(new RentalCharge(toolType, dailyCharge, weekdayCharge, weekendCharge, holidayCharge));
    }
}
