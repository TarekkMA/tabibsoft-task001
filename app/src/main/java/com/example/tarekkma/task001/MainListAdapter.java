package com.example.tarekkma.task001;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tarekkma.task001.modles.Section;
import com.squareup.picasso.Picasso;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tarekkma on 2/9/16.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyVH> {
    List<Section> list=new ArrayList<>();
    Context context;
    public MainListAdapter(Context context) {
        this.context=context;
    }
    public  void update(List<Section> newList){
        list = newList;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        Section section = list.get(position);
        holder.nameText.setText(section.getNameArabic());
        Picasso.with(context).load(section.getFullImgURL()).noFade().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyVH extends RecyclerView.ViewHolder{
        TextView nameText;
        CircleImageView imageView;
        public MyVH(View itemView) {
            super(itemView);
            nameText=(TextView) itemView.findViewById(R.id.itemNameText);
            imageView=(CircleImageView)itemView.findViewById(R.id.itemImage);
        }
    }


}
