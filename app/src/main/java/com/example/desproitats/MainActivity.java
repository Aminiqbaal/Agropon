package com.example.desproitats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout1);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout1, new Budidaya()).commit();
        bottomNavigationView.setSelectedItemId(R.id.budidaya);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.hasil_budidaya)
                    replaceFragment(new Hasil_Budidaya());
                else if (menuItem.getItemId() == R.id.hasil_olahan)
                    replaceFragment(new Hasil_Olahan());
                else if (menuItem.getItemId() == R.id.budidaya)
                    replaceFragment(new Budidaya());
                else if (menuItem.getItemId() == R.id.pengolahan)
                    replaceFragment(new Pengolahan());
                else if (menuItem.getItemId() == R.id.user)
                    replaceFragment(new Login_Frag());
                return true;
            }
        });

    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout1, fragment).commit();
    }
}
