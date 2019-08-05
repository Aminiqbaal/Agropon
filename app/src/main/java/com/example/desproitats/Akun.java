package com.example.desproitats;

public class Akun {
    String nama, email, password, alamat;

    public Akun(String nama, String email, String password, String alamat) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAlamat() {
        return alamat;
    }
}
