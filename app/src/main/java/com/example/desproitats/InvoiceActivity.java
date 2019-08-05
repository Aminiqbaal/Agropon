package com.example.desproitats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InvoiceActivity extends AppCompatActivity {
TextView norek, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        norek = findViewById(R.id.norek);
        harga = findViewById(R.id.bayar);

        norek.setText(getIntent().getStringExtra("norek"));
        harga.setText(getIntent().getStringExtra("harga"));

    }
}
