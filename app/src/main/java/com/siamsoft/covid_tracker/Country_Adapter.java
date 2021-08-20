package com.siamsoft.covid_tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siamsoft.covid_tracker.api.Countd;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class Country_Adapter extends RecyclerView.Adapter<Country_Adapter.Countview> {

    public Country_Adapter(Context context, List<Countd> list) {
        this.context = context;
        this.list = list;
    }

    private Context context;
 private List<Countd> list;




    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Countview onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.country_item_layout,parent,false);


        return new Countview(view);
    }

    public void filterList(List<Countd> filterList){

        list = filterList;
        notifyDataSetChanged();


    }



    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Country_Adapter.Countview holder, int position) {

    Countd data = list.get(position);

    holder.countcass.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
    holder.countname.setText(data.getCountry());
    holder.ser.setText(String.valueOf(position+1));

        Map<String, String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);

       holder.itemView.setOnClickListener(v -> {
           Intent intent = new Intent(context, MainActivity.class);
           intent.putExtra("country", data.getCountry());
           context.startActivity(intent);
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Countview extends RecyclerView.ViewHolder {

        private TextView ser, countname, countcass;
        private ImageView imageView;


        public Countview(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            ser = itemView.findViewById(R.id.sno);
            countname = itemView.findViewById(R.id.countryName);
            countcass = itemView.findViewById(R.id.cases);
            imageView = itemView.findViewById(R.id.flag);

        }
    }



}
