package com.monika.pie.onwall;

import android.os.Environment;

import java.io.File;

/**
 * Created by monik on 16.11.2017.
 */

public class BaseAlbumDirFactory extends AlbumStorageDirFactory{
    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File (Environment.getExternalStorageDirectory() + CAMERA_DIR + albumName);
    }
}
