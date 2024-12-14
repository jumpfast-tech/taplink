package tech.jumpfast.taplink.helpers;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
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
}
