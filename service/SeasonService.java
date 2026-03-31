package service;

import model.*;

public class SeasonService {

    public static Season simularTemporada(Player jogador, int numeroTemporada, PlayerRole role) {
        int idade = jogador.getIdade();

        int jogos        = ProfessionalPhaseService.calcularJogos(role);
        int gols         = ProfessionalPhaseService.calcularGols(jogos, jogador.getPosition());
        int assistencias = ProfessionalPhaseService.calcularAssistencias(jogos, jogador.getPosition());

        double fatorIdade = calcularFatorIdade(idade);
        gols         = (int)(gols * fatorIdade);
        assistencias = (int)(assistencias * fatorIdade);

        jogador.setJogosUltimaFase(jogos);
        jogador.setGolsUltimaFase(gols);
        jogador.setAssistenciasUltimaFase(assistencias);

        // Guarda os gols reais ANTES dos eventos inflarem
        jogador.setGolsReaisTemporada(gols);

        return new Season(numeroTemporada, jogador.getClubeAtual(), role,
                jogos, gols, assistencias, idade);
    }

    private static double calcularFatorIdade(int idade) {
        if (idade <= 20) return 0.75 + (idade - 17) * 0.05;
        if (idade <= 23) return 0.90 + (idade - 20) * 0.03;
        if (idade <= 29) return 1.0;
        if (idade <= 32) return 1.0 - (idade - 29) * 0.04;
        if (idade <= 36) return 0.88 - (idade - 32) * 0.05;
        return 0.60;
    }

    public static PlayerRole calcularNovoRole(PlayerRole roleAtual, int score) {
        if (score >= 150 && roleAtual != PlayerRole.DESTAQUE)       return PlayerRole.DESTAQUE;
        if (score >= 100 && roleAtual == PlayerRole.ROTACAO)        return PlayerRole.TITULAR;
        if (score >= 60  && roleAtual == PlayerRole.JOVEM_PROMESSA) return PlayerRole.ROTACAO;
        if (score < 30   && roleAtual == PlayerRole.TITULAR)        return PlayerRole.ROTACAO;
        if (score < 20   && roleAtual == PlayerRole.ROTACAO)        return PlayerRole.JOVEM_PROMESSA;
        return roleAtual;
    }
}