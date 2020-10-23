package com.example.insnew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class First_CardsAdapter extends RecyclerView.Adapter<First_CardsAdapter.ViewHolder> {
    private List<String> mylist;
    private Context mycontext;

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

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        //根据列表设置不同的显示
        holder.textView.setText(mylist.get(position));
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
        public TextView textView;
        public Button button;

        public ViewHolder(View itemVieww) {
            super(itemVieww);
            textView = (TextView) itemView.findViewById(R.id.textView);
            button = (Button) itemView.findViewById(R.id.button);
        }
    }


}
