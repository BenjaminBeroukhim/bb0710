package com.acme.repository;

import com.acme.dto.CodeRentalCharge;
import com.acme.model.LuCode;

public interface RentalToolRepository {
    public CodeRentalCharge findByCode(LuCode.Name code);
}
