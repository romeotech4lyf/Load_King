package com.tech4lyf.loadking.interfaces;


public interface OtpReceivedInterface {
    void onOtpReceived(String otp);
    void onOtpTimeout();
}
