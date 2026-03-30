package ui;

import model.CareerEvent;
import model.PlayerRole;
import model.Season;
import service.ChampionshipService.ResultadoCampeonato;

import java.util.List;

public class SeasonUI {

    public static void exibirResultadoTemporada(Season temporada, List<CareerEvent> eventos) {
        System.out.println("\n══════════ TEMPORADA " + temporada.getNumero() + " ══════════");
        System.out.println("Clube   : " + temporada.getClube().getNome());
        System.out.println("Role    : " + temporada.getRole());
        System.out.println("Idade   : " + temporada.getIdade() + " anos");
        System.out.println("──────────────────────────────");
        System.out.println("Jogos   : " + temporada.getJogos());
        System.out.println("Gols    : " + temporada.getGols());
        System.out.println("Assist. : " + temporada.getAssistencias());

        if (!eventos.isEmpty()) {
            System.out.println("\n★ EVENTOS DA TEMPORADA:");
            for (CareerEvent e : eventos) {
                System.out.println("  → " + e.getTitulo());
                System.out.println("    " + e.getDescricao());
            }
        }
        System.out.println("══════════════════════════════");
    }

    public static void exibirCampeonatos(List<ResultadoCampeonato> resultados) {
        if (resultados.isEmpty()) return;

        System.out.println("\n🏟️  CAMPEONATOS DA TEMPORADA:");
        for (ResultadoCampeonato rc : resultados) {
            String icone = switch (rc.resultado()) {
                case "Campeão"      -> "🏆";
                case "Vice-campeão" -> "🥈";
                case "Semifinal"    -> "🔹";
                default             -> "❌";
            };
            System.out.println("  " + icone + " " + rc.nomeCampeonato() + ": " + rc.resultado());
        }
    }

    public static void exibirPremios(List<String> premios) {
        if (premios.isEmpty()) return;
        System.out.println("\n🎖️  PRÊMIOS INDIVIDUAIS:");
        for (String p : premios)
            System.out.println("  " + p);
    }

    public static void exibirMensagemRole(PlayerRole role) {
        String msg = switch (role) {
            case JOVEM_PROMESSA -> "Você ainda está se desenvolvendo como jovem promessa.";
            case ROTACAO        -> "Você é peça de rotação no elenco.";
            case TITULAR        -> "Você é titular absoluto!";
            case DESTAQUE       -> "Você é o grande destaque do clube!";
        };
        System.out.println("\n" + msg);
    }
}