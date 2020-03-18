package com.example.manejointeligentedepragas.model;

public class AtingeModel {
    private int Cod_Atinge;
    private String NivelDanoEconomico;
    private String NivelDeControle;
    private String NivelDeEquilibrio;
    private String NumeroPlantasAmostradas;
    private String PontosPorTalhao;
    private String PlantasPorPonto;

    private int fk_Cod_Planta;
    private int fk_Cod_Praga;

    public AtingeModel(int cod_Atinge, String nivelDanoEconomico, String nivelDeControle, String nivelDeEquilibrio, String numeroPlantasAmostradas, String pontosPorTalhao, String plantasPorPonto, int fk_Cod_Planta, int fk_Cod_Praga) {
        this.Cod_Atinge = cod_Atinge;
        this.NivelDanoEconomico = nivelDanoEconomico;
        this.NivelDeControle = nivelDeControle;
        this.NivelDeEquilibrio = nivelDeEquilibrio;
        this. NumeroPlantasAmostradas = numeroPlantasAmostradas;
        this.PontosPorTalhao = pontosPorTalhao;
        this.PlantasPorPonto = plantasPorPonto;
        this.fk_Cod_Planta = fk_Cod_Planta;
        this.fk_Cod_Praga = fk_Cod_Praga;
    }

    public int getCod_Atinge() {
        return Cod_Atinge;
    }

    public void setCod_Atinge(int cod_Atinge) {
        this.Cod_Atinge = cod_Atinge;
    }

    public String getNivelDanoEconomico() {
        return NivelDanoEconomico;
    }

    public void setNivelDanoEconomico(String nivelDanoEconomico) {
        this.NivelDanoEconomico = nivelDanoEconomico;
    }

    public String getNivelDeControle() {
        return NivelDeControle;
    }

    public void setNivelDeControle(String nivelDeControle) {
        this.NivelDeControle = nivelDeControle;
    }

    public String getNivelDeEquilibrio() {
        return NivelDeEquilibrio;
    }

    public void setNivelDeEquilibrio(String nivelDeEquilibrio) {
        this.NivelDeEquilibrio = nivelDeEquilibrio;
    }

    public String getNumeroPlantasAmostradas() {
        return NumeroPlantasAmostradas;
    }

    public void setNumeroPlantasAmostradas(String numeroPlantasAmostradas) {
        this.NumeroPlantasAmostradas = numeroPlantasAmostradas;
    }

    public String getPontosPorTalhao() {
        return PontosPorTalhao;
    }

    public void setPontosPorTalhao(String pontosPorTalhao) {
        this.PontosPorTalhao = pontosPorTalhao;
    }

    public String getPlantasPorPonto() {
        return PlantasPorPonto;
    }

    public void setPlantasPorPonto(String plantasPorPonto) {
        this.PlantasPorPonto = plantasPorPonto;
    }

    public int getFk_Cod_Planta() {
        return fk_Cod_Planta;
    }

    public void setFk_Cod_Planta(int fk_Cod_Planta) {
        this.fk_Cod_Planta = fk_Cod_Planta;
    }

    public int getFk_Cod_Praga() {
        return fk_Cod_Praga;
    }

    public void setFk_Cod_Praga(int fk_Cod_Praga) {
        this.fk_Cod_Praga = fk_Cod_Praga;
    }
}
