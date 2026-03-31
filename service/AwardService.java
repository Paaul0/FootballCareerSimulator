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
        List<String> premios  = new ArrayList<>();
        int golsReais  = jogador.getGolsReaisTemporada(); // gols reais, sem inflação de eventos
        int idade      = jogador.getIdade();
        int temporada  = stats.getTotalTemporadas();
        int nivelClube = jogador.getClubeAtual().getNivel();
        int titulos    = stats.getTotalTitulos();

        // Artilheiro — usa gols reais, mínimo 20
        if (golsReais >= 20) {
            int chance = golsReais >= 35 ? 80 : golsReais >= 28 ? 55 : 30;
            if (r.nextInt(100) < chance)
                premios.add("🥾 Artilheiro da Temporada (T" + temporada + " — " + golsReais + " gols)");
        }

        // Melhor jogador — score alto + clube decente + pelo menos 1 título
        if (score >= 140 && nivelClube >= 3 && titulos >= 1
            && (role == PlayerRole.TITULAR || role == PlayerRole.DESTAQUE)) {
            int chance = score >= 170 ? 50 : 25;
            if (r.nextInt(100) < chance)
                premios.add("🏅 Melhor Jogador da Temporada (T" + temporada + ")");
        }

        // Melhor jovem sub-23
        if (idade <= 23 && score >= 80 && role != PlayerRole.JOVEM_PROMESSA) {
            int chance = score >= 110 ? 65 : 35;
            if (r.nextInt(100) < chance)
                premios.add("⭐ Melhor Jovem da Temporada (T" + temporada + " — " + idade + " anos)");
        }

        // Bola de Ouro
        if (score >= 180 && nivelClube >= 4 && titulos >= 2) {
            int bolaDeOuroGanhas = stats.getBolaDeOuro();
            int chance = bolaDeOuroGanhas == 0 ? 20 : 10;
            if (r.nextInt(100) < chance)
                premios.add("🌟 Bola de Ouro (T" + temporada + ")");
        }

        return premios;
    }
}