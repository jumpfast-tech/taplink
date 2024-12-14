package tech.jumpfast.taplink.tap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.widget.ArrayAdapter;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import tech.jumpfast.taplink.databinding.FragmentFirstBinding;
import tech.jumpfast.taplink.helpers.CountryCodeHelper;
import tech.jumpfast.taplink.helpers.WhatsappHelper;

public class TapLinkFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        List<String> countryCodes = CountryCodeHelper.getCountryCodes();
        AutoCompleteTextView countryCodeDropdown = binding.countryCodeDropdown;

        // Don't hide phone number input since we are using inputType as numberPassword
        binding.numberInput.setTransformationMethod(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                countryCodes
        );
        countryCodeDropdown.setAdapter(adapter);

        for (String code : countryCodes) {
            if (code.contains("India")) {
                countryCodeDropdown.setText(code, false);
                break;
            }
        }

        countryCodeDropdown.setOnClickListener(view -> countryCodeDropdown.showDropDown());
        countryCodeDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCode = parent.getItemAtPosition(position).toString();
            // Handle the selected country code
            Log.v("Chirag", "Selected: " + selectedCode);
        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout countryCodeLayout = binding.countryCodeLayout;
        TextInputLayout numberLayout = binding.numberLayout;


        binding.submitButton.setOnClickListener(view1 -> {
            String countryCode = binding.countryCodeDropdown.getText().toString().trim();
            String phoneNumber = binding.numberInput.getText().toString().trim();

            numberLayout.setError(null);

            if (countryCode.isEmpty()) {
                countryCodeLayout.setError("Please select a country code!");
                return;
            }

            if (phoneNumber.isEmpty()) {
                numberLayout.setError("Phone number cannot be empty!");
                return;
            }

            String region = CountryCodeHelper.getRegionFromCountryCode(countryCode);

            if (!CountryCodeHelper.validatePhoneNumber(phoneNumber, region)) {
                numberLayout.setError("Enter a valid phone number!");
                return;
            }

            String extractedCode = countryCode.split(" ")[0];
            String fullPhoneNumber = extractedCode + phoneNumber;

            WhatsappHelper.openWhatsAppChat(requireContext(), fullPhoneNumber, numberLayout);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}