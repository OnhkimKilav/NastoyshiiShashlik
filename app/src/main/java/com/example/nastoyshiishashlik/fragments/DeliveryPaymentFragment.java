package com.example.nastoyshiishashlik.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;

import butterknife.BindView;

public class DeliveryPaymentFragment extends BaseFragment {
    @BindView(R.id.codeSpinner)
    Spinner spin;

    @Override
    public int getViewId() {
        return R.layout.fragment_delivery_payment;
    }

    @Override
    public void onViewCreated(View view) {

        final String[] countryCodes = getResources().getStringArray(R.array.codes);
        ArrayAdapter<String> countryCodeAdapter = new ArrayAdapter<String>(App.getContext(),
                R.layout.item_delivery_payment,R.id.textView,countryCodes);
        spin.setAdapter(countryCodeAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = String.valueOf(spin.getSelectedItem());
                if (position == 0) {
                    spin.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
