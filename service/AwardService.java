package service;

import model.CareerStats;
import model.Player;
import model.PlayerRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AwardService {

    private static final Random r = new Random();

    public static List<String> calcularPremios(Player jogador, PlayerRole role,
                                               CareerStats stats, int score) {
        List<String> premios = new ArrayList<>();
        int gols      = jogador.getGolsUltimaFase();
        int jogos     = jogador.getJogosUltimaFase();
        int idade     = jogador.getIdade();
        int temporada = stats.getTotalTemporadas();

        // Artilheiro da temporada — precisa de score alto e muitos gols
        if (gols >= 20 && score >= 100) {
            int chance = gols >= 30 ? 70 : 40;
            if (r.nextInt(100) < chance) {
                premios.add("🥾 Artilheiro da Temporada (T" + temporada + " — " + gols + " gols)");
            }
        }

        // Melhor jogador da temporada — score muito alto
        if (score >= 130 && (role == PlayerRole.TITULAR || role == PlayerRole.DESTAQUE)) {
            int chance = score >= 160 ? 60 : 30;
            if (r.nextInt(100) < chance) {
                premios.add("🏅 Melhor Jogador da Temporada (T" + temporada + ")");
            }
        }

        // Melhor jovem sub-23
        if (idade <= 23 && score >= 80 && role != PlayerRole.JOVEM_PROMESSA) {
            int chance = score >= 110 ? 65 : 35;
            if (r.nextInt(100) < chance) {
                premios.add("⭐ Melhor Jovem da Temporada (T" + temporada + " — " + idade + " anos)");
            }
        }

        // Bola de Ouro — muito difícil, exige score altíssimo e clube de elite
        if (score >= 170 && jogador.getClubeAtual().getNivel() >= 4) {
            int bolaDeOuroGanhas = stats.getBolaDeOuro();
            int chance = bolaDeOuroGanhas == 0 ? 25 : 15; // mais difícil ganhar de novo
            if (r.nextInt(100) < chance) {
                premios.add("🌟 Bola de Ouro (T" + temporada + ")");
            }
        }

        return premios;
    }
}