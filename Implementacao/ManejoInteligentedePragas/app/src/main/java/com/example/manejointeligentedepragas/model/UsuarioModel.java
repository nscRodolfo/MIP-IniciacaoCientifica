package com.example.manejointeligentedepragas.model;

public class UsuarioModel {
    private int Cod_Usuario;
    private String Nome;
    private String Telefone;
    private String Email;
    private String Senha;

    public UsuarioModel(int cod_Usuario, String nome, String telefone, String email, String senha) {
        this.Cod_Usuario = cod_Usuario;
        this.Nome = nome;
        this.Telefone = telefone;
        this.Email = email;
        this.Senha = senha;
    }
    public UsuarioModel() {
        this.Cod_Usuario = 0;
        this.Nome = null;
        this.Telefone = null;
        this.Email = null;
        this.Senha = null;
    }

    public int getCod_Usuario() {
        return Cod_Usuario;
    }

    public void setCod_Usuario(int cod_Uuario) {
        this.Cod_Usuario = cod_Uuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }
}
