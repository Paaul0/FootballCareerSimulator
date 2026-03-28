package model;

public class Club {
    private String nome;
    private Country pais;
    private int nivel;

    public Club(String nome, Country pais, int nivel) {
        this.nome = nome;
        this.pais = pais;
        this.nivel = nivel;
    }

    public String getNome()    { return nome; }
    public Country getPais()   { return pais; }
    public int getNivel()      { return nivel; }

    @Override
    public String toString() {
        return nome + " (" + pais + ", nível " + nivel + ")";
    }
}
