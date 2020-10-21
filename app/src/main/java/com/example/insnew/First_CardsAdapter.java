package com.example.insnew;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class First_CardsAdapter extends RecyclerView.Adapter<First_CardsAdapter.ViewHolder> {
    private List<String> mylist;
    public First_CardsAdapter(List<String> list){
        mylist = list;
    }


    @NonNull
    @Override
    public First_CardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.first_cards_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull First_CardsAdapter.ViewHolder holder, int position) {

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
            textView = itemView.findViewById(R.id.textView);
            button = itemVieww.findViewById(R.id.button);
        }
    }


}
