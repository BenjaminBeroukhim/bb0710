package com.acme.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
/**
 * LuType is Lookup table. LuType provides description for Tool Type in Data Tables. The columns are defined by enum variables.
 * The enum guarantees PK Constraint and referential integrity when used by Data tables (AvailableTool, RentalCharge)
 */
@Data
public class LuType {
    public enum Name
    {
        Chainsaw, Ladder, Jackhammer;
    };
}

