package com.mteam.nutri.ui.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.mteam.nutri.R;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<Item> {


    static class ViewHolder {
        TextView title;
        TextView location;
        ImageView image;
    }
    public FoodAdapter(@NonNull Context context, List<Item> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.workout_list_row, parent, false);
        }

        Item currentItem = getItem(position);
        ViewHolder holder = new ViewHolder();



        holder.title = listItemView.findViewById(R.id.txtName);
        holder.title.setText(currentItem.getTitle());

        holder.location = listItemView.findViewById(R.id.description);
        holder.location.setText(currentItem.getLocation());

        holder.image = listItemView.findViewById(R.id.imgLogo);
        if(currentItem.getImageResourceId() != 0){
            holder.image.setImageResource(currentItem.getImageResourceId());
        } else {
            Glide.with(holder.image.getContext()).load(currentItem.getImageUrl()).into(holder.image);
        }

        return listItemView;
    }
}
