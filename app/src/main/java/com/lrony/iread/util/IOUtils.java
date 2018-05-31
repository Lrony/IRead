package com.lrony.iread.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Lrony on 18-5-31.
 */
public class IOUtils {

    public static void close(Closeable closeable){
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            //close error
        }
    }
}
