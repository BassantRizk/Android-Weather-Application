package com.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>{

    public ArrayList<CityInfo>cities;
    LayoutInflater inflater;
    RecyclerlickListener listener;

    public CityListAdapter(ArrayList<CityInfo> cityInfo, Context context, RecyclerlickListener listener) {
        this.cities = cityInfo;
        this.inflater = LayoutInflater.from(context);
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityInfo cityInfo = cities.get(position);
        holder.city.setText(cityInfo.city);
        holder.temp.setText(cityInfo.random);

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView city;
        TextView temp;

        public ViewHolder(View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city_txt);
            temp = itemView.findViewById(R.id.temp_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,getAdapterPosition());
        }
    }
    public interface RecyclerlickListener {
        void onItemClick(View v, int position);
    }


}
