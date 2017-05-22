package com.adnomus_inc.demoapp;

/*
This file provides an implemented by AdnomusNativeListener class which is responsible to get the native ad and set it to the ImageView.
Take a look on NativeAdActivity also.
 */


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.adNomus.library.AdnomusNativeListener;
import com.adNomus.library.EmptyAdException;
import com.adNomus.library.NativeAd;

public class NativeAdManager implements AdnomusNativeListener {
    private final Activity context;
    ImageView adImage;
    NativeAd nativeAd;

    public NativeAdManager(Activity context){
        this.context = context;
    }

    @Override
    public void didReceiveAdResponse(final NativeAd nativeAd) {
        this.nativeAd = nativeAd;
        adImage = (ImageView) context.findViewById(R.id.ad);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Picasso.with(context)
                            .load(nativeAd.getAd().getImageUrl())
                            .into(adImage);
                    nativeAd.reportAdShown();
                } catch (EmptyAdException e) {
                    Toast.makeText(context, "There was a problem retrieving your ad.", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });
        adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nativeAd.reportAdClicked();
            }
        });
    }
}
