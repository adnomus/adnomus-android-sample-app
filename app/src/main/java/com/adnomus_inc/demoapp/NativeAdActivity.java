package com.adnomus_inc.demoapp;

/*
This Activity provides a simple implementation of a simple NativeAd. You also need the NativeAdManager file to complete the process.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adNomus.library.AdNomusControl;

public class NativeAdActivity extends Activity {


    NativeAdManager nativeAdManager;
    Button submitbtn;
    EditText message;
    SeekBar seekbar;
    AdNomusControl.AdSize adSize;
    AdNomusControl adNomusControl;
    String textToLabel;
    TextView sizeLabel;

    String user_id;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        /* Loading widgets from the layout file */
        submitbtn = (Button) findViewById(R.id.submitbtn);
        message = (EditText) findViewById(R.id.message);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        sizeLabel = (TextView) findViewById(R.id.sizelabel);

        adSize = AdNomusControl.AdSize.auto;
        seekbar.setEnabled(false);
        seekbar.setProgress(9);
        textToLabel = "Auto Size";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                user_id= "test_user";
                content = "";
            } else {
                user_id= extras.getString("user_id");
                content = extras.getString("content");
                message.setText(content);
            }
        } else {
            user_id= (String) savedInstanceState.getSerializable("user_id");
            content = (String) savedInstanceState.getSerializable("content");
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Creating an AdNomucControl object and setting all the necessary information to it. */
                adNomusControl = AdNomusControl.sharedInstance(getActivity());
                /*if you don't have any credentials you will be able to see only sample images as ads
                * test_android_publisher is just a test user so you will not be able to see real ads with it.*/
                adNomusControl.initialize("test_android_publisher", "666xfe", "test_app", "test_user");
                adNomusControl.setAge("50");
                adNomusControl.setGender("Male");
                adNomusControl.setNationality("Martian");
                nativeAdManager = new NativeAdManager(getActivity());

                String text_to_send;
                if (message.getText().toString().equals(content))
                    text_to_send = content;
                else
                    text_to_send = message.getText().toString();

                /*Creating also a NativeAdManager instance  and pass it throught the getNativeAdFromContent method*/
                adNomusControl.getNativeAdFromContent(text_to_send, AdNomusControl.AdType.standard, adSize, nativeAdManager);

                //enable and reset the seekbar
                seekbar.setEnabled(true);
                sizeLabel.setText("Auto size");
                seekbar.setProgress(9);

            }
        });


        //The listener for the seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if ( i==0 ) {
                    adSize = AdNomusControl.AdSize.size_120x20;
                    textToLabel = "120x20";
                }
                else if ( i==1 ) {
                    adSize = AdNomusControl.AdSize.size_120x600;
                    textToLabel = "120x600";
                }
                else if ( i==2) {
                    adSize = AdNomusControl.AdSize.size_168x28;
                    textToLabel = "166x28";
                }
                else if (i==3) {
                    adSize = AdNomusControl.AdSize.size_216x36;
                    textToLabel = "216x36";
                }
                else if (i==4) {
                    adSize = AdNomusControl.AdSize.size_300x50;
                    textToLabel = "300x50";
                }
                else if (i==5) {
                    adSize = AdNomusControl.AdSize.size_300x250;
                    textToLabel = "300x250";
                }
                else if (i==6) {
                    adSize = AdNomusControl.AdSize.size_320x50;
                    textToLabel = "320x50";
                }
                else if (i==7) {
                    adSize = AdNomusControl.AdSize.size_728x90;
                    textToLabel = "728x90";
                }
                else if (i==8){
                    adSize = AdNomusControl.AdSize.auto;
                    textToLabel = "Auto size";
                }

                String text_to_send;
                if (message.getText().toString().equals(content))
                    text_to_send = content;
                else
                    text_to_send = message.getText().toString();

                /*Creating also a NativeAdManager instance  and pass it throught the getNativeAdFromContent method
                * (keeping the same nativeAdManager*/
                adNomusControl.getNativeAdFromContent(text_to_send, AdNomusControl.AdType.standard, adSize, nativeAdManager);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //change the label (in the bottom of seekbar)
                sizeLabel.setText(textToLabel);

            }


        });

    }



    //Helper function that simply returns the NativeAdActivity
    private  Activity getActivity() {
        return  this;
    }

}
