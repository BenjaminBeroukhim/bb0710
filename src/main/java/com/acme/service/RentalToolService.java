package com.acme.service;

import com.acme.dto.RentalAgreement;

import java.time.LocalDate;

public interface RentalToolService {
    //Generate the RentalAgreement logic by accessing the Repository.
    RentalAgreement generateRentalAgreementByCode(String toolCode, int rentalDayCount, int discountPercent,
                                                  LocalDate checkOutDate);
}
