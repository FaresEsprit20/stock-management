package com.fares.stock.management.core.constants;

public class Constants {

    // Address Validation
    public static final int MIN_ADDRESS_LENGTH = 5;
    public static final int MAX_ADDRESS_LENGTH = 255;
    public static final String ADDRESS_CONTENT_REGEX = "^[a-zA-Z0-9\\s,.-/#]+$";

    // City Validation
    public static final int MIN_CITY_LENGTH = 2;
    public static final int MAX_CITY_LENGTH = 20;
    public static final String CITY_REGEX = "^[a-zA-ZÀ-ÿ\\s-]+$";

    // Zip Code Validation
    public static final int MIN_ZIPCODE_LENGTH = 4;
    public static final int MAX_ZIPCODE_LENGTH = 4;
    public static final String ZIP_CODE_REGEX = "^\\d+$";

    // Country Validation
    public static final int MIN_COUNTRY_LENGTH = 2;
    public static final int MAX_COUNTRY_LENGTH = 20;
    public static final String COUNTRY_REGEX = "^[a-zA-ZÀ-ÿ\\s]+$";




    
}
