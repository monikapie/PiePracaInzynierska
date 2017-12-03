package com.monika.pie.onwall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends Activity {

    private Button mBtnCreateTrack;
    private Button mBtnViewTracks;
    private Button mBtnViewTrainings;

    Button.OnClickListener mCreateTrackClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view){
            createTrack();
        }
    };

    private void createTrack() {
        Intent mCreateTrackIntent = new Intent(this,PhotoActivity.class);
        startActivity(mCreateTrackIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnCreateTrack = (Button) findViewById(R.id.button);
        setBtnListenerOrDisable(mBtnCreateTrack, mCreateTrackClickListener, MediaStore.ACTION_IMAGE_CAPTURE);
    }

    private void setBtnListenerOrDisable(Button button, Button.OnClickListener onClickListener, String intentName){
        if(isIntentAvailable(this, intentName))
            button.setOnClickListener(onClickListener);
        else{
            button.setText(getText(R.string.cannot).toString() + " " + button.getText());
            button.setClickable(false);
        }
    }

    public static boolean isIntentAvailable(Context context, String action){
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfoList.size() > 0;
    }
}
