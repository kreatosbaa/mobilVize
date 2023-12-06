package com.example.mobilvize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    String phoneNumber, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorevSMS);

        Button sendButton = findViewById(R.id.send);
        EditText phoneNumberEditText = findViewById(R.id.tel);
        EditText messageEditText = findViewById(R.id.mesaage);

        try {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                    } else {
                        phoneNumber = phoneNumberEditText.getText().toString();
                        message = messageEditText.getText().toString();
                        if (message.equals("") || message == null) {
                            new GetQuoteTask().execute();
                            Toast.makeText(getApplicationContext(), "GÜZEL SÖZ GÖNDERİLDİ!", Toast.LENGTH_SHORT).show();
                        } else {
                            sendSms(message);
                            Toast.makeText(getApplicationContext(), "SMS GÖNDERİLDİ!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e("SmsActivity", "buton işe yaramadı! " + e.getMessage());
        }
    }

    private void sendSms(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "OK!!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SmsActivity", "hata!!!!!" + e.getMessage());
        }
    }

    private class GetQuoteTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String apiUrl = "https://api.quotable.io/random";
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String quote = jsonObject.getString("content");
                sendSms(quote);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
