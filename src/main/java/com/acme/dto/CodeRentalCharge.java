package com.acme.dto;

import com.acme.model.LuBrand;
import com.acme.model.LuCode;
import com.acme.model.LuType;
import com.acme.model.LuYesNo;
import lombok.Data;

/**
 * CodeRentalCharge Class defines the result of join between AvailableTool and RentalCharge tables. It transfers data from
 * Repository layer to Service layer.
 */
@Data
public class CodeRentalCharge {
    private LuCode.Name toolCode;
    private LuType.Name toolType;
    private LuBrand.Name toolBrand;
    private Double dailyCharge;
    private LuYesNo.Name weekdayCharge;
    private LuYesNo.Name weekendCharge;
    private LuYesNo.Name holidayCharge;
}
