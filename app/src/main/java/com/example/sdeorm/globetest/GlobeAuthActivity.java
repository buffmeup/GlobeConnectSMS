package com.example.sdeorm.globetest;

import android.content.Intent;
import org.json.JSONObject;
import ph.com.globe.connect.AuthenticationActivity;

public class GlobeAuthActivity extends AuthenticationActivity {
    @Override
    public void onSuccess(JSONObject results) {
        // create new intent for response
        Intent response = new Intent();

        // put auth response string
        response.putExtra("auth_response", results.toString());

        // now set activity result
        setResult(this.RESULT_OK, response);
    }
}
