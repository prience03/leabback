/**
 * 
 */
package com.duolebo.appbase.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zlhl
 * @date 2014年6月10日
 */
public class AssetsUtils {

    public static JSONObject loadJSONObject(Context context, String asset) {
        try {
            InputStream stream = context.getAssets().open(asset);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while(null != (line = reader.readLine())) {
                sb.append(line).append("\n");
            }
            reader.close();
            stream.close();
            return (new JSONObject(sb.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
