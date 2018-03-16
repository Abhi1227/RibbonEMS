package com.rbbn.ems.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rbbn.ems.R;
import com.rbbn.ems.utils.PerformNetworkOperations;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mEMSIpview;
    private String launchURL;
    private String getAuthURL;
    private String logonURL;
    private String securityURL;
    private String authLoginURL;
    private String error;
    private Button mEmailSignInButton;

    private String token;
    private PerformNetworkOperations performNetworkOperations;
    private static LoginActivity instance;

    public static LoginActivity getInstance() {
        return instance;
    }

    public static void setInstance(LoginActivity instance) {
        LoginActivity.instance = instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setInstance(this);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEMSIpview = (EditText) findViewById(R.id.ipAddress);
        mEmailSignInButton = (Button) findViewById(R.id.loginBtn);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        launchURL = getResources().getString(R.string.launch_url);
        getAuthURL = getResources().getString(R.string.auth_session);
        logonURL = getResources().getString(R.string.logon_url);
        securityURL = getResources().getString(R.string.security_url);
        authLoginURL = getResources().getString(R.string.auth_login);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };


        performNetworkOperations = new PerformNetworkOperations(this);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//
                attemptLogin();
//                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
//                startActivity(i);
            }
        });
//        mEMSIpview.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.i("Navin","Navin");
//                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    mEmailView.requestFocus();
//                }
//                return true;
//            }
//        });
//        mEmailView.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    mPasswordView.requestFocus();
//                }
//                return true;
//            }
//        });
//        mPasswordView.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    mEmailSignInButton.requestFocus();
//                }
//                return true;
//            }
//        });
        displayFirebaseRegId();
//        List<String> nodeList = new ArrayList<String>(Arrays.asList((getResources().getStringArray(R.array.node_list))));
//        navigateActivity(nodeList,"10.54.9.18");

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mEMSIpview.setError(null);
        // Store values at the time of the login attempt.
        final String emsIP = mEMSIpview.getText().toString();
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(emsIP)) {
            mEMSIpview.setError("Please enter an ems ip");
            focusView = mEMSIpview;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            performNetworkOperations.doLogin(emsIP, email, password);
//            Toast.makeText(this,token,Toast.LENGTH_SHORT).show();
//            List<String> nodeData = performNetworkOperations.getNodesList(emsIP,token);

        }
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

    }
    public void navigateActivity(List<String> nodeDataList, String emsIp) {
        View view = mEmailSignInButton;
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, DemoLikeTumblrActivity.class);
        intent.putExtra(DemoLikeTumblrActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(DemoLikeTumblrActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        intent.putStringArrayListExtra("NodeData", (ArrayList<String>) nodeDataList);
        intent.putExtra("EMSIP", emsIp);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
