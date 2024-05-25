package com.example.dai_tub;

public class User {
    private String userId;
    private String name;
    private String email;
    private String nif;
    private String numeroPasse;
    private double saldo;
    private int viagensCompradas; // Novo campo para armazenar o número de viagens compradas

    public User() {
        // Construtor vazio necessário para Firebase
    }

    public User(String userId, String name, String email, String nif, String numeroPasse, double saldo, int viagensCompradas) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.numeroPasse = numeroPasse;
        this.saldo = saldo;
        this.viagensCompradas = viagensCompradas;
    }

    // Métodos getters e setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getViagensCompradas() {
        return viagensCompradas;
    }

    public void setViagensCompradas(int viagensCompradas) {
        this.viagensCompradas = viagensCompradas;
    }
}
