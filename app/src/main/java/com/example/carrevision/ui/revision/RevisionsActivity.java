package com.example.carrevision.ui.revision;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.example.carrevision.adapter.RevisionRecyclerAdapter;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.RecyclerViewItemClickListener;
import com.example.carrevision.viewmodel.revision.RevisionListVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RevisionsActivity extends BaseActivity {

    private static final String TAG = "RevisionsActivity";

    private List<CompleteRevision> revisions;
    private RevisionRecyclerAdapter adapter;
    private RevisionListVM revisionsVM;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_list, frameLayout);

        setTitle(R.string.title_activity_revisions);
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        revisions = new ArrayList<>();
        adapter = new RevisionRecyclerAdapter (new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position: " + position + " on " + revisions.get(position).revision.getId());

                Intent intent = new Intent(RevisionsActivity.this, RevisionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("revisionId", revisions.get(position).revision.getId());
                startActivity(intent);
            }
        }, this);

        FloatingActionButton btnAdd = findViewById(R.id.button_add);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(RevisionsActivity.this, RevisionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        RevisionListVM.Factory factory = new RevisionListVM.Factory(getApplication());
        revisionsVM = new ViewModelProvider(new ViewModelStore(), factory).get(RevisionListVM.class);
        revisionsVM.getRevisions().observe(this, revisionEntities -> {
            if (revisionEntities != null) {
                revisions = revisionEntities;
                adapter.setData(revisions);
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
     * Searches in the revisions list for the typed text
     * @param text Text to search
     */
    private void filter(String text) {
        ArrayList<CompleteRevision> filtered = new ArrayList<>();
        for (CompleteRevision cr : revisions) {
            if (cr.completeCar.car.getPlate().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filtered.add(cr);
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
