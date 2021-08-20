package com.siamsoft.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.siamsoft.covid_tracker.api.APIUtilities;
import com.siamsoft.covid_tracker.api.Countd;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView totcon, totact, totreco, totdeath, tottests;
    private TextView tdcon,tdrec, tddeth, dates;
    private PieChart pieChart;

    private List<Countd> list;

    String country = "Bangladesh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        if(getIntent().getStringExtra("country") != null)
            country = getIntent().getStringExtra("country");

        init();

        TextView cname = findViewById(R.id.country);

        cname.setText(country);

        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,CountryAct.class)));

        APIUtilities.getApint().getCountd().enqueue(new Callback<List<Countd>>() {
            @Override
            public void onResponse(Call<List<Countd>> call, Response<List<Countd>> response) {

                list.addAll(response.body());

                for(int i = 0; i <list.size(); i++)
                {
                    if(list.get(i).getCountry().equals(country)){

                        int confirm =Integer.parseInt( list.get(i).getCases());
                        int active =Integer.parseInt( list.get(i).getActive());
                        int recovered =Integer.parseInt( list.get(i).getRecovered());
                        int death =Integer.parseInt( list.get(i).getDeaths());
                        int test = Integer.parseInt( list.get(i).getTests());


                        totcon.setText(NumberFormat.getInstance().format(confirm));
                        totact.setText(NumberFormat.getInstance().format(active));
                        totreco.setText(NumberFormat.getInstance().format(recovered));
                        totdeath.setText(NumberFormat.getInstance().format(death));
                        tottests.setText(NumberFormat.getInstance().format(test));

                        tddeth.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        tdcon.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        tdrec.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                        setText(list.get(i).getUpdated());

                        pieChart.addPieSlice( new PieModel("Confirm",confirm, getResources().getColor(R.color.yellow)));
                        pieChart.addPieSlice( new PieModel("Active",active, getResources().getColor(R.color.blue)));
                        pieChart.addPieSlice( new PieModel("Recovered",recovered, getResources().getColor(R.color.green_pie)));
                        pieChart.addPieSlice( new PieModel("Death",death, getResources().getColor(R.color.red_pie)));
                        pieChart.startAnimation();



                    }

                }

            }

            @Override
            public void onFailure(Call<List<Countd>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "ERROR : " +t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setText(String updated) {

        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        long miliseconds = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);

        dates.setText(" Updated at "+format.format(calendar.getTime()));

    }

    private void init() {

        totcon = findViewById(R.id.totalcon);
        totact = findViewById(R.id.totalact);
        totreco = findViewById(R.id.totalrec);
        totdeath = findViewById(R.id.totaldet);
        tottests = findViewById(R.id.totaltest);
        tdcon = findViewById(R.id.todaycon);
        tdrec = findViewById(R.id.todayrec);
        tddeth = findViewById(R.id.todaydet);
        pieChart = findViewById(R.id.pieChart);
        dates = findViewById(R.id.date);


    }
}