package com.example.desproitats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class detail_olahan extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterDetailOlahan adapter;
    String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_olahan);
        recyclerView = findViewById(R.id.rv_detailhasilolahan);
        if (getIntent().getStringExtra("namakota").equals("Malang"))
            data = ListDetailOlahan.malang;
        else if (getIntent().getStringExtra("namakota").equals("Jombang"))
            data = ListDetailOlahan.jombang;
        else if (getIntent().getStringExtra("namakota").equals("Surabaya"))
            data = ListDetailOlahan.surabaya;
        else if (getIntent().getStringExtra("namakota").equals("Blitar"))
            data = ListDetailOlahan.blitar;

        adapter = new AdapterDetailOlahan(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
