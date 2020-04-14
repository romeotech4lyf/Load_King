package com.tech4lyf.loadking.Helpers;


import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSHelper__ {
    public String sendOTP(String _phone,String _message) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            // Construct data
            String apiKey = "apikey=" + URLEncoder.encode("nEYbOYS7MI0-Bhtqx6vNWTwmNibnFfQz6FeJoFR3Dj\t", "UTF-8");
            String message = "&message=" + URLEncoder.encode(_message, "UTF-8");
            String sender = "&sender=" + URLEncoder.encode("TCHLYF", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode(_phone, "UTF-8");

            // Send data
            String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult="";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult=sResult+line+" ";
            }
            rd.close();

            return sResult;
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}