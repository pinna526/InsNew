package com.example.insnew;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class First_Fragment extends Fragment {
    private static final String TAG = "First_Fragment";
    RecyclerView recyclerView;
    TextView news_title_show;
    ImageView news_photo_show;
    FrameLayout show_frame;
    String title;
    LinearLayoutManager manager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static First_Fragment newInstance(String str) {
        First_Fragment ff = new First_Fragment();
        Bundle bdl = new Bundle();
        bdl.putString("title", str);
        ff.setArguments(bdl);
        return ff;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
//        Log.i(TAG, "onCreateView: 该fragment已被唤醒");
        //获取从activity传来的title值
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.first_cards);
        news_title_show = (TextView) view.findViewById(R.id.news_title_show);
        news_photo_show = (ImageView) view.findViewById(R.id.news_photo_show);
        show_frame = (FrameLayout) view.findViewById(R.id.show_frame);

        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        Log.i(TAG, "onCreateView: "+title);

        //初始化卡片填充内容[测试]
//        List<String> list = new ArrayList<>();
//        for (int i=0;i<10;i++){
//            list.add("第"+i+"张:"+title);
//        }
        //异步请求数据
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        try {
            myAsyncTask.execute(title).get();
//            Log.i(TAG, "onCreate: "+data.get(1).get("title"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //添加上滑隐藏动作
        //参考：https://www.jianshu.com/p/f384032572d6
        recyclerView.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                Resources resources = getActivity().getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                float density = dm.density;
                int width = dm.widthPixels;
                int height = dm.heightPixels;
                show_frame.animate()
                        .translationY(-height)
                        .setDuration(800)
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();

            }

            @Override
            public void onShow() {
                show_frame.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).setDuration(800).start();
            }
        });


//        //设置为true保证每个card尺寸固定，不需要根据内容重新计算
//        recyclerView.setHasFixedSize(true);
        //设置card滚动的布局和方向
        //设置圆角
//        recyclerView.setOutlineProvider(new ViewOutlineProvider(){
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),30);
//            }
//        });
//        recyclerView.setClipToOutline(true);

        //将这部分代码转移到内部类MyAsyncTask的onPostExecute函数里
//        First_CardsAdapter first_cardsAdapter = new First_CardsAdapter(getActivity(),data);
//        recyclerView.setAdapter(first_cardsAdapter);
//
//        first_cardsAdapter.notifyDataSetChanged();
//        recyclerView.setLayoutManager(new LinearLayoutManager(
//                        getActivity(),LinearLayoutManager.HORIZONTAL,false));

//        First_CardsAdapter first_cardsAdapter = new First_CardsAdapter();
//        //添加点击事件【打开一个新的activity展示新闻详情页面】
//        first_cardsAdapter.setOnItemClickLitener(new First_CardsAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //打开一个新的Activity
//                Intent next = new Intent(getActivity(),SecondActivity.class);
//                next.putExtra("news_data_title",title);  //将文章标题传递给下一个页面
//                startActivity(next);
//            }
//        });
        return view;
    }


    //异步请求网络资源
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
                    JSONObject jsonObj = JSONObject.parseObject(response);
                    String result = jsonObj.getString("result");
                    JSONObject jData = JSONObject.parseObject(result);

                    data = new ArrayList<>();
                    if (jData.get("list") != null) {
                        /**把list转换成jsonArray对象**/
                        JSONArray jarr = jData.getJSONArray("list");
                        /**循环list对象**/
                        for (int i = 0; i < jarr.size(); i++) {
                            data.add(jarr.getJSONObject(i));

                            //为了避免麻烦，在这里直接将url转换为bitmap替换进json
                            //×由于占用整个显示加载的时间，所以弃用，转为在Adapter里操作
//                            String pic_url = jarr.getJSONObject(i).getString("pic");
//                            Bitmap pic_bm = getBitmap(jarr.getJSONObject(i).getString("pic"));
//                            String pic_str = bitmapToString(pic_bm);
//                            jarr.getJSONObject(i).put("pic", pic_str);
//                            Log.i(TAG, "doInBackground: " + jarr.getJSONObject(i).getString("pic"));
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

        //线程结束后回主线程
        @Override
        protected void onPostExecute(final List<JSONObject> jsonObjects) {
            super.onPostExecute(jsonObjects);
            Log.i(TAG, "onPostExecute: 开始了");
            //提取出第一个元素放在展示位
            final JSONObject json_show = jsonObjects.get(0);
            jsonObjects.remove(0);
            news_title_show.setText(json_show.getString("title"));
            Glide.with(getActivity()).load(json_show.getString("pic")).into(news_photo_show);

            //为卡片填充数据
            First_CardsAdapter first_cardsAdapter = new First_CardsAdapter(getActivity(), jsonObjects);
            recyclerView.setAdapter(first_cardsAdapter);

            first_cardsAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(manager);

            //添加点击事件【打开一个新的activity展示新闻详情页面】
            first_cardsAdapter.setOnItemClickLitener(new First_CardsAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    //打开一个新的Activity
                    Intent next = new Intent(getActivity(), SecondActivity.class);
                    next.putExtra("news_data_title", jsonObjects.get(position).toJSONString());  //将文章传递给下一个页面
                    startActivity(next);
                }
            });
            show_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next = new Intent(getActivity(), SecondActivity.class);
                    next.putExtra("news_data_title", json_show.toJSONString());  //将文章传递给下一个页面
                    startActivity(next);
                }
            });


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
        public String getMD5(String str) {
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

        /**
         * 获取网落图片资源
         *
         * @param url
         * @return 参考：https://www.open-open.com/lib/view/open1329994245811.html
         */
        public Bitmap getBitmap(final String url) {
            Bitmap bm = null;
            try {
                URL iconUrl = new URL(url);
                URLConnection conn = iconUrl.openConnection();
                HttpURLConnection http = (HttpURLConnection) conn;

                int length = http.getContentLength();

                conn.connect();
                // 获得图像的字符流
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, length);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();// 关闭流
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bm;
        }


        /**
         * bitmap格式转换为string
         *
         * @param bitmap
         * @return 参考：https://blog.csdn.net/xiaoxiangyuhai/article/details/77863063
         */
        public String bitmapToString(Bitmap bitmap) {
            //将Bitmap转换成字符串
            String string = null;
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] bytes = bStream.toByteArray();
            string = Base64.encodeToString(bytes, Base64.DEFAULT);
            return string;
        }

    }

    /**
     * 内部抽象类，继承了recycler的滚动事件，将在上面被重写
     */
    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 20;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                Log.i(TAG, "onScrolled: "+manager.findFirstCompletelyVisibleItemPosition());
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                int position = manager.findFirstCompletelyVisibleItemPosition();
                if(position<2){
                    onShow();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }
            }
            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy;
            }

        }

        public abstract void onHide();

        public abstract void onShow();
    }


}