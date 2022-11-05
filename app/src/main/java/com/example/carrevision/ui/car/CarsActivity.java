package com.example.carrevision.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrevision.R;
import com.example.carrevision.adapter.CarRecyclerAdapter;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.viewmodel.car.CarListVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Cars activity class
 */
public class CarsActivity extends BaseActivity {
    private static final String TAG = "CarsActivity";

    private List<CompleteCar> cars;
    private CarRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_list, frameLayout);

        setTitle(getString(R.string.title_activity_cars));
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        cars = new ArrayList<>();
        adapter = new CarRecyclerAdapter((v, position) -> {
            Log.d(TAG, "Clicked position: " + position + " on car " + cars.get(position).car.getId());

            Intent intent = new Intent(CarsActivity.this, CarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("carId", cars.get(position).car.getId());
            startActivity(intent);
        });

        FloatingActionButton btnAdd = findViewById(R.id.button_add);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(CarsActivity.this, CarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        CarListVM.Factory factory = new CarListVM.Factory(getApplication());
        CarListVM carsVM = new ViewModelProvider(new ViewModelStore(), factory).get(CarListVM.class);
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

    /**
     * Searches in the cars list for the typed text
     * @param text Text to search
     */
    private void filter(String text) {
        ArrayList<CompleteCar> filtered = new ArrayList<>();
        for (CompleteCar ce : cars) {
            if (ce.car.getPlate().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filtered.add(ce);
            }
        }
        if (filtered.isEmpty()) {
            showSnack(R.string.no_matching_item);
        } else {
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
