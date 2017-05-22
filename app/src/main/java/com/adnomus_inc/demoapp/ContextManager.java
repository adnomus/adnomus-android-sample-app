package com.adnomus_inc.demoapp;

/*
This file provides an implemented by AdnomusContextListener class which is responsible to get the native ad and set it to the WebView.
Take a look on ContextActivity also.
 */


import android.app.Activity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.adNomus.library.AdContext;
import com.adNomus.library.AdNomusControl;
import com.adNomus.library.AdnomusContextListener;
import com.adNomus.library.ContextErrorException;

public class ContextManager implements AdnomusContextListener {


    private final Activity appContext;
    String adnomusContext;


    public ContextManager(Activity appContext){
        this.appContext = appContext;
    }

    @Override
    public void onContextReady(final AdContext adContext) {
        appContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    LinearLayout container = (LinearLayout) appContext.findViewById(R.id.container);
                    WebView webView = (WebView) appContext.findViewById(R.id.webview);
                    Toast.makeText(appContext, adContext.getContext(), Toast.LENGTH_SHORT);
                    webView.addView(AdNomusControl.sharedInstance(appContext).getStandardWebViewFromContext(adContext.getContext(), AdNomusControl.AdType.standard, AdNomusControl.AdSize.size_300x250));
                } catch (ContextErrorException e) {
                    Toast.makeText(appContext, "There was an error retrieving the context", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });
    }
}

