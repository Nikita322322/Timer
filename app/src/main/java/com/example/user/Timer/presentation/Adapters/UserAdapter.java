package com.example.user.Timer.presentation.Adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.user.Timer.R;
import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.databinding.ItemInListBinding;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by User on 18.05.2018.
 */

public class UserAdapter extends /*PagedListAdapter<User, RecyclerView.ViewHolder>,*/RecyclerView.Adapter {

    Context context;
    List<ModelInPresentationLayer> userList = new ArrayList<>();
    private int cellWidth, cellHeight;
    private int maxPosition = 0;

    @Inject
    public UserAdapter(Context context) {
        //super(User.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater1 = LayoutInflater.from(parent.getContext());
        ItemInListBinding binding = DataBindingUtil.inflate(inflater1, R.layout.item_in_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ModelInPresentationLayer item = userList.get(position);
        if (maxPosition < position) {
            maxPosition = position;
            viewHolder.binding.image.setModel(item, 300);
        } else {
            viewHolder.binding.image.setModel(item, 0);
        }
    }

    public void setCellWidthAndHeight(int cellWidth, int cellHeight) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    private String getDate(String milliseconds) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(milliseconds));  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
        return date + " " + time;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemInListBinding binding;

        ViewHolder(ItemInListBinding binding) {
            super(binding.getRoot());
            itemView.setLayoutParams(new LinearLayout.LayoutParams(cellWidth,
                    cellHeight));
            this.binding = binding;
        }
    }

    public void setData(List<ModelInPresentationLayer> userList) {
        this.userList.addAll(userList);
        notifyItemRangeInserted((this.userList.size() - 1) - (userList.size() - 1), userList.size());
    }
}
