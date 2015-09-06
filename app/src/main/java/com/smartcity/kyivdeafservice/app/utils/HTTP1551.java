package com.smartcity.kyivdeafservice.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by voronsky on 06.09.15.
 */
public class HTTP1551 {
    /**
     * 1551.gov.ua req
     * "a.title" - get title of news
     * ("https://1551.gov.ua" + element.attr("href")) - get expandable by link from title
     * "div.date" - get date of news
     * "span" - get time of news
     */

    public ArrayList<String> newsTitleDateTime(String string) {
        ArrayList<String> newsList = new ArrayList<>();
        try {
            Document document = Jsoup.connect("https://1551.gov.ua/news/").get();
            Elements titleElements = document.select(string);

            for (Element element : titleElements) {
                newsList.add(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public ArrayList<Bitmap> loadImage() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://1551.gov.ua/news/").get();
            Elements titleElements = doc.select("a.title");

            for (Element element : titleElements) {
                URL url = new URL("https://1551.gov.ua" + element.attr("href"));
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                bitmaps.add(bmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmaps;
    }
}
