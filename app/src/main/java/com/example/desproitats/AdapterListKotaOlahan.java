package com.example.desproitats;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AdapterListKotaOlahan extends RecyclerView.Adapter<AdapterListKotaOlahan.ViewHolder> {
    String[][] data;
    Context context;

    public AdapterListKotaOlahan(Context context, String[][] data) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_kota, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(data[i][0]);
        Glide.with(context).load(data[i][1]).into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,detail_olahan.class);
                intent.putExtra("namakota",data[i][0]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_kota);
            textView = itemView.findViewById(R.id.namakota);
            imageView = itemView.findViewById(R.id.gambarkota);
        }
    }
}
