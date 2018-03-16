package com.rbbn.ems.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rbbn.ems.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by nsoni on 3/15/2018.
 */

public class SpeechRecognitionActivity extends AppCompatActivity {
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_recognition);
        previousActivity = getIntent().getStringExtra("Activity Name");
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        btnSpeak.callOnClick();

    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    String voicedata = result.get(0);
//                    Log.d("Abhishek",previousActivity);
                    if (voicedata.toLowerCase().contains("node") || voicedata.toLowerCase().contains("note") || voicedata.toLowerCase().contains("know")) {
//                        Log.d("Abhishek",NodeInfoActivity.class.getSimpleName());
                        if (previousActivity.equals(NodeInfoActivity.class.getSimpleName())) {
                            super.onBackPressed();
                        } else {
                            Intent i = new Intent(SpeechRecognitionActivity.this, NodeInfoActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else if (voicedata.toLowerCase().contains("dashboard")) {
                        if (previousActivity.equals(DashboardActivity.class.getSimpleName())) {
                            super.onBackPressed();
                        } else {
                            Intent i = new Intent(SpeechRecognitionActivity.this, DashboardActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else if (voicedata.toLowerCase().contains("fault") || voicedata.toLowerCase().contains("alarm") || voicedata.toLowerCase().contains("fm")) {
                        if (previousActivity.equals(AlarmInfoActivity.class.getSimpleName())) {
                            super.onBackPressed();
                        } else {
                            Intent i = new Intent(SpeechRecognitionActivity.this, AlarmInfoActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else if (voicedata.toLowerCase().contains("health") || voicedata.toLowerCase().contains("log")) {
                        if (previousActivity.equals(LogInfoActivity.class.getSimpleName())) {
                            super.onBackPressed();
                        } else {
                            Intent i = new Intent(SpeechRecognitionActivity.this, LogInfoActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Sorry Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
        }
    }

}
