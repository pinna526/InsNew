package com.example.insnew;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class First_CardsAdapter extends RecyclerView.Adapter<First_CardsAdapter.ViewHolder> {
    private List<JSONObject> mylist;
    private Context mycontext;
    private OnItemClickLitener mOnItemClickLitener;
    Bitmap bm = null;

    private static final String TAG = "First_CardsAdapter";

    public First_CardsAdapter(Context context, List<JSONObject> list) {
        mylist = list;
        mycontext = context;
        Log.i(TAG, "First_CardsAdapter: " + mylist);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.first_cards_item, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_cards_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        //根据列表设置不同的显示
        //            Log.i(TAG, "onBindViewHolder: "+mylist);
//            Log.i(TAG, "onBindViewHolder: "+position);
//            Log.i(TAG, "onBindViewHolder: "+mylist.get(position));
//            Log.i(TAG, "onBindViewHolder: "+mylist.get(position).getString("title"));

        holder.news_title.setText(mylist.get(position).getString("title"));
        holder.news_src.setText(mylist.get(position).getString("src"));
        Glide.with(mycontext).load(mylist.get(position).getString("pic")).into(holder.news_photo);

        //即时加载图片
        //× 会阻塞所有视图的加载，会出现很久的空白，被弃用
//            Bitmap pic_bm = stringToBitmap(mylist.get(position).getString("pic"));
//            Bitmap bitmap = getHttpBitmap(url);
//            holder.news_photo.setImageBitmap(pic_bm);

        //异步加载图片
        //× system error ，不明原因，所以被弃用
//            MyBitmapAsyncTask myBitmapAsyncTask = new MyBitmapAsyncTask();
//            Bitmap pic_bm = myBitmapAsyncTask.execute(mylist.get(position).getString("src")).get();
//            holder.news_photo.setImageBitmap(pic_bm);


        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            });

        }
//        Log.i(TAG, "onBindViewHolder: "+holder);
    }

    @Override
    public int getItemCount() {
        if (mylist == null) {
            return 0;
        } else {
            return mylist.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView news_title, news_src;
        public ImageView news_photo, news_logo;

        public ViewHolder(View itemVieww) {
            super(itemVieww);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_src = (TextView) itemView.findViewById(R.id.news_src);
            news_photo = (ImageView) itemView.findViewById(R.id.news_photo);
            news_logo = (ImageView) itemView.findViewById(R.id.news_logo);


        }
    }

    //为点击事件设置回调接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * string转换为bitmap
     *
     * @param string
     * @return 参考：https://blog.csdn.net/xiaoxiangyuhai/article/details/77863063
     */
    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
//
//    //异步加载图片
//    public class MyBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            //从url请求图片资源，转换为bitmap
//            Bitmap bm = null;
//            try {
//                URL iconUrl = new URL(strings[0]);
//                URLConnection conn = iconUrl.openConnection();
//                HttpURLConnection http = (HttpURLConnection) conn;
//
//                int length = http.getContentLength();
//
//                conn.connect();
//                // 获得图像的字符流
//                InputStream is = conn.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is, length);
//                bm = BitmapFactory.decodeStream(bis);
//                bis.close();
//                is.close();// 关闭流
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return bm;
//        }
//
//
//    }

}
