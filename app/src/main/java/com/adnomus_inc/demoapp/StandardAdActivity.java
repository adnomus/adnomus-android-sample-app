package com.adnomus_inc.demoapp;

/*
This Activity provides a simple implementation of a simple StandardAd
 */


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.adNomus.library.AdNomusControl;



public class StandardAdActivity extends Activity {

    /* Loading all the widgets from layout files */

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.message)
    EditText message;

    @BindView(R.id.submitbtn)
    Button submitbtn;

    @BindView(R.id.webview)
    WebView webview;

    AdNomusControl.AdSize adSize;
    AdNomusControl adNomusControl;
    WebView prView;

    @BindView(R.id.seekBar)
    SeekBar seekbar;

    @BindView(R.id.sizelabel)
    TextView sizeLabel;

    String textToLabel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        ButterKnife.bind(this);
        seekbar.setEnabled(false);
        seekbar.setProgress(9);
        textToLabel = "Auto Size";

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //when the user clicks the submit button
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                /*Creating an AdNomucControl object and setting all the necessary informations to it. */
                adNomusControl = AdNomusControl.sharedInstance(getActivity());
                /*if you don't have any credentials you will be able to see only sample images as ads.
                 * test_android_publisher is just a test user so you will not be able to see real ads with it. */
                adNomusControl.initialize("test_android_publisher", "666xfe", "test_app", "test_user");

                /*It's better to call the following methods only one time for every user. There is no need to call them
                * in every ad request*/
                adNomusControl.setAge("50");
                adNomusControl.setGender("Male");
                adNomusControl.setNationality("Martian");

                /*Get the view (given the user's message, the type and the size) and set it to the webview.
                * You can also set this to a layout but it possibly cause some render errors*/
                webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                adSize = AdNomusControl.AdSize.auto;
                prView = adNomusControl.getStandardWebViewFromContent(message.getText().toString(), AdNomusControl.AdType.standard, adSize);
                if (prView != null) webview.removeView(prView);
                webview.addView(prView);

                seekbar.setEnabled(true);
                sizeLabel.setText("Auto size");
                seekbar.setProgress(9);

            }
        });


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                webview.removeView(prView);
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


                prView = adNomusControl.getStandardWebViewFromContent(message.getText().toString(), AdNomusControl.AdType.standard, adSize);
                webview.addView(prView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sizeLabel.setText(textToLabel);

            }


        });


    }

    private Activity getActivity(){
        return this;
    }
}