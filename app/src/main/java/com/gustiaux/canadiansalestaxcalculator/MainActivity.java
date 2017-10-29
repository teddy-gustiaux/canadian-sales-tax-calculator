package com.gustiaux.canadiansalestaxcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.gustiaux.CanadianSalesTaxCalculator;
import com.gustiaux.canadiansalestaxcalculator.model.Location;
import com.gustiaux.canadiansalestaxcalculator.model.Price;
import com.gustiaux.canadiansalestaxcalculator.utils.UX;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected TextView result;
    protected TextView locationTextView;
    protected TextView taxTextView;
    protected EditText priceInput;
    protected SharedPreferences sharedPref;
    protected String locationSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CanadianSalesTaxCalculator.applyCorrectTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle(getString(R.string.app_label));
        result = (TextView) findViewById(R.id.result);
        priceInput = (EditText) findViewById(R.id.priceInput);
        priceInput.addTextChangedListener(inputPriceWatcher);

        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        updateLocationInformation();
        updateInputPrice();
    }

    public void updateLocationInformation() {
        locationTextView = (TextView) findViewById(R.id.location);
        this.locationSetting = sharedPref.getString(getString(R.string.location_list), getString(R.string.ON));

        Boolean locationDisplayName = sharedPref.getBoolean(getString(R.string.location_name_switch), true);
        if (locationDisplayName) {
            String[] locationNames = getResources().getStringArray(R.array.pref_location_list);
            String[] locationValues = getResources().getStringArray(R.array.pref_location_list_values);
            int indexOfLocation = Arrays.asList(locationValues).indexOf(this.locationSetting);
            String locationName = locationNames[indexOfLocation];
            locationTextView.setText(getString(R.string.location, locationName));
        } else {
            locationTextView.setText(getString(R.string.location, this.locationSetting));
        }

        taxTextView = (TextView) findViewById(R.id.tax);
        Location l = ((CanadianSalesTaxCalculator) this.getApplication()).getLocation();
        l.updateLocation(this.locationSetting);
        ((CanadianSalesTaxCalculator) this.getApplication()).setLocation(l);
        taxTextView.setText(getString(R.string.tax, l.getPercentage()));
    }

    public void updateInputPrice() {
        String memoryInput = sharedPref.getString(getString(R.string.memory_input_price), null);
        Boolean separatorSwitch = sharedPref.getBoolean(getString(R.string.separator_switch), false);
        if (memoryInput != null && !memoryInput.isEmpty()) {
            if (!separatorSwitch) memoryInput = memoryInput.replaceAll(",", "");
            displayInputPrice(memoryInput);
        }
    }

    public void displayInputPrice(String price) {
        sharedPref.edit().putString(getString(R.string.memory_input_price), price).apply();
        priceInput.setText(price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                this.startActivity(settings);
                return true;

            case R.id.action_clear:
                priceInput.setText("");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLocationInformation();
        updateInputPrice();
    }

    private TextWatcher inputPriceWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            priceInput.removeTextChangedListener(this);

            String input = s.toString();

            // Make sure there are no more than 10 digits
            Boolean separatorSwitch = sharedPref.getBoolean(getString(R.string.separator_switch), false);
            if (!separatorSwitch && input.length() > 10) {
                if (input.contains(".")) {
                    input = input.substring(0, 11);
                } else {
                    input = input.substring(0, 10);
                }
                displayInputPrice(input);
            } else if (separatorSwitch && input.length() > 10 && !input.contains(",")) {
                input = input.substring(0, 11);
            }

            if (!input.isEmpty() && !input.equals(".")) {
                Price inputPrice = new Price(input);
                if (separatorSwitch) {
                    displayInputPrice(inputPrice.formatNumber());
                } else {
                    displayInputPrice(input);
                }

                // Calculate total after taxes
                Price resultPrice = inputPrice;
                Location l = ((CanadianSalesTaxCalculator) getApplication()).getLocation();
                resultPrice.addSalesTax(l.getTaxPercentage());
                Boolean pennyRounding = sharedPref.getBoolean(getString(R.string.penny_rounding_switch), false);
                resultPrice.roundNumber(pennyRounding);
                result.setText(resultPrice.formatNumber());
            } else if (!input.isEmpty() && input.length() == 1 && input.equals(".")) {
                displayInputPrice(getString(R.string.default_input_decimal));
            } else {
                result.setText(getString(R.string.default_result));
            }

            priceInput.setSelection(priceInput.getText().length());
            priceInput.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            String input = s.toString();
            if (input.isEmpty()) return;
            input = input.replaceAll("[,.]", "");
            if (input.length() == 10) {
                if (s.toString().contains(".")) {
                    UX.displayToast(getString(R.string.input_too_long));
                } else {
                    UX.displayToast(getString(R.string.input_too_expensive));
                }
            }
        }
    };
}
