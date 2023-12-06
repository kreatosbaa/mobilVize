package com.example.mobilvize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Conversion extends AppCompatActivity {

    private final String[] sizes = {"bit", "kibibit", "byte", "kilobyte"};
    private final String[] bases = {"2", "8", "16"};
    String selectedBase, selectedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorevConvert);

        EditText decimalInput = findViewById(R.id.decimal);
        EditText megabyteInput = findViewById(R.id.mbyte);
        EditText celsiusInput = findViewById(R.id.celcius);

        TextView resultBase = findViewById(R.id.tsonuc);
        TextView resultSize = findViewById(R.id.bsonuc);
        TextView resultTemperature = findViewById(R.id.csonuc);

        RadioButton fahrenheitRadioButton = findViewById(R.id.fah);
        RadioButton kelvinRadioButton = findViewById(R.id.kel);

        Spinner baseSpinner, sizeSpinner;
        ArrayAdapter<String> baseAdapter, sizeAdapter;

        baseSpinner = findViewById(R.id.taban);
        sizeSpinner = findViewById(R.id.boyut);

        baseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bases);
        sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);

        baseSpinner.setAdapter(baseAdapter);
        sizeSpinner.setAdapter(sizeAdapter);

        try {
            baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        selectedBase = baseSpinner.getSelectedItem().toString();
                        //Toast.makeText(getBaseContext(), selectedBase, Toast.LENGTH_SHORT).show();

                        String str = decimalInput.getText().toString();
                        if (!str.isEmpty()) {
                            Double dec = Double.parseDouble(str);
                            String result = calculateBase(dec, Integer.parseInt(selectedBase));
                            resultBase.setText("RESULT: " + result);
                        }

                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        selectedSize = sizeSpinner.getSelectedItem().toString();
                        //Toast.makeText(getBaseContext(), selectedSize, Toast.LENGTH_SHORT).show();

                        String str = megabyteInput.getText().toString();
                        if (!str.isEmpty()) {
                            Double dec = Double.parseDouble(str);
                            String result = sizeCalculation(dec, selectedSize);
                            resultSize.setText("RESULT: " + result);
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            fahrenheitRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            Double celsius = Double.parseDouble(celsiusInput.getText().toString());
                            double fahrenheit = (celsius * 9 / 5) + 32;
                            resultTemperature.setText("RESULT: " + fahrenheit);
                        }
                    } catch (Exception e) {
                    }
                }
            });

            kelvinRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            Double celsius = Double.parseDouble(celsiusInput.getText().toString());
                            double kelvin = celsius + 273.15;
                            resultTemperature.setText("RESULT: " + kelvin);
                        }
                    } catch (Exception e) {
                    }
                }
            });

        } catch (Exception e) {
        }

    }

    public static String calculateBase(double decimalValue, int base) {
        long intPart = (long) decimalValue;
        String result = Long.toString(intPart, base);
        if (decimalValue % 1 != 0) {
            result += ".";
            double fraction = decimalValue % 1;
            for (int i = 0; i < 10; i++) {
                fraction *= base;
                int whole = (int) fraction;
                result += Integer.toString(whole, base);
                fraction -= whole;
            }
        }
        return result;
    }

    public static String sizeCalculation(double value, String size) {
        double result = 0;
        String resultString;

        switch (size) {
            case "kilobyte":
                result = value * 1024;
                resultString = String.format("%.0f", result) + "KB";
                break;
            case "byte":
                result = value * 1024 * 1024;
                resultString = String.format("%.0f", result) + "BYTE";
                break;
            case "kibibit":
                result = value * 1024 * 8;
                resultString = String.format("%.0f", result) + "KIBIBIT";
                break;
            case "bit":
                result = value * 1024 * 8 * 1024;
                resultString = String.format("%.0f", result) + "BIT";
                break;
            default:
                resultString = "Invalid selection!!!";
        }
        return resultString;
    }
}
