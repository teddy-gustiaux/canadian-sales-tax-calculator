package com.gustiaux.canadiansalestaxcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.gustiaux.canadiansalestaxcalculator.model.Price;
import com.gustiaux.canadiansalestaxcalculator.utils.UX;

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
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
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

    private TextWatcher inputPriceWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            priceInput.removeTextChangedListener(this);

            result.setText(getString(R.string.default_result));
            String input = s.toString();

            if (!input.isEmpty() && !input.equals(".")) {
                Price inputPrice = new Price(input);
                priceInput.setText(inputPrice.formatNumber(false));
                inputPrice.addSalesTax();
                result.setText(inputPrice.formatNumber(true));
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
                UX.displayToast(getString(R.string.input_too_long));
            }
        }
    };
}
