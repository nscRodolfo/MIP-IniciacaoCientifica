package com.example.manejointeligentedepragas.model;

public class AplicacaoModel {
    private int Cod_Aplicacao;
    private String Data;
    private String Estacao;
    private String Temperatura;
    private String Tempo;
    private int fk_Cod_MetodoControle;
    private int fk_Cod_Cultura;

    public AplicacaoModel(int cod_Aplicacao, String data, String estacao, String temperatura, String tempo, int fk_Cod_MetodoControle, int fk_Cod_Cultura) {
        this.Cod_Aplicacao = cod_Aplicacao;
        this.Data = data;
        this.Estacao = estacao;
        this.Temperatura = temperatura;
        this.Tempo = tempo;
        this.fk_Cod_MetodoControle = fk_Cod_MetodoControle;
        this.fk_Cod_Cultura = fk_Cod_Cultura;
    }

    public int getCod_Aplicacao() {
        return Cod_Aplicacao;
    }

    public void setCod_Aplicacao(int cod_Aplicacao) {
        this.Cod_Aplicacao = cod_Aplicacao;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        this.Data = data;
    }

    public String getEstacao() {
        return Estacao;
    }

    public void setEstacao(String estacao) {
        this.Estacao = estacao;
    }

    public String getTemperatura() {
        return Temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.Temperatura = temperatura;
    }

    public String getTempo() {
        return Tempo;
    }

    public void setTempo(String tempo) {
        this.Tempo = tempo;
    }

    public int getFk_Cod_MetodoControle() {
        return fk_Cod_MetodoControle;
    }

    public void setFk_Cod_MetodoControle(int fk_Cod_MetodoControle) {
        this.fk_Cod_MetodoControle = fk_Cod_MetodoControle;
    }

    public int getFk_Cod_Cultura() {
        return fk_Cod_Cultura;
    }

    public void setFk_Cod_Cultura(int fk_Cod_Cultura) {
        this.fk_Cod_Cultura = fk_Cod_Cultura;
    }
}
