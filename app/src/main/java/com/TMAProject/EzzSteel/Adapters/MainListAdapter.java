package com.TMAProject.EzzSteel.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.Navigation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.TMAProject.EzzSteel.Utils.LangHelper.*;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tarekkma on 2/9/16.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyVH> {
    List<Department> list=new ArrayList<>();
    Context context;
    public MainListAdapter(Context context) {
        this.context=context;
    }
    public  void update(List<Department> newList){
        list = newList;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_department_item, parent, false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, final int position) {
        Department department = list.get(position);
        holder.nameText.setText(getName(department));
        Picasso.with(context).load(department.getFullImgUrl()).noFade().into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(position);
            }
        });
    }

     void onClickItem(int pos) {
        Department d = ((Department) list.get(pos));
         Navigation.handleDepartment(context,d);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyVH extends RecyclerView.ViewHolder {
        TextView nameText;
        CircleImageView imageView;
        public MyVH(View itemView) {
            super(itemView);
            nameText=(TextView) itemView.findViewById(R.id.itemNameText);
            imageView=(CircleImageView)itemView.findViewById(R.id.itemImage);
        }


    }


}
