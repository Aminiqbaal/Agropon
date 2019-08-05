package com.example.desproitats;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Frag extends Fragment {
    EditText inputEmail, inputPassword;
    Button login, logout;
    TextView register, email, name, alamat;
    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mAuth;
    LinearLayout form, profile;
    DatabaseReference database;

    public Login_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_, container, false);
        inputEmail = view.findViewById(R.id.input_email);
        inputPassword = view.findViewById(R.id.input_password);
        login = view.findViewById(R.id.btn_login);
        logout = view.findViewById(R.id.btn_logout);
        register = view.findViewById(R.id.link_signup);
        form = view.findViewById(R.id.signin_form);
        profile = view.findViewById(R.id.profile);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        alamat = view.findViewById(R.id.alamat);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Register.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                updateUI(null);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void updateUI(final FirebaseUser user) {
        if (user != null) {
            form.setVisibility(View.GONE);
            profile.setVisibility(View.VISIBLE);
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.child("akun").getChildren()){
                        String _email = snapshot.child("email").getValue(String.class);
                        if(_email.equals(user.getEmail())){
                            name.setText(snapshot.child("nama").getValue(String.class));
                            email.setText(snapshot.child("email").getValue(String.class));
                            alamat.setText(snapshot.child("alamat").getValue(String.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            form.setVisibility(View.VISIBLE);
            profile.setVisibility(View.GONE);
        }
    }

    private void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String _email = inputEmail.getText().toString();
        String _password = inputPassword.getText().toString();

        if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (_password.isEmpty() || _password.length() < 8) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

}
