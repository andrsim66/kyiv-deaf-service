package com.smartcity.kyivdeafservice.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.smartcity.kyivdeafservice.app.R;

import java.io.File;

/**
 * This utility class is for device related stuff.
 *
 * @author Sotti https://plus.google.com/+PabloCostaTirado/about
 */
public class UtilsDevice {
    /**
     * Returns the screen width in pixels
     *
     * @param context is the context to get the resources
     * @return the screen width in pixels
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }

    public static void loadImage(String url, ImageView imageView, int width) {
        AQuery aq = new AQuery(imageView);
        if (url != null && url.length() > 0) {
            File file = aq.getCachedFile(url);
            if (file != null) {
                aq.id(imageView).image(file, width);
            } else {
                aq.id(imageView).image(url, false, true, width, 0);
            }
        } else {
            aq.id(imageView).image(R.drawable.ic_account_circle_grey600_24dp);
        }
    }
}
