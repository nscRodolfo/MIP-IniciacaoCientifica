package com.example.manejointeligentedepragas.model;

public class PresencaPragaModel {
    private int Cod_Propriedade;
    private int fk_Cod_Praga;
    private int fk_Cod_Cultura;

    public PresencaPragaModel(int cod_Propriedade, int fk_Cod_Praga, int fk_Cod_Cultura) {
        this.Cod_Propriedade = cod_Propriedade;
        this.fk_Cod_Praga = fk_Cod_Praga;
        this.fk_Cod_Cultura = fk_Cod_Cultura;
    }

    public int getCod_Propriedade() {
        return Cod_Propriedade;
    }

    public void setCod_Propriedade(int cod_Propriedade) {
        this.Cod_Propriedade = cod_Propriedade;
    }

    public int getFk_Cod_Praga() {
        return fk_Cod_Praga;
    }

    public void setFk_Cod_Praga(int fk_Cod_Praga) {
        this.fk_Cod_Praga = fk_Cod_Praga;
    }

    public int getFk_Cod_Cultura() {
        return fk_Cod_Cultura;
    }

    public void setFk_Cod_Cultura(int fk_Cod_Cultura) {
        this.fk_Cod_Cultura = fk_Cod_Cultura;
    }
}
