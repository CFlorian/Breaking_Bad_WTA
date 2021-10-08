package com.cflorian.breakingbadwta.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cflorian.breakingbadwta.R;
import com.cflorian.breakingbadwta.model.DbData;
import com.cflorian.breakingbadwta.model.ModelCharacter;
import com.cflorian.breakingbadwta.utils.CircleTransform;
import com.cflorian.breakingbadwta.utils.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterModelAdapter extends RecyclerView.Adapter<CharacterModelAdapter.ViewHolder> {

    private Context context;
    private DbData conn;
    ArrayList<ModelCharacter> list;
    private OnItemClickListener clickListener;

    public CharacterModelAdapter(Context context, ArrayList<ModelCharacter> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelCharacter model = list.get(position);
        holder.tvName.setText(model.getName());
        holder.tvNick.setText(model.getNickname());
        Picasso.get().load(model.getImg())
                .transform(new CircleTransform())
                .resize(150, 150).into(holder.ivImg);
        if (model.getFav().equals("1")){
            holder.tbnFav.setBackgroundResource(R.drawable.ic_android_heart_on);
            holder.tbnFav.setChecked(true);
        }else if (model.getFav().equals("0")){
            holder.tbnFav.setBackgroundResource(R.drawable.ic_android_heart_off);
            holder.tbnFav.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvNick;
        ToggleButton tbnFav;
        ImageView ivImg;
        LinearLayout llayout;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvNick = v.findViewById(R.id.tvNickname);
            ivImg = v.findViewById(R.id.ivImg);
            tbnFav = v.findViewById(R.id.tbnFav);
            tbnFav.setOnClickListener(this);
            llayout = v.findViewById(R.id.lLinear);
            llayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null){
                clickListener.onClick(v, getAdapterPosition());
            }

        }
    }
}
