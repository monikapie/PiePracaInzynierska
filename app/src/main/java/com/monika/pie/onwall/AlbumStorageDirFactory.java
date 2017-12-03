package com.monika.pie.onwall;

import java.io.File;

/**
 * Created by monik on 16.11.2017.
 */

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
