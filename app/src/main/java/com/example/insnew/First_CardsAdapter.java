package com.example.insnew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class First_CardsAdapter extends RecyclerView.Adapter<First_CardsAdapter.ViewHolder> {
    private List<String> mylist;
    private Context mycontext;
    private OnItemClickLitener   mOnItemClickLitener;

    private static final String TAG = "First_CardsAdapter";

    public First_CardsAdapter(Context context,List<String> list){
        mylist = list;
        mycontext = context;

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
        holder.news_title.setText(mylist.get(position));

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
        if (mylist==null) {
            return 0;
        } else {
            return mylist.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView news_title,news_src;
        public ImageView news_photo,news_logo;

        public ViewHolder(View itemVieww) {
            super(itemVieww);
            news_title = (TextView)itemView.findViewById(R.id.news_title);
            news_src = (TextView)itemView.findViewById(R.id.news_title);
            news_photo = (ImageView)itemView.findViewById(R.id.news_photo);
            news_logo = (ImageView)itemView.findViewById(R.id.news_logo);


        }
    }

    //为点击事件设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
