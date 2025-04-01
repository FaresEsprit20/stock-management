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


    // Code Validation
    public static final int MIN_CODE_LENGTH = 3;
    public static final int MAX_CODE_LENGTH = 50;
    public static final String CODE_REGEX = "^[a-zA-Z0-9-]+$";
    // Only alphanumeric characters and dashes

    // Designation Validation
    public static final int MIN_DESIGNATION_LENGTH = 3;
    public static final int MAX_DESIGNATION_LENGTH = 50;
    public static final String DESIGNATION_REGEX = "^[a-zA-ZÀ-ÿ\s-]+$";

    // First Name Validation
    public static final int MIN_FIRST_NAME_LENGTH = 2;
    public static final int MAX_FIRST_NAME_LENGTH = 20;
    public static final String FIRST_NAME_REGEX = "^[a-zA-ZÀ-ÿ\\s-]+$";

    // Last Name Validation
    public static final int MIN_LAST_NAME_LENGTH = 2;
    public static final int MAX_LAST_NAME_LENGTH = 20;
    public static final String LAST_NAME_REGEX = "^[a-zA-ZÀ-ÿ\\s-]+$";

    // Email Validation
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    // Phone Number Validation
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{8}$";
    // Assuming phone number should be 8 digits




}
