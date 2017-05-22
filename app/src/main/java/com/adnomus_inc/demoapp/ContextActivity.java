package com.adnomus_inc.demoapp;

/*
This Activity provides a simple implementation of a simple ContextAd. You also need the ContextAdManager file to complete the process.
 */


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adNomus.library.AdNomusControl;


public class ContextActivity extends Activity {
    AdNomusControl adNomusControl;
    Button submitbtn;
    EditText message;

    public static String user_id = "test_user_from_context";
    public static String content = "this is a test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        /* Loading widgets from the layout file */
        submitbtn = (Button) findViewById(R.id.submitbtn);
        message = (EditText) findViewById(R.id.message);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*Creating an AdNomucControl object and setting all the necessary information to it. */
//                adNomusControl = AdNomusControl.sharedInstance(getActivity());
//                adNomusControl.initialize("test_customer", "666xfe", "test_app", "test_user");
//                adNomusControl.setAge("50");
//                adNomusControl.setGender("Male");
//                adNomusControl.setNationality("Martian");
//                /*Creating also a ContextManager instance  and pass it throught the getAdContextFromContent method*/
//                ContextManager contextManager = new ContextManager(getActivity());
//                adNomusControl.getAdContextFromContent(message.getText().toString(), AdNomusControl.AdType.standard, AdNomusControl.AdSize.size_120x600, contextManager);


                content = message.getText().toString();

                Toast.makeText(ContextActivity.this, "Your personal id is " + user_id, Toast.LENGTH_SHORT).show();

                ChooseContextDialog chooseContextDialog = new ChooseContextDialog();
                chooseContextDialog.show(getFragmentManager(), "contextDialog");

            }
        });

    }

    //Helper function that simply returns the ContextActivity
    private Activity getActivity(){
        return this;
    }
}


