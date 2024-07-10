package com.acme.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * LuCode is Lookup table. LuCode provides description for Tool Code in Data. The columns are defined by enum variables.
 * The enum guarantees PK Constraint and referential integrity when used by Data tables (AvailableTool, RentalCharge)
 */
@Data
public class LuCode {
    public enum Name
    {
        CHNS, LADW, JAKD, JAKR;
    };
}
