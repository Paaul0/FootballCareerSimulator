package service;

import model.PlayerRole;
import model.Position;

import java.util.Random;

public class ProfessionalPhaseService {

    public static int calcularJogos(PlayerRole role) {
        Random r = new Random();
        return switch (role) {
            case JOVEM_PROMESSA -> 15 + r.nextInt(16); // 15–30
            case ROTACAO        -> 30 + r.nextInt(21); // 30–50
            case TITULAR        -> 45 + r.nextInt(21); // 45–65
            case DESTAQUE       -> 60 + r.nextInt(21); // 60–80
        };
    }

    public static int calcularGols(int jogos, Position position) {
        Random r = new Random();
        double baseGols  = 0.45;
        double fatorIdade = 0.85;
        double aleatorio = 0.85 + (r.nextDouble() * 0.3);
        double gols = jogos * baseGols * position.getFatorGols() * fatorIdade * aleatorio;
        return (int) gols;
    }

    public static int calcularAssistencias(int jogos, Position position) {
        Random r = new Random();
        double baseAssist = 0.30;
        double fatorIdade = 0.85;
        double aleatorio = 0.85 + (r.nextDouble() * 0.3);
        double assists = jogos * baseAssist * position.getFatorAssistencias() * fatorIdade * aleatorio;
        return (int) assists;
    }
}
