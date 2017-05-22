package com.adnomus_inc.demoapp;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChooseContextDialog extends DialogFragment {

    Button standardbtn;
    Button nativebtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.context_dialog, null);
        getDialog().setTitle("Choose a type");

        standardbtn = (Button) view.findViewById(R.id.standard_button);
        nativebtn = (Button) view.findViewById(R.id.native_button);

        standardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,  StandardAdActivity.class);
                intent.putExtra("user_id", ContextActivity.user_id);
                intent.putExtra("content", ContextActivity.content);
                startActivity(intent);
            }








        });

        nativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,  NativeAdActivity.class);
                intent.putExtra("user_id", ContextActivity.user_id);
                intent.putExtra("content", ContextActivity.content);
                getDialog().dismiss();
                getActivity().finish();

                startActivity(intent);

            }
        });


        return view;

    }


}
