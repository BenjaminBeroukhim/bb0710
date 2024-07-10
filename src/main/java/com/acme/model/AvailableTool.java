package com.acme.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AvailableTool Class defines the tools available for rental table. It is Data table. List<AvailableTool> holds the row data;
 * availableTool_codeMap is the index which maps a PK ToolCode to row number.  Method addRowData checks for PK constraint.
 */
@Data
public class AvailableTool {
    //Table Columns
    private LuCode.Name toolCode;
    private LuType.Name toolType;
    private LuBrand.Name toolBrand;

    //Table Data
    private List<AvailableTool> availableToolList  = new ArrayList<>();

    private int row = 0;

    //Table Index. References th PK to row number
    private Map<LuCode.Name, Integer> availableTool_codeMap = new HashMap<>();

    public AvailableTool() {}


    public AvailableTool(LuCode.Name toolCode, LuType.Name toolType, LuBrand.Name toolBrand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
    }

    //AddRow to the table
    public void addRow(LuCode.Name toolCode, LuType.Name toolType, LuBrand.Name toolBrand) {
        //Check PK constraint
        if (availableTool_codeMap.containsKey(toolCode)) {
            throw new IllegalArgumentException("Violation of PRIMARY KEY constraint - Cannot insert duplicate key [" + toolCode + "] in AvailableTool");
        }
        //Add to Data
        availableToolList.add(new AvailableTool(toolCode, toolType,toolBrand));

        //Add to Index
        availableTool_codeMap.put(toolCode, row);

        row++;
    }
}
