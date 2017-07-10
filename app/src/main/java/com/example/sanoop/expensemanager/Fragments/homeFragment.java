package com.example.sanoop.expensemanager.Fragments;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanoop.expensemanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class homeFragment extends Fragment implements TextToSpeech.OnInitListener
{

    private TextToSpeech tts;
    private final int SPEECH_RECOGNITION_CODE = 1;
    private static int flag = 1;
    private static int count = 0;
    private static int txtbox_counter=0;
    private FloatingActionButton fab_speakup;
    private TextView mtxtviewMsg;
    private Intent speak_intent;
    private EditText mtxtTransType,mtxtexpenseCat,mtxtamount;
    private ArrayList<String> result;
    private ArrayList<String> question_to_ask;
    private ArrayList<EditText> mEditText_names;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);

    /*intailing the speak text */
        speak_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speak_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speak_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speak_intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
     /*intailing the speak text */

        fab_speakup = (FloatingActionButton) view.findViewById(R.id.fab_speakup);
        fab_speakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ask_question();
            }
        });

        fab_speakup.setEnabled(false);



        mtxtTransType = (EditText) view.findViewById(R.id.txt_transtype);
        mtxtexpenseCat = (EditText) view.findViewById(R.id.txt_expensetype);
        mtxtamount = (EditText) view.findViewById(R.id.txt_amount);

        mEditText_names = new ArrayList<>();
        mEditText_names.add(mtxtTransType);
        mEditText_names.add(mtxtexpenseCat);
        mEditText_names.add(mtxtamount);

        tts = new TextToSpeech(getActivity().getApplicationContext(), this);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId)
            {

            }

            @Override
            public void onDone(String utteranceId) {

                count = count + 1;
                if(flag!=1)
                {
                    startSpeechToText();
                }
                else
                {
                    flag=2;
                    ask_question();
                }

            }

            @Override
            public void onError(String utteranceId) {

            }
        });

        question_to_ask = new ArrayList<>();
        question_to_ask.add("Hi there ");
        question_to_ask.add("What kind of entry , income or expense ? ");
        question_to_ask.add("What category does it belongs");
        question_to_ask.add("Please provide the amount ?");

    }

    public void ask_question()
    {
        if(count < question_to_ask.size())
        {
            speakOut(question_to_ask.get(count));
        }
    }


    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text)
    {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }


    private void speakOut(String commandString)
    {
        String text= commandString;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ttsGreater21(text);
        }
        else {
            ttsUnder20(text);
        }

    }

    private void startSpeechToText()
    {
        try
        {
            startActivityForResult(speak_intent, SPEECH_RECOGNITION_CODE);
        }
        catch (ActivityNotFoundException a)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "This Language is not supported");
            }
            else
            {
                    fab_speakup.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SPEECH_RECOGNITION_CODE:
            {
                if (resultCode == RESULT_OK && null != data)
                {

                    result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    mEditText_names.get(txtbox_counter).setText(text);
                    txtbox_counter++;
                    ask_question();
                }
                break;
            }
        }
    }
}
