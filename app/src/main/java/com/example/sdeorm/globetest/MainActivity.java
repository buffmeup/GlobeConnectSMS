package com.example.sdeorm.globetest;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ph.com.globe.connect.*;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    String token="";
    String globelocate="";
    String plng,plat;
    String address;
    Geocoder geocoder;
    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askHelpRespo();
        geocoder = new Geocoder(this, Locale.getDefault());

        Log.d("Globelocate",globelocate);
        Log.d("myToken","Token"+token);

//        if(!token.isEmpty()) {



        ///}
//        else {
//            Log.d("EX", "No token");
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if request code is = 1
        if(requestCode == 1) {
            // and is activity result ok
            if(resultCode == Activity.RESULT_OK) {
                // parse string
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("auth_response"));
                    System.out.println(response.toString()); // <-- will return the access token
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void askHelp(View v){
        Intent intent = new Intent(getApplicationContext(), GlobeAuthActivity.class);

        intent.putExtra("app_id", "X54MIgX5zLh5rcGG5KT5g6hq65onI9oL");
        intent.putExtra("app_secret", "d32204e99986a06dcdb79b0dd1836032d79ebcb799ef70cd1931b96348d22cc4");

        //startActivityForResult(intent, 1);


        try {
            Sms sms = new Sms("5630", "FuvZPUi1ZogJKEEk3bGewnJ2-jHWboXIYuS1EZoYlME");
            Log.d("Globelocateloob",globelocate);
            sms.setReceiverAddress("+639951957980")
                    .setClientCorrelator("123456")
                    .setMessage("[1/3] I need help, I'm at "+plng+" "+plat)
                    .sendMessage(new AsyncHandler() {
                        @Override
                        public void response(HttpResponse response) throws HttpResponseException {
                            try {
                                JSONObject json = new JSONObject(response.getJsonResponse().toString());

                                System.out.println("textsent" + json.toString());

                                try {
                                    Sms sms = new Sms("5630", "FuvZPUi1ZogJKEEk3bGewnJ2-jHWboXIYuS1EZoYlME");
                                    Log.d("Globelocateloob",globelocate);
                                    sms.setReceiverAddress("+639951957980").setMessage("[2/3]"+address)
                                            .sendMessage(new AsyncHandler() {
                                                @Override
                                                public void response(HttpResponse response) throws HttpResponseException {
                                                    try {
                                                        JSONObject json = new JSONObject(response.getJsonResponse().toString());

                                                        System.out.println("textsent" + json.toString());

                                                        try {
                                                            Sms sms = new Sms("5630", "FuvZPUi1ZogJKEEk3bGewnJ2-jHWboXIYuS1EZoYlME");
                                                            Log.d("Globelocateloob",globelocate);
                                                            sms.setReceiverAddress("+639951957980").setMessage("[3/3]"+globelocate)
                                                                    .sendMessage(new AsyncHandler() {
                                                                        @Override
                                                                        public void response(HttpResponse response) throws HttpResponseException {
                                                                            try {
                                                                                JSONObject json = new JSONObject(response.getJsonResponse().toString());

                                                                                System.out.println("textsent" + json.toString());
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    });

                                                        } catch (ApiException | HttpRequestException | HttpResponseException e) {
                                                            e.printStackTrace();
                                                        }


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                } catch (ApiException | HttpRequestException | HttpResponseException e) {
                                    e.printStackTrace();
                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (ApiException | HttpRequestException | HttpResponseException e) {
            e.printStackTrace();
        }
    }

    public void askHelpRespo (){
        Location location = new Location("FuvZPUi1ZogJKEEk3bGewnJ2-jHWboXIYuS1EZoYlME");

        try {
            location
                    .setAddress("+639951957980")
                    .setRequestedAccuracy(100)
        .getLocation(new AsyncHandler() {
                @Override
                public void response(HttpResponse response) throws HttpResponseException {
                    try {
                        JSONObject json = new JSONObject(response.getJsonResponse().toString());
                        try {
                            formatString(json.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Located"+globelocate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(ApiException | HttpRequestException | HttpResponseException e) {
            e.printStackTrace();
            globelocate="location null | Contact Developer";

        }

    }

    public String convertToAddress(double lat,double lng) throws IOException {
        addresses = geocoder.getFromLocation(lat,lng,1);
        Log.d("address",addresses.get(0).getFeatureName()+
                ","+addresses.get(0).getLocality());
        address=addresses.get(0).getFeatureName()+
                ","+addresses.get(0).getLocality();
    return address;
    }

    public String formatString(String rawString) throws IOException {
        String words[] = rawString.split(",");
        String rawLat= words[3];
        String rawLong= words[4];
        String rawURL= words[5];

        String cleanLat[] = rawLat.split(":");
        String cleanLong[] = rawLong.split(":");
        String cleanURL[] = rawURL.split(":");

        String lat=cleanLat[1];
        String lng=cleanLong[1];
        String url=cleanURL[1]+":"+cleanURL[2]+":"+cleanURL[3];

        convertToAddress(Double.parseDouble(lat.replace("\"","")),Double.parseDouble(lng.replace("\"","")));
        globelocate=" "+url;
        globelocate=globelocate.replaceAll("\\\\","");
        mlat("Lat:"+lng.replace("\"",""));
        mlng("Long:"+lat.replace("\"",""));
        globelocate=globelocate.replace("\"","");
        Log.d("FormattedLoc",globelocate);
        return globelocate;
    }

    public String mlat(String lat){
        plat=lat;
        return plat;
    }

    public String mlng(String lng){
        plng=lng;
        return plng;
    }

}
