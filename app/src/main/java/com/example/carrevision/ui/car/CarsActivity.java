package com.example.carrevision.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrevision.R;
import com.example.carrevision.adapter.RecyclerAdapter;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.RecyclerViewItemClickListener;
import com.example.carrevision.viewmodel.car.CarListVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarsActivity extends BaseActivity {
    private static final String TAG = "CarsActivity";

    private List<CarEntity> cars;
    private RecyclerAdapter<CarEntity> adapter;
    private CarListVM carsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);

        setTitle(getString(R.string.title_activity_cars));
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.carsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        cars = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position: " + position + " on " + cars.get(position).getId());

                Intent intent = new Intent(CarsActivity.this, CarDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("carId", cars.get(position).getId());
                startActivity(intent);
            }
        });

        FloatingActionButton btnAdd = findViewById(R.id.floatingActionButton);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(CarsActivity.this, CarEditActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        CarListVM.Factory factory = new CarListVM.Factory(getApplication());
        carsVM = new ViewModelProvider(new ViewModelStore(), factory).get(CarListVM.class);
        carsVM.getCars().observe(this, carEntities -> {
            if (carEntities != null) {
                cars = carEntities;
                adapter.setData(cars);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        ArrayList<CarEntity> filtered = new ArrayList<>();
        for (CarEntity ce : cars) {
            if (ce.getPlate().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filtered.add(ce);
            }
        }
        if (filtered.isEmpty()) {
            Toast.makeText(this, "en bas les larmes", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setData(filtered);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        finish();
        return super.onNavigationItemSelected(item);
    }
}
