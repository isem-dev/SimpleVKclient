package com.vk.simplevkclient;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONArray;
import org.json.JSONException;

public class FetchUserDataService extends IntentService {

    private final String LOG_TAG = FetchUserDataService.class.getSimpleName();

    public FetchUserDataService() {
        super("FetchUserDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100"));
//        request.secure = false;
//        request.useSystemLanguage = false;

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                    VKApiUser user = new VKApiUser(jsonArray.getJSONObject(0));
                    SharedPreferences preferences = getSharedPreferences("VKPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id", String.valueOf(user.id));
                    editor.putString("first_name", user.first_name);
                    editor.putString("last_name", user.last_name);
                    editor.commit();

                    Log.d(LOG_TAG, "SharedPreferences writed");

                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.e(LOG_TAG, error.toString());
            }
        });
    }

}
