package service;

import model.Player;

public class PerformanceService {
    public static int calcularScore(Player jogador) {
        return (jogador.getGolsUltimaFase() * 4)
             + (jogador.getAssistenciasUltimaFase() * 3)
             + (int)(jogador.getJogosUltimaFase() * 0.5);
    }
}
