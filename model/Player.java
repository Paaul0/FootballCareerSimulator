package model;

public class Player {
    private String nome;
    private Country pais;
    private Club clubeBase;
    private BaseCareer baseCareer;
    private Position position;
    private Club clubeAtual;
    private int idade;
    private int jogosUltimaFase;
    private int golsUltimaFase;
    private int assistenciasUltimaFase;

    public Player(String nome, Country pais, Club clubeBase, Position position) {
        this.nome = nome;
        this.pais = pais;
        this.clubeBase = clubeBase;
        this.position = position;
        this.baseCareer = new BaseCareer();
        this.clubeAtual = clubeBase;
        this.idade = 17;
    }

    public String getNome()         { return nome; }
    public Country getPais()        { return pais; }
    public Club getClubeBase()      { return clubeBase; }
    public BaseCareer getBaseCareer() { return baseCareer; }
    public Position getPosition()   { return position; }
    public Club getClubeAtual()     { return clubeAtual; }
    public int getIdade()           { return idade; }
    public int getJogosUltimaFase() { return jogosUltimaFase; }
    public int getGolsUltimaFase()  { return golsUltimaFase; }
    public int getAssistenciasUltimaFase() { return assistenciasUltimaFase; }

    public void setClubeAtual(Club c)          { this.clubeAtual = c; }
    public void setIdade(int i)                { this.idade = i; }
    public void setJogosUltimaFase(int v)      { this.jogosUltimaFase = v; }
    public void setGolsUltimaFase(int v)       { this.golsUltimaFase = v; }
    public void setAssistenciasUltimaFase(int v) { this.assistenciasUltimaFase = v; }
}
