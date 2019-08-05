package com.example.desproitats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

public class Detail_Budidaya extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterDetailBudidaya adapter;
    String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__budidaya);
        recyclerView = findViewById(R.id.rv_detailhasilbudidaya);
        if (getIntent().getStringExtra("namakota").equals("Malang"))
            data = ListDetailBudidaya.malang;
        else if (getIntent().getStringExtra("namakota").equals("Jombang"))
            data = ListDetailBudidaya.jombang;
        else if (getIntent().getStringExtra("namakota").equals("Surabaya"))
            data = ListDetailBudidaya.surabaya;
        else if (getIntent().getStringExtra("namakota").equals("Blitar"))
            data = ListDetailBudidaya.blitar;

        adapter = new AdapterDetailBudidaya(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
