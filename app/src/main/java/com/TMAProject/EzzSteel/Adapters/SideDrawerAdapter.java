package com.TMAProject.EzzSteel.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.LangHelper;
import com.TMAProject.EzzSteel.Utils.Navigation;

import java.util.ArrayList;
import java.util.List;import static com.TMAProject.EzzSteel.Utils.LangHelper.*;

/**
 * Created by tarekkma on 2/10/16.
 */

public class SideDrawerAdapter extends RecyclerView.Adapter<SideDrawerAdapter.MyVH> {



    private String DEPARTMENT_KEY;

    public static String COMPLAINT_OPTION = "Complaint";
    public static String SEARCH_OPTION="Search" ;
    public static String HOME_OPTION="Home" ;
    public static String SETTING_OPTION = "Setting";
    public static String NOTIFICATION_OPTION = "Notification";


    private List<String> list = new ArrayList<>();
    private int inserstIndex = 1;
    private String loadingText = "Loading ...";
    private List<Object> items = new ArrayList<>();

    Context context;

    public SideDrawerAdapter(Context context) {
        LangHelper.renameNavOptions(context);
        items.add(HOME_OPTION);
        items.add(SEARCH_OPTION);
        items.add(COMPLAINT_OPTION);
        items.add(SETTING_OPTION);
        items.add(NOTIFICATION_OPTION);
        items.add(inserstIndex, loadingText);
        this.context=context;
    }

    public void addDepartments(List<Department> sections) {
        items.remove(inserstIndex);
        items.addAll(inserstIndex, sections);
        notifyDataSetChanged();
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nav_item, parent, false);
        return new MyVH(v, viewType);
    }

    @Override
    public void onBindViewHolder(MyVH holder,int position) {
            final int cPos = position;
            if (items.get(cPos) instanceof String) {
                if(((String) items.get(cPos)).equalsIgnoreCase(loadingText))return;
                holder.textView.setText(((String) items.get(cPos)));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickItem(cPos);
                    }
                });
            } else if (items.get(cPos) instanceof Department) {
                Department d = ((Department) items.get(cPos));
                holder.textView.setText(getName(d));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDepartment(cPos);
                    }
                });
            }
    }

    void onClickDepartment(int pos) {
        Department d = ((Department) items.get(pos));
        Navigation.handleDepartment(context,d);
    }
    void onClickItem(int pos){
        Navigation.handleNavItem(context,(String) items.get(pos));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyVH extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView icon;
        public MyVH(View itemView, int ViewType) {
            super(itemView);
                textView = (TextView) itemView.findViewById(R.id.rowText);
                icon = (ImageView) itemView.findViewById(R.id.rowIcon);
        }
    }

}
