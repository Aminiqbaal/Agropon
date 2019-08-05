package com.example.desproitats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdapterDetailBudidaya extends RecyclerView.Adapter<AdapterDetailBudidaya.ViewHolder> {
    String[][] data;
    String alamat;
    Context context;
    private FirebaseAuth mAuth;
    DatabaseReference database;

    public AdapterDetailBudidaya(Context context, String[][] data) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hasil_budidaya, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.namabarang.setText(data[i][0]);
        viewHolder.namadesa.setText(data[i][1]);
        viewHolder.harga.setText(data[i][2]);
        Glide.with(context).load(data[i][3]).into(viewHolder.gambarbarang);
        viewHolder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() != null) {
                    retrieveData();
                    Intent intent = new Intent(context, PembelianActivity.class);
                    intent.putExtra("nama", data[i][0]);
                    intent.putExtra("harga", data[i][2].split("/")[0]);
                    intent.putExtra("alamat", alamat);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Login terlebih dahulu di menu Akun", Toast.LENGTH_SHORT).show();
                    new MainActivity().replaceFragment(new Login_Frag());
                    ((Activity) context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namabarang, namadesa, harga;
        ImageView gambarbarang;
        Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namabarang = itemView.findViewById(R.id.namabarang);
            namadesa = itemView.findViewById(R.id.namadesa);
            harga = itemView.findViewById(R.id.harga);
            gambarbarang = itemView.findViewById(R.id.gambarbarang);
            buy = itemView.findViewById(R.id.buy_budidaya_btn);
        }
    }

    protected void retrieveData() {
        final FirebaseUser user = mAuth.getCurrentUser();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("akun").getChildren()) {
                    String _email = snapshot.child("email").getValue(String.class);
                    if (_email.equals(user.getEmail()))
                        alamat = snapshot.child("alamat").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
