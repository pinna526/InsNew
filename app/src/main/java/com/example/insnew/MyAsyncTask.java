package com.example.insnew;

import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyAsyncTask extends AsyncTask<String, Void, List<JSONObject>> {
    private static final String TAG = "MyAsyncTask";
    JSONObject postData;
    String title;
    List<JSONObject> data;

    @Override
    protected List<JSONObject> doInBackground(String[] strings) {
        try {
            Log.i(TAG, "doInBackground: 开始执行啦");
            //根据网站规则对url进行处理
            title = strings[0];
            //对应的接口暂时停用
//            String en_sign = "appid3878formatjsonn_channel" + title + "n_num40n_start0time15458294668ca5bb8aab8b8dc47012526bfb3f0d15";
//            String md5_sign = getMD5(en_sign);
//            String tmp_url = "https://oyen.api.storeapi.net/pyi/87/206?appid=3878&format=json&n_channel=" + title + "&n_num=40&n_start=0&sign=" + md5_sign + "&time1545829466";

            String tmp_url = "https://api.jisuapi.com/news/get?channel=" + title + "&start=0&num=40&appkey=16b1deeef6db4623";
            Log.i(TAG, "doInBackground: " + tmp_url);
            URL url = new URL(tmp_url);

            // 创建连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestProperty("Authorization", "someAuthString");

            if (this.postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);
                Log.i(TAG, "doInBackground: " + response);
                JSONObject jsonObj = new JSONObject(response);
                String result = jsonObj.getString("result");
                JSONObject jData = new JSONObject(result);

                data = new ArrayList<>();
                if (jData.get("list") != null) {
                    /**把list转换成jsonArray对象**/
                    JSONArray jarr = jData.getJSONArray("list");
                    /**循环list对象**/
                    for (int i = 0; i < jarr.length(); i++) {
                        data.add(jarr.getJSONObject(i));
                    }
                }

            } else {
                // Status code is not 200
                // Do something to handle the error
            }

        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return data;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = sb.toString();
        return str;
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            Log.i(TAG, "getMD5:ERROR!! ");
            return null;
        }
    }
}
