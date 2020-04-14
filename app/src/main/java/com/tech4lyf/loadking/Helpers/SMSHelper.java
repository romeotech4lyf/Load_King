package com.tech4lyf.loadking.Helpers;


import android.content.Context;
import android.content.res.Resources;
import android.os.StrictMode;
import android.util.Log;

import com.tech4lyf.loadking.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SMSHelper {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "https://api.textlocal.in/send/";

    //private static final String POST_URL = "https://localhost:9090/SpringMVCExample/home";

    //private static final String POST_PARAMS = "userName=Pankaj";

//    public static void main(String[] args) throws IOException {
//
//        sendGET();
//        System.out.println("GET DONE");
//        //sendPOST();
//        //System.out.println("POST DONE");
//    }

    public static void sendOTP(String phone,String message) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String newURL=GET_URL+"?apikey=nEYbOYS7MI0-Bhtqx6vNWTwmNibnFfQz6FeJoFR3Dj"+"&numbers="+phone+"&message="+ URLEncoder.encode(message,"UTF-8")+"&sender=TCHLYF&test=1";
        Log.e("OTP",newURL);
        Log.e("SMS",newURL);
        URL obj = new URL(newURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

//    private static void sendPOST() throws IOException {
//        URL obj = new URL(POST_URL);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        // For POST only - START
//        con.setDoOutput(true);
//        OutputStream os = con.getOutputStream();
//        os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//        // For POST only - END
//
//        int responseCode = con.getResponseCode();
//        System.out.println("POST Response Code :: " + responseCode);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) { //success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//        } else {
//            System.out.println("POST request not worked");
//        }
//    }
}
