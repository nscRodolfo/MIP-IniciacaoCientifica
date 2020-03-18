package com.example.manejointeligentedepragas.model;

public class TalhaoModel {
    private int Cod_Talhao;
    private String NumeroDePlantas;
    private int  fk_Cod_Cultura;
    private int fk_Cod_Planta;

    public TalhaoModel(int cod_Talhao, String numeroDePlantas, int fk_Cod_Cultura, int fk_Cod_Planta) {
        this.Cod_Talhao = cod_Talhao;
        this.NumeroDePlantas = numeroDePlantas;
        this.fk_Cod_Cultura = fk_Cod_Cultura;
        this.fk_Cod_Planta = fk_Cod_Planta;
    }

    public int getCod_Talhao() {
        return Cod_Talhao;
    }

    public void setCod_Talhao(int cod_Talhao) {
        this.Cod_Talhao = cod_Talhao;
    }

    public String getNumeroDePlantas() {
        return NumeroDePlantas;
    }

    public void setNumeroDePlantas(String numeroDePlantas) {
        this.NumeroDePlantas = numeroDePlantas;
    }

    public int getFk_Cod_Cultura() {
        return fk_Cod_Cultura;
    }

    public void setFk_Cod_Cultura(int fk_Cod_Cultura) {
        this.fk_Cod_Cultura = fk_Cod_Cultura;
    }

    public int getFk_Cod_Planta() {
        return fk_Cod_Planta;
    }

    public void setFk_Cod_Planta(int fk_Cod_Planta) {
        this.fk_Cod_Planta = fk_Cod_Planta;
    }
}
