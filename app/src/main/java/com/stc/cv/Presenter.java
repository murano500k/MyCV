package com.stc.cv;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.EXTRA_TEXT;

/**
 * Created by artem on 3/29/17.
 */

public class Presenter {
    FirebaseDatabase database;
    private JSONObject getJson(){
        try {
            return new JSONObject(database.getReference().getRoot().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getShareJsonIntent(Context c){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(EXTRA_TEXT);
        ClipboardManager clipboardManager = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData =ClipData.newPlainText("CV.json", (CharSequence) getJson());
        clipboardManager.setPrimaryClip(clipData);
    }
}
