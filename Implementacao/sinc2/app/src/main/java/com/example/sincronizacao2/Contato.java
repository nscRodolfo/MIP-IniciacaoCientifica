package com.example.sincronizacao2;

public class Contato {

    private String nome;
    private int syncStatus;

    Contato(String nome, int syncStatus){
        this.setNome(nome);
        this.setSyncStatus(syncStatus);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        nome = name;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }
}
