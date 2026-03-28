package model;

public class Season {
    private int numero;
    private Club clube;
    private PlayerRole role;
    private int jogos;
    private int gols;
    private int assistencias;
    private int idade;
    private String eventoDestaque;

    public Season(int numero, Club clube, PlayerRole role,
                  int jogos, int gols, int assistencias, int idade) {
        this.numero       = numero;
        this.clube        = clube;
        this.role         = role;
        this.jogos        = jogos;
        this.gols         = gols;
        this.assistencias = assistencias;
        this.idade        = idade;
    }

    public int getNumero()            { return numero; }
    public Club getClube()            { return clube; }
    public PlayerRole getRole()       { return role; }
    public int getJogos()             { return jogos; }
    public int getGols()              { return gols; }
    public int getAssistencias()      { return assistencias; }
    public int getIdade()             { return idade; }
    public String getEventoDestaque() { return eventoDestaque; }

    public void setEventoDestaque(String e) { this.eventoDestaque = e; }

    @Override
    public String toString() {
        String base = String.format(
            "T%d | %d anos | %s | %s | %dj %dg %da",
            numero, idade, clube.getNome(), role, jogos, gols, assistencias
        );
        if (eventoDestaque != null) base += " | ★ " + eventoDestaque;
        return base;
    }
}
