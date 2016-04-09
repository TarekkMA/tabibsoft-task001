package com.TMAProject.EzzSteel.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Notification;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.Navigation;

import java.util.ArrayList;
import java.util.List;

import static com.TMAProject.EzzSteel.Utils.LangHelper.getName;

/**
 * Created by TarekkMA on 4/7/16.
 */
public class NotifcationAdapter extends RecyclerView.Adapter<NotifcationAdapter.VH> {
    List<Notification> notificationList = new ArrayList<>();
    Context c;

    public NotifcationAdapter(Context c) {
        this.c = c;
    }

    public void updateList(List<Notification> list){
        notificationList= list ;
        notifyDataSetChanged();
    }

    @Override
    public NotifcationAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notifcation, parent, false));
    }

    @Override
    public void onBindViewHolder(NotifcationAdapter.VH holder, final int position) {
        holder.textView.setText(getName(notificationList.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.handleNotifcation(c,getName(notificationList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView textView;
        public VH(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textView);
        }
    }
}
