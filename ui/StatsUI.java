package ui;

import model.CareerStats;
import model.Player;
import model.Season;

import java.util.Map;

public class StatsUI {

    public static void exibirResumoCarreira(Player jogador, CareerStats stats) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         ESTATÍSTICAS DA CARREIRA     ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Jogador  : " + jogador.getNome());
        System.out.println("País     : " + jogador.getPais());
        System.out.println("Posição  : " + jogador.getPosition());
        System.out.println("Idade    : " + jogador.getIdade() + " anos");
        System.out.println("Clube    : " + jogador.getClubeAtual().getNome());

        System.out.println("\n──────────── TOTAIS DE CARREIRA ────────────");
        System.out.println("Temporadas : " + stats.getTotalTemporadas());
        System.out.println("Jogos      : " + stats.getTotalJogos());
        System.out.println("Gols       : " + stats.getTotalGols());
        System.out.println("Assist.    : " + stats.getTotalAssistencias());
        System.out.println("Títulos    : " + stats.getTotalTitulos());
        System.out.println("Prêmios    : " + stats.getPremiosIndividuais().size());

        System.out.println("\n──────────── STATS POR CLUBE ────────────");
        System.out.printf("  %-20s %6s %6s %6s %6s%n", "Clube", "Jogos", "Gols", "Ast.", "Títulos");
        System.out.println("  " + "─".repeat(50));
        for (Map.Entry<String, int[]> entry : stats.getStatsPorClube().entrySet()) {
            int[] s = entry.getValue();
            System.out.printf("  %-20s %6d %6d %6d %6d%n",
                    entry.getKey(), s[0], s[1], s[2], s[3]);
        }

        if (!stats.getTitulos().isEmpty()) {
            System.out.println("\n──────────── TÍTULOS ────────────");
            for (String t : stats.getTitulos())
                System.out.println("  🏆 " + t);
        }

        if (!stats.getPremiosIndividuais().isEmpty()) {
            System.out.println("\n──────────── PRÊMIOS INDIVIDUAIS ────────────");
            for (String p : stats.getPremiosIndividuais())
                System.out.println("  " + p);
        }

        System.out.println("\n──────────── HISTÓRICO ────────────");
        for (Season s : stats.getHistorico())
            System.out.println("  " + s);

        System.out.println("\n──────────── AVALIAÇÃO ────────────");
        System.out.println("Pontuação : " + stats.calcularPontuacaoFinal());
        System.out.println("Rating    : " + stats.getRating());
        System.out.println("══════════════════════════════════════");
    }

    public static void exibirTelaAposentadoria(Player jogador, CareerStats stats) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         FIM DE CARREIRA              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("\n" + jogador.getNome() + " pendurou as chuteiras aos "
                           + jogador.getIdade() + " anos.");
        exibirResumoCarreira(jogador, stats);
        System.out.println("\nObrigado por jogar! Até a próxima carreira.");
    }
}