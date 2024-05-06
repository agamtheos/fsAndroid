package com.example.fs_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.datastore.core.DataStore;

import com.example.fs_android.data.response.BaseResponse;
import com.example.fs_android.data.response.batch.BatchResponse;
import com.example.fs_android.databinding.ActivityChartBinding;
import com.example.fs_android.domain.model.batch.Batch;
import com.example.fs_android.domain.model.user.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ChartActivity extends AppCompatActivity {

    // Define Retrofit interface
    interface ChartApiService {
        @GET("/api/v1/funder-statement-batch/{batchId}")
        Call<BaseResponse<BatchResponse>> getChartData(@Path("batchId") String batchId);
    }

    private ChartApiService chartApiService;
    private ActivityChartBinding binding;

    private DataStore<User> dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the Batch object sent as an extra
        String batchId = intent.getSerializableExtra("batchId").toString();
        String token = intent.getSerializableExtra("token").toString();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient(token))
                .baseUrl("http://10.0.2.2:3030/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the Retrofit interface
        chartApiService = retrofit.create(ChartApiService.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set the back button click listener
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        getDetail(batchId);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

    }

    // make get data from repository
    private void getDetail(String batchId) {
        Call<BaseResponse<BatchResponse>> call = chartApiService.getChartData(batchId);
        call.enqueue(new Callback<BaseResponse<BatchResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<BatchResponse>> call, retrofit2.Response<BaseResponse<BatchResponse>> response) {
                if (response.isSuccessful()) {
                    Batch batch = response.body().getData().mapToBatch();
                    createChart(batch);
                    setData(batch);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BatchResponse>> call, Throwable t) {
                Toast.makeText(ChartActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData(Batch batch) {
        binding.batchNameValue.setText(batch.getBatchName());
        // make getStatus to PascalCase
        if (batch.getStatus().equals("REVIEWED")) {
            batch.setStatus("Reviewed");
            binding.statusValue.setTextColor(ContextCompat.getColor(this, R.color.green));
        } else if (batch.getStatus().equals("READY")) {
            batch.setStatus("Ready");
            binding.statusValue.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
        } else {
            batch.setStatus("Rejected");
            binding.statusValue.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        binding.statusValue.setText(batch.getStatus());
        binding.totalFunderFileValue.setText(String.valueOf(batch.getTotalFunderFile()) + " Funder");
        binding.totalSentValue.setText(String.valueOf(batch.getTotalSent()) + " Email");
        binding.totalRejectedValue.setText(String.valueOf(batch.getTotalRejected()) + " Email");
        binding.totalBlacklistValue.setText(String.valueOf(batch.getTotalBlacklisted()) + " Email");

        if (Objects.equals(batch.getApproveBy(), "null")) {
            batch.setApproveBy("-");
        }
        binding.approveByValue.setText(batch.getApproveBy());

        if (batch.getRejectedAt() == null) {
            binding.rejectedAtValue.setText("-");
        } else {
            Date rejectedAt = new Date(Long.parseLong(batch.getRejectedAt()));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String formattedDate = sdf.format(rejectedAt);
            binding.rejectedAtValue.setText(formattedDate);
        }

        if (Objects.equals(batch.getRejectedBy(), "null")) {
            batch.setRejectedBy("-");
        }
        binding.rejectedByValue.setText(batch.getRejectedBy());

        if (batch.getApproveAt() == null) {
            binding.approveAtValue.setText("-");
        } else {
            Date approveAt = new Date(Long.parseLong(batch.getApproveAt()));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String formattedDate = sdf.format(approveAt);
            binding.approveAtValue.setText(formattedDate);
        }

        if (Objects.equals(batch.getCreatedAt(), "null")) {
            batch.setCreatedAt("-");
        } else {
            Date createdAt = new Date(Long.parseLong(batch.getCreatedAt()));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String formattedDate = sdf.format(createdAt);
            binding.createdAtValue.setText(formattedDate);
        }
        binding.createdByValue.setText(batch.getCreatedBy());
    }

    private void createChart(Batch batch) {
        // Initialize PieChart
        PieChart pieChart = findViewById(R.id.chart);

        if (batch.getTotalSent() == 0 && batch.getTotalRejected() == 0 && batch.getTotalBlacklisted() == 0) {
            pieChart.setNoDataText("No data available");
            pieChart.setNoDataTextColor(ContextCompat.getColor(this, R.color.yellow));
            return;
        }

        // Add data entries to the chart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(batch.getTotalSent(), "Total Terkirim")); // Set a minimum value of 0.1f
        entries.add(new PieEntry(batch.getTotalRejected(), "Total Ditolak")); // Set a minimum value of 0.1f
        entries.add(new PieEntry(batch.getTotalBlacklisted(), "Total Diblacklist")); // Set a minimum value of 0.1f

        // Define the data set
        PieDataSet dataSet = new PieDataSet(entries, "Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueLineColor(ContextCompat.getColor(this, R.color.black));

        // Define the data
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);

        // Increase the text size of values
        data.setValueTextSize(16f);
        data.setValueTextColor(ContextCompat.getColor(this, R.color.black));

        // Set data to the chart
        pieChart.setData(data);

        // Add animation to the chart
        pieChart.animateY(1500, Easing.EaseInOutCubic);

        // Refresh the chart
        pieChart.invalidate();
    }

    // Method to create OkHttpClient with interceptor for authorization token header
    private OkHttpClient getOkHttpClient(String token) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + token) // Replace "your_auth_token" with the actual authorization token
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });
        return httpClient.build();
    }

}
