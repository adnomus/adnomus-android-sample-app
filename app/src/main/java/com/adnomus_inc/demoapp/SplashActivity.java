package com.adnomus_inc.demoapp;

/*
This is just the first screen of the demo. You can choose if you want to test a Standard Ad, a Context Ad or a Native ad
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;


        public class SplashActivity extends Activity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash);
                ButterKnife.bind(this);



            }

    @OnClick(R.id.standard_activity) public void loadStandardActivity(){

        startActivity(new Intent(this, StandardAdActivity.class));
    }

    @OnClick(R.id.native_activity) public void loadNativeActivity(){
        startActivity(new Intent(this, NativeAdActivity.class));
    }

    @OnClick(R.id.context_activity) public void loadContextActivity(){
        startActivity(new Intent(this, ContextActivity.class));
    }
}
