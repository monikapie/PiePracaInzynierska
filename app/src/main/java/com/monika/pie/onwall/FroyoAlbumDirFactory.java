package com.monika.pie.onwall;

import android.os.Environment;

import java.io.File;

/**
 * Created by monik on 16.11.2017.
 */

public class FroyoAlbumDirFactory extends AlbumStorageDirFactory{

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),albumName);
    }
}
