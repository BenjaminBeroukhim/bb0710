package com.acme.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * LuBrand is Lookup table. LuBrand provides description for Brand in Data tables. The columns are defined by enum variables.
 * The enum guarantees PK Constraint and referential integrity when used by Data tables (AvailableTool, RentalCharge)
 */
@Data
public class LuBrand {

    public enum Name
    {
        Stihl, Werner, DeWalt, Ridgid;
    };
}
