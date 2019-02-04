package com.example.firstproject_eat.datamodels;

public class Restaurant {

    private String nome;
    private String indirizzo;
    private float minOrdine;
    private String anteprima;

    public Restaurant(String nome, String indirizzo, float minOrdine, String anteprima){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.minOrdine = minOrdine;
        this.anteprima = anteprima;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public float getMinOrdine() {
        return minOrdine;
    }

    public void setMinOrdine(float minOrdine) {
        this.minOrdine = minOrdine;
    }

    public String getAnteprima() {
        return anteprima;
    }

    public void setAnteprima(String anteprima) {
        this.anteprima = anteprima;
    }
}
