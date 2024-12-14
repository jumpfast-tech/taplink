package tech.jumpfast.taplink.helpers;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryCodeHelper {
    public static List<String> getCountryCodes() {
        List<String> countryCodes = new ArrayList<>();
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        for (String region : phoneNumberUtil.getSupportedRegions()) {
            int countryCode = phoneNumberUtil.getCountryCodeForRegion(region);
            String countryName = new Locale("", region).getDisplayCountry();
            countryCodes.add("+" + countryCode + " (" + countryName + ")");
        }
        return countryCodes;
    }

    public static String getRegionFromCountryCode(String countryCode) {
        // Extract numeric part of the country code
        String numericCode = countryCode.split(" ")[0].replace("+", "");

        // Extract the region code (e.g., "IN") from the country code dropdown
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        return phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(numericCode));
    }

    public static boolean validatePhoneNumber(String phoneNumber, String region) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, region);
            return phoneNumberUtil.isValidNumber(parsedNumber);
        } catch (Exception e) {
            return false;
        }
    }
}
