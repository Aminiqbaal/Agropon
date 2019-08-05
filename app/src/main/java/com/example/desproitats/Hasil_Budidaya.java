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
public class Hasil_Budidaya extends Fragment {
    RecyclerView recyclerView;
    AdapterListKotaBudidaya adapter;

    public Hasil_Budidaya() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hasil__budidaya, container, false);
        recyclerView = view.findViewById(R.id.rv_hasilbudidaya);
        adapter = new AdapterListKotaBudidaya(getContext(),ListKota.datakota);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
