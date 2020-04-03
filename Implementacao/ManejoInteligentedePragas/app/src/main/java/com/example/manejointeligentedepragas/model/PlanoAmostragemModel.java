package com.example.manejointeligentedepragas.model;

public class PlanoAmostragemModel {
    private int Cod_Plano;
    private String date;
    private int PlantasInfestadas;
    private int PlantasAmostradas;
    private int fk_Cod_Talhao;
    private int fk_Cod_Praga;

    public PlanoAmostragemModel(int cod_Plano, String date, int plantasInfestadas, int plantasAmostradas, int fk_Cod_Talhao, int fk_Cod_Praga) {
        this.Cod_Plano = cod_Plano;
        this.date = date;
        this.PlantasInfestadas = plantasInfestadas;
        this.PlantasAmostradas = plantasAmostradas;
        this.fk_Cod_Talhao = fk_Cod_Talhao;
        this.fk_Cod_Praga = fk_Cod_Praga;
    }

    public PlanoAmostragemModel(String date, int plantasInfestadas, int plantasAmostradas, int fk_Cod_Talhao, int fk_Cod_Praga) {
        this.date = date;
        this.PlantasInfestadas = plantasInfestadas;
        this.PlantasAmostradas = plantasAmostradas;
        this.fk_Cod_Talhao = fk_Cod_Talhao;
        this.fk_Cod_Praga = fk_Cod_Praga;
    }

    public PlanoAmostragemModel() {
        this.Cod_Plano = 0;
        this.date = null;
        this.PlantasInfestadas = 0;
        this.PlantasAmostradas = 0;
        this.fk_Cod_Talhao = 0;
        this.fk_Cod_Praga = 0;
    }

    public int getCod_Plano() {
        return Cod_Plano;
    }

    public void setCod_Plano(int cod_Plano) {
        this.Cod_Plano = cod_Plano;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPlantasInfestadas() {
        return PlantasInfestadas;
    }

    public void setPlantasInfestadas(int plantasInfestadas) {
        this.PlantasInfestadas = plantasInfestadas;
    }

    public int getPlantasAmostradas() {
        return PlantasAmostradas;
    }

    public void setPlantasAmostradas(int plantasAmostradas) {
        this.PlantasAmostradas = plantasAmostradas;
    }

    public int getFk_Cod_Talhao() {
        return fk_Cod_Talhao;
    }

    public void setFk_Cod_Talhao(int fk_Cod_Talhao) {
        this.fk_Cod_Talhao = fk_Cod_Talhao;
    }

    public int getFk_Cod_Praga() {
        return fk_Cod_Praga;
    }

    public void setFk_Cod_Praga(int fk_Cod_Praga) {
        this.fk_Cod_Praga = fk_Cod_Praga;
    }
}
