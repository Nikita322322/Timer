package com.example.user.Timer.presentation.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.user.Timer.R;
import com.example.user.Timer.databinding.ItemInListBinding;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by User on 18.05.2018.
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<ModelInPresentationLayer> userList;

    @Inject
    public UserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemInListBinding binding;
        if (view == null) {
            LayoutInflater from = LayoutInflater.from(context);
            binding = ItemInListBinding.inflate(from, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (ItemInListBinding) view.getTag();
        }

        ModelInPresentationLayer item = (ModelInPresentationLayer) getItem(i);
        binding.time.setText(String.valueOf(item.getTime()) + "(goal is " + String.valueOf(item.getGoal()) + ")");
        binding.textViewDate.setText(getDate(item.getDate()));
        if (item.getTime() < item.getGoal()) {
            binding.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checkbox_state_true));
        } else {
            binding.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checkbox_state));
        }
        return binding.getRoot();
    }

    public void deleteUser(int position) {
        userList.remove(position);
        notifyDataSetChanged();
    }

    public void setUserList(List<ModelInPresentationLayer> userList) {
        if (userList != null) {
            this.userList = userList;
        } else {
            this.userList = new ArrayList<>();
        }
    }

    private String getDate(String milliseconds) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(milliseconds));  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
        return date + " " + time;
    }

    public List<ModelInPresentationLayer> getUserList() {
        return userList;
    }
}
