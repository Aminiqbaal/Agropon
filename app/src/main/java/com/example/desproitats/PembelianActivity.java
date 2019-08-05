package com.example.desproitats;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringJoiner;

public class PembelianActivity extends AppCompatActivity {
    Button plus, minus, pay;
    TextView alamat, namaBarang, hargaBarang, jumlah, totalHargaBarang, ongkir, totalBayar;
    RadioGroup daftarKurir, daftarMetode;
    String norek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        plus = findViewById(R.id.increment);
        minus = findViewById(R.id.decrement);
        pay = findViewById(R.id.pay_btn);

        alamat = findViewById(R.id.alamat);
        namaBarang = findViewById(R.id.nama_barang);
        hargaBarang = findViewById(R.id.harga_barang);
        jumlah = findViewById(R.id.jumlah);
        totalHargaBarang = findViewById(R.id.harga_total);
        ongkir = findViewById(R.id.harga_ongkir);
        totalBayar = findViewById(R.id.harga_bayar);

        daftarKurir = findViewById(R.id.kurir);
        daftarMetode = findViewById(R.id.metode);

        alamat.setText(getIntent().getStringExtra("alamat"));

        ongkir.setText("Rp8.000");
        daftarKurir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.jne:
                        ongkir.setText("Rp8.000");
                        break;
                    case R.id.jnt:
                        ongkir.setText("Rp12.000");
                        break;
                    case R.id.tiki:
                        ongkir.setText("Rp10.000");
                        break;
                }
                calculate();
            }
        });

        daftarMetode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bca:
                        norek = "13865017654108461";
                        break;
                    case R.id.bri:
                        norek = "65801465015850";
                        break;
                    case R.id.bni:
                        norek = "10831086515168176";
                        break;
                    case R.id.mandiri:
                        norek = "21651913581511369";
                        break;
                }
                pay.setEnabled(true);
            }
        });

        namaBarang.setText(getIntent().getStringExtra("nama"));
        hargaBarang.setText("Rp" + getIntent().getStringExtra("harga"));

        calculate();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
                calculate();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
                calculate();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InvoiceActivity.class);
                intent.putExtra("norek", norek);
                intent.putExtra("harga", totalBayar.getText().toString());
                startActivity(intent);
            }
        });
    }

    void increment() {
        int i = Integer.parseInt(jumlah.getText().toString());
        jumlah.setText(String.valueOf(++i));
        minus.setEnabled(true);
    }

    void decrement() {
        int i = Integer.parseInt(jumlah.getText().toString());
        --i;
        if (i == 0) minus.setEnabled(false);
        else jumlah.setText(String.valueOf(i));
    }

    void calculate() {
        String hargaBarangTotal = String.valueOf(Integer.parseInt(getIntent().getStringExtra("harga").replace(".", "")) * Integer.parseInt(jumlah.getText().toString()));
        String hargaBayar = String.valueOf(Integer.parseInt(hargaBarangTotal) + Integer.parseInt(ongkir.getText().toString().replace("Rp", "").replace(".", "")));

        totalHargaBarang.setText("Rp" + new StringBuilder(hargaBarangTotal).insert(hargaBarangTotal.length() - 3, "."));
        totalBayar.setText("Rp" + new StringBuilder(hargaBayar).insert(hargaBayar.length() - 3, "."));
    }
}
