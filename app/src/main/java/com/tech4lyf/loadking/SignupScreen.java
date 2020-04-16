package com.tech4lyf.loadking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.tech4lyf.loadking.Helpers.SMSBroadcastReceiver;
import com.tech4lyf.loadking.Helpers.SMSHelper;
import com.tech4lyf.loadking.interfaces.OtpReceivedInterface;

import java.util.Random;

public class SignupScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OtpReceivedInterface {

    String phone="";
    TextInputEditText edtPhone;
    EditText edtOTPa,edtOTPb,edtOTPc,edtOTPd;


    Button btnSendOTP;
    private GoogleApiClient mCredentialsApiClient;
    private static final int RC_HINT = 1000;
    SMSBroadcastReceiver smsBroadcastReceiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        mCredentialsApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        showHint();
        smsBroadcastReceiver=new SMSBroadcastReceiver();
        smsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(smsBroadcastReceiver, intentFilter);


        edtPhone=(TextInputEditText)findViewById(R.id.edtPhone);
        btnSendOTP=(Button)findViewById(R.id.btnSendOTP);
        edtOTPa=(EditText)findViewById(R.id.edtOTPa);
        edtOTPb=(EditText)findViewById(R.id.edtOTPb);
        edtOTPc=(EditText)findViewById(R.id.edtOTPc);
        edtOTPd=(EditText)findViewById(R.id.edtOTPd);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignupScreen.this.startActivity(new Intent(SignupScreen.this,MainActivity.class));

                phone = edtPhone.getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(SignupScreen.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 10) {
                    Toast.makeText(SignupScreen.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    if (btnSendOTP.getText().toString().equalsIgnoreCase("Send OTP")) {
                        Toast.makeText(SignupScreen.this, "Sending OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupScreen.this, "Resending OTP", Toast.LENGTH_SHORT).show();
                    }

                    int otp = generateOTP(1111, 9999);
                    SMSHelper smsHelper = new SMSHelper();
                    startSMSListener();

                    /*String OTPMessage = "[#] Your OTP code is " + String.valueOf(otp) + ".\n" +
                            "\n" +
                            "tVwTeHKbGvf";*/


                    String OTPMessage = "[#] Your OTP is "+String.valueOf(otp)+".%n %ntVwTeHKbGvf";
                    try {
                        smsHelper.sendOTP(phone, OTPMessage);
                    } catch (Exception e) {
                        Toast.makeText(SignupScreen.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    btnSendOTP.setText("Resend OTP");
                }
            }
        });

        edtOTPa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtOTPa.getText().length()!=0)
                {
                    edtOTPb.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtOTPb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtOTPb.getText().length()!=0)
                {
                    edtOTPc.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTPc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtOTPc.getText().length()!=0)
                {
                    edtOTPd.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTPd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyOTP();
            }
        });

    }


    void verifyOTP()
    {
        if(edtOTPa.getText().length()!=0 && edtOTPb.getText().length()!=0 && edtOTPc.getText().length()!=0 && edtOTPd.getText().length()!=0)
        {
            final ProgressDialog progressDialog;
            progressDialog=ProgressDialog.show(SignupScreen.this,"","Verifying",false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },5000);
            }
            Toast.makeText(SignupScreen.this, "Verifying", Toast.LENGTH_SHORT).show();
        }
    }


        private void showHint() {

            HintRequest hintRequest = new HintRequest.Builder()
                    .setHintPickerConfig(new CredentialPickerConfig.Builder()
                            .setShowCancelButton(true)
                            .build())
                    .setPhoneNumberIdentifierSupported(true)
                    .build();

            PendingIntent intent =
                    Auth.CredentialsApi.getHintPickerIntent(mCredentialsApiClient, hintRequest);
            try {
                startIntentSenderForResult(intent.getIntentSender(), RC_HINT, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e("OTP", "Could not start hint picker Intent", e);
            }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                Intent intent;
                // Check for the user ID in your user database.

                Log.e("OTP", credential.getId());
                phone=credential.getId();

                if (phone.startsWith("+91")) {
                    phone=phone.replaceAll("\\s+","").replaceFirst("\\+91","");
                }
                Log.e("OTP",phone);
                edtPhone.setText(phone);

//                if (userDatabaseContains(credential.getId())) {
//                    intent = new Intent(this, SignInActivity.class);
//                } else {
//                    intent = new Intent(this, SignUpNewUserActivity.class);
//                }
//                intent.putExtra("com.mycompany.myapp.SIGNIN_HINTS", credential);
//                startActivity(intent);
//            } else {
//                Log.e(TAG, "Hint Read: NOT OK");
//                Toast.makeText(this, "Hint Read Failed", Toast.LENGTH_SHORT).show();
//            }
            }




        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {
        Toast.makeText(SignupScreen.this, "Otp Received " + otp, Toast.LENGTH_LONG).show();
        Log.e("Otp Received " , otp);

        //edtPhone.setText(otp.charAt(1));
        Toast.makeText(this, "OTP-test "+otp.charAt(1), Toast.LENGTH_SHORT).show();
        edtOTPa.setText(String.valueOf(otp.charAt(0)));
        edtOTPb.setText(String.valueOf(otp.charAt(1)));
        edtOTPc.setText(String.valueOf(otp.charAt(2)));
        edtOTPd.setText(String.valueOf(otp.charAt(3)));


    }

    @Override
    public void onOtpTimeout() {
        Toast.makeText(SignupScreen.this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
                //Toast.makeText(SignupScreen.this, "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupScreen.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private static int generateOTP(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

