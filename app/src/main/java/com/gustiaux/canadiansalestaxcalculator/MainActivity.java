package com.gustiaux.canadiansalestaxcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected TextView result;
    protected EditText priceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle(getString(R.string.app_label));
        result = (TextView) findViewById(R.id.result);
        priceInput = (EditText) findViewById(R.id.priceInput);
        priceInput.addTextChangedListener(inputPriceWatcher);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TextWatcher inputPriceWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            priceInput.removeTextChangedListener(this);

            result.setText(getString(R.string.default_result));
            String input = s.toString();

            if (!input.isEmpty()) {
                Double price = Double.parseDouble(Utils.cleanNumber(input));
                priceInput.setText(Utils.formatNumber(price, false));
                Double priceCalculated = price * 1.13;
                result.setText(Utils.formatNumber(priceCalculated, true));
            }

            priceInput.setSelection(priceInput.getText().length());
            priceInput.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            int length = Utils.cleanNumber(s.toString()).length();
            if (length == 9) {
                Utils.displayToast(getString(R.string.input_too_long));
            }
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    };
}
