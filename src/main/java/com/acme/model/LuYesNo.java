package com.acme.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
/**
 * LuYesNo is Lookup table. LuYesNo provides description for Yes/No Columns in Data Tables. The columns are defined by enum
 * variables. The enum guarantees PK Constraint and referential integrity when used by Data tables (AvailableTool, RentalCharge)
 */
@Data
public class LuYesNo {
    public enum Name
    {
        Yes, No;
    };
}
