package com.example.desproitats;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText name, email, password, alamat;
    Button register;
    TextView login;
    private FirebaseAuth mAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        alamat = findViewById(R.id.input_alamat);
        register = findViewById(R.id.btn_signup);
        login = findViewById(R.id.link_login);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        register.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String _name = name.getText().toString();
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        String _alamat = alamat.getText().toString();

        mAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            register.setEnabled(true);
                            setResult(RESULT_OK, null);
                            finish();
                        } else {
                            onSignupFailed();
                        }
                    }
                });

        Akun akun = new Akun(_name, _email, _password, _alamat);
        database.child("akun").push().setValue(akun).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Register.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onSignupFailed() {
        Toast.makeText(Register.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
        register.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String _name = name.getText().toString();
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        String _alamat = alamat.getText().toString();

        if (_name.isEmpty() || _name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            email.setError("enter a valid inputEmail address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (_password.isEmpty() || _password.length() < 8) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (_alamat.isEmpty()) {
            alamat.setError("enter a valid address");
            valid = false;
        } else {
            alamat.setError(null);
        }

        return valid;
    }
}
