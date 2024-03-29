package com.example.desproitats;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Hasil_Olahan extends Fragment {
    RecyclerView recyclerView;
    AdapterListKotaOlahan adapter;

    public Hasil_Olahan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hasil__olahan, container, false);
        recyclerView = view.findViewById(R.id.rv_hasilolahan);
        adapter = new AdapterListKotaOlahan(getContext(), ListKota.datakota);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
