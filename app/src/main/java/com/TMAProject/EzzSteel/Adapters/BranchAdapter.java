package com.TMAProject.EzzSteel.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.GenralHelper;
import com.TMAProject.EzzSteel.Utils.Navigation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;import static com.TMAProject.EzzSteel.Utils.LangHelper.*;


/**
 * Created by TarekkMA on 4/2/16.
 */
public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.VH> {

    public static final int BRANCH_SEARCH=70;
    public static final int BRANCH_NORMAL=90;


    List<Branche> branchList = new ArrayList<>();
    Context context;
    int iconResId;

    int style;

    String type;

    public void updateBranches(List<Branche> list){
        branchList = list;
        notifyDataSetChanged();
    }

    public BranchAdapter(Context context, String type,int style) {
        this.context = context;
        this.type = type;
        this.iconResId = GenralHelper.getIconByParentName(type,false);
        this.style = style;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (style==BRANCH_SEARCH)
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_provider, parent, false));
        else if (style==BRANCH_NORMAL)
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branch, parent, false));
        else
            return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final Branche b = branchList.get(position);
        if(b.getImg()!=null && !b.getImg().equalsIgnoreCase(""))
            Picasso.with(context).load(b.getFullImgUrl()).fit().into(holder.imageView);
        else
            Picasso.with(context).load(iconResId).into(holder.imageView);

        holder.textView.setText(getName(b));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.handleBranch(context,b,type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public VH(View itemView) {
            super(itemView);
            if (style==BRANCH_SEARCH) {
                textView = (TextView) itemView.findViewById(R.id.prov_txt);
                imageView = (ImageView) itemView.findViewById(R.id.prov_ico_iv);
            }else if (style==BRANCH_NORMAL){
                textView = (TextView) itemView.findViewById(R.id.branch_text);
                imageView = (ImageView) itemView.findViewById(R.id.branch_icon);
            }

        }
    }
}
