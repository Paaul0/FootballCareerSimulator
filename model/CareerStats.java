package model;

import java.util.ArrayList;
import java.util.List;

public class CareerStats {
    private int totalJogos;
    private int totalGols;
    private int totalAssistencias;
    private int totalTitulos;
    private int totalTemporadas;
    private List<Season> historico       = new ArrayList<>();
    private List<String> titulos         = new ArrayList<>();
    private List<String> premiosIndividuais = new ArrayList<>();
    private int bolasDeOuro = 0;

    public void registrarTemporada(Season s) {
        historico.add(s);
        totalJogos        += s.getJogos();
        totalGols         += s.getGols();
        totalAssistencias += s.getAssistencias();
        totalTemporadas++;
    }

    public void registrarTitulo(String titulo) {
        titulos.add(titulo);
        totalTitulos++;
    }

    public void registrarPremioIndividual(String premio) {
        premiosIndividuais.add(premio);
        if (premio.contains("Bola de Ouro")) bolasDeOuro++;
    }

    public int getTotalJogos()                    { return totalJogos; }
    public int getTotalGols()                     { return totalGols; }
    public int getTotalAssistencias()             { return totalAssistencias; }
    public int getTotalTitulos()                  { return totalTitulos; }
    public int getTotalTemporadas()               { return totalTemporadas; }
    public List<Season> getHistorico()            { return historico; }
    public List<String> getTitulos()              { return titulos; }
    public List<String> getPremiosIndividuais()   { return premiosIndividuais; }
    public int getBolaDeOuro()                    { return bolasDeOuro; }

    public int calcularPontuacaoFinal() {
        return (totalGols * 4)
               + (totalAssistencias * 3)
               + (totalTitulos * 50)
               + (bolasDeOuro * 100)
               + (premiosIndividuais.size() * 20)
               + (int)(totalJogos * 0.5);
    }

    public String getRating() {
        int p = calcularPontuacaoFinal();
        if (p >= 2000) return "Lenda do Futebol";
        if (p >= 1200) return "Craque Mundial";
        if (p >= 700)  return "Estrela";
        if (p >= 350)  return "Profissional Sólido";
        return "Carreira Discreta";
    }
}