package com.siamsoft.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.siamsoft.covid_tracker.api.APIUtilities;
import com.siamsoft.covid_tracker.api.Countd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryAct extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Countd>list;
    private ProgressDialog dialog;
    private EditText searchbar;
    private Country_Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.countries);
        list = new ArrayList<>();

        searchbar = findViewById(R.id.sb);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new Country_Adapter(this,list);
        recyclerView.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.show();


        APIUtilities.getApint().getCountd().enqueue(new Callback<List<Countd>>() {
            @Override
            public void onResponse(Call<List<Countd>> call, Response<List<Countd>> response) {

                list.addAll(response.body());

                adapter.notifyDataSetChanged();


                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Countd>> call, Throwable t) {

                Toast.makeText(CountryAct.this,""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());


            }
        });
    }

    private void filter(String text) {
        List<Countd> filt = new ArrayList<>();
        for (Countd items :list)
        {
            if (items.getCountry().toLowerCase().contains(text.toLowerCase())){

                filt.add(items);
            }
        }

        adapter.filterList(filt);


    }
}