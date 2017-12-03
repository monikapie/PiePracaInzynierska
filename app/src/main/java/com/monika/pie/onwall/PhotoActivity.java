package com.monika.pie.onwall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_RECORD_VIDEO = 2;
    private static final String JPEG_FILE_PREFIX = "ONWALL_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private String mCurrentPhotoPath = null;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private void dispatchTakePictureIntent(int actionCode){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (actionCode){
            case ACTION_TAKE_PHOTO:
                try {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName(), createImageFile()));
                } catch (IOException ex){
                    Log.d(PhotoActivity.class.getName(),"Cannot find requested photo: " + ex.getLocalizedMessage());
                    mCurrentPhotoPath = null;
                }
                break;
            default:
                break;
        }
        startActivityForResult(takePictureIntent, actionCode);
    }

    private File createImageFile() throws IOException{
        @SuppressLint("SimpleDateFormat") String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timestamp + "_";
        File albumFile = getAlbumDir();
        File imageFile = File.createTempFile(imageFileName,JPEG_FILE_SUFFIX, albumFile);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private File getAlbumDir(){
        File storageDir = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            if(storageDir != null){
                if( !storageDir.mkdirs()){
                    if(!storageDir.exists()){
                        Log.d(PhotoActivity.class.getName(), "failed to create directory for photos.");
                    }
                }
            }
        } else{
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE");
        }
        return storageDir;
    }

    private String getAlbumName(){
        return getString(R.string.album_name);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        else
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        dispatchTakePictureIntent(ACTION_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case ACTION_TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    handleCameraPhoto();
                }
                break;
            }
        }
    }

    private void handleCameraPhoto(){
        if(mCurrentPhotoPath != null){
            Intent imageIntent = new Intent(this, ImageActivity.class);
            imageIntent.putExtra("imgPath", mCurrentPhotoPath);
            startActivity(imageIntent);
        }
    }
}
