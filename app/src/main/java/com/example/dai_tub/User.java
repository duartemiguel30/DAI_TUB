package com.example.dai_tub;

public class User {
    private String name;
    private String email;
    private String nif;
    private String numeroPasse; // Alterado de passNumber para numeroPasse

    public User() {
        // Construtor vazio necessário para Firebase
    }

    public User(String name, String email, String nif, String numeroPasse) {
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.numeroPasse = numeroPasse;
    }

    // Métodos getters e setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumeroPasse() {
        return numeroPasse;
    }

    public void setNumeroPasse(String numeroPasse) {
        this.numeroPasse = numeroPasse;
    }
}
