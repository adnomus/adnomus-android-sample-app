CONTENTS OF THIS FILE
---------------------
   
 * Introduction
 * Setting the library to your project
 * Use the degug version
 * Create a Standard ad (example)
 * Create a Context ad (example)
 * Create a Native ad (example)
 * Notes
 

INTRODUCTION
------------

adNomus is A.I. driven advertising technology for publishers who want to prioritize quality ad experiences over quantity of advertisements. This sdk can be used in any Android project (after version 4) to improve the quality of displayed ads. 

SETTING THE LIBRARY TO YOUR PROJECT (using our maven repository)
------------

The first thing you have to do is to ask us for credentials. The credentials contains an AWS_ACCESS_KEY and a AWS_SECRET_KEY. With these keys you'll be able to use our android SDK. 
You need to contact adNomus via the contact form of the website.

Then you need to store the keys on gradle.properties. You can also add them directly to the basic gradle later, but for security reasons we recommend to put them on gradle.properties as variables like the example below: 

AWS_ACCESS_KEY=IAI...
AWS_SECRET_KEY=9S....

The next thing you have to do, is to define our maven repository to your top-level gradle file. Find the “allprojects” code block and on “repositories” add the following lines:

```java
maven {
    url "s3://adnomus-maven-repo.adnomus.com/releases"
    credentials(AwsCredentials) {
        accessKey AWS_ACCESS_KEY
        secretKey AWS_SECRET_KEY
    }
}
```

Finally, on the other gradle file on “dependencies” area add the “compile” command: 

compile 'com.adNomus:adNomus:1.0:release@aar'

Once you sync the gradle you'll be able to use our services on your android project. 



USE THE DEBUG VERSION
------------
In debug mode, we provide dumps/reports about the app interaction with the adNomus service (e.g. if the user 
misuses the sdk,  she/he can see the errors on the console). In release mode, no reporting is done. 

To use the debug version you need to add the “snapshots” directory to your top-level gradle instead.

```java
maven {
    url "s3://adnomus-maven-repo.adnomus.com/snapshots"
    credentials(AwsCredentials) {
        accessKey AWS_ACCESS_KEY
        secretKey AWS_SECRET_KEY
    }
}
```


CREATE A STANDARD AD (example)
------------

To create a standard ad, you firstly need to have a WebView widget to your layout file. When you load this to your Activity you need to create an AdNomusControl object and pass every needed information (like the network name, the app name, the age of the user, the gender of the user etc). After that you can call a get method (like the getStandardViewFromContent) to take the WebView. Finally you need to set this view to your application’s view. 

This is an example of a StandardAd implementation: 

```java
/*Creating an AdNomucControl object and setting all the necessary informations to it. */
AdNomusControl adNomusControl = AdNomusControl.sharedInstance(getActivity());
adNomusControl.initialize("test_customer", "666xfe", "test_app", "test_user");
adNomusControl.setAge("50");
adNomusControl.setGender("Male");
adNomusControl.setNationality("Martian");
/*Get the view (given the user's message, the type and the size) and set it to the webview.
* You can also set this to a layout but it possibly cause some render errors*/
webview.addView(adNomusControl.getStandardWebViewFromContent(message.getText().toString(), AdNomusControl.AdType.standard, AdNomusControl.AdSize.size_300x250));
```

CREATE A CONTEXT AD (example)
------------

To create a Context ad we need to create a ContextManager class first which implements the AdNomusContextListener. You need to implement the onContextReady method and run a UI thread there setting up the WebView. You can do this as the following example: 

```java
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
```
Finally you need to go on your Activity (where you want to see the ads), and create an AdNomusControl object setting all the necessary information like the following example: 
```java
/*Creating an AdNomucControl object and setting all the necessary information to it. */
adNomusControl = AdNomusControl.sharedInstance(getActivity());
adNomusControl.initialize("test_customer", "666xfe", "test_app", "test_user");
adNomusControl.setAge("50");
adNomusControl.setGender("Male");
adNomusControl.setNationality("Martian");
/*Creating also a ContextManager instance  and pass it throught the getAdContextFromContent method*/
ContextManager contextManager = new ContextManager(getActivity());
adNomusControl.getAdContextFromContent(message.getText().toString(), AdNomusControl.AdType.standard, AdNomusControl.AdSize.size_300x250, contextManager);
```
 
CREATE A NATIVE AD (example)
------------

Like ContextAd, to create a native ad you need a “manager” class first. You can call it NativeAdManager and it needs to implement AdNomusNativeListener. In here you need to overwrite the didReceiveAdResponse method, take the image using nativeAd.getId().getImageUrl() and add it to an ImageView this time. For example: 

```java
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
```
After that you need to create an AdNomusControl object on your Activity  (where you want to see the ads) setting all the necessary information like the following example:

```java
/*Creating an AdNomucControl object and setting all the necessary information to it. */
AdNomusControl adNomusControl = AdNomusControl.sharedInstance(getActivity());
adNomusControl.initialize("test_customer", "666xfe", "test_app", "test_user");
adNomusControl.setAge("50");
adNomusControl.setGender("Male");
adNomusControl.setNationality("Martian");
nativeAdManager = new NativeAdManager(getActivity());
/*Creating also a NativeAdManager instance  and pass it throught the getNativeAdFromContent method*/
adNomusControl.getNativeAdFromContent(message.getText().toString(), AdNomusControl.AdType.standard, AdNomusControl.AdSize.size_300x250, nativeAdManager);
```

NOTES
------------

* If you don't have the adNomus credentials you'll be able to see only sample ad images.
* You don't have to use “setNationality”, “setAge” etc methods in every ad request. The best strategy is to set these values only one time for every user. These information is stored in our database. 
