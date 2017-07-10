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


public class homeNewFragment extends Fragment
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
        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);



    }


}
