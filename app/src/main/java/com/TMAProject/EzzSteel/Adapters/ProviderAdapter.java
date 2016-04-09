package com.TMAProject.EzzSteel.Adapters;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.Navigation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;import static com.TMAProject.EzzSteel.Utils.LangHelper.*;


/**
 * Created by TarekkMA on 4/2/16.
 */
public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.VH> {

    List<Provider> providerList = new ArrayList<>();
    Context context;
    int iconResId;
    String type;

    public void updateProviders(List<Provider> list){
        providerList = list;
        notifyDataSetChanged();
    }

    public ProviderAdapter(Context context, int iconResId, String type) {
        this.context = context;
        this.iconResId = iconResId;
        this.type = type;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_provider, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
       Picasso.with(context).load(iconResId).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        holder.textView.setText(getName(providerList.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.handleProvider(context,providerList.get(position),type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ContentLoadingProgressBar loadingProgressBar;
        public VH(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.prov_txt);
            imageView = (ImageView)itemView.findViewById(R.id.prov_ico_iv);
            loadingProgressBar= (ContentLoadingProgressBar)itemView.findViewById(R.id.image_loading);
        }
    }
}
