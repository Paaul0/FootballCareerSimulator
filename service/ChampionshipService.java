package service;

import model.Club;
import model.Country;
import model.Player;
import model.PlayerRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChampionshipService {

    private static final Random r = new Random();

    public record ResultadoCampeonato(
            String nomeCampeonato,
            String resultado,      // "Campeão", "Vice", "Semifinal", "Eliminado"
            boolean titulo
    ) {}

    // Decide quais campeonatos o clube disputa baseado no país e nível
    public static List<ResultadoCampeonato> simularCampeonatos(Player jogador, PlayerRole role) {
        List<ResultadoCampeonato> resultados = new ArrayList<>();
        Club clube = jogador.getClubeAtual();
        int nivel  = clube.getNivel();

        // Campeonato nacional (todos disputam)
        resultados.add(simularCampeonatoNacional(clube, role));

        // Copa nacional (todos disputam, mata-mata)
        if (r.nextInt(100) < 70) // 70% chance de ir longe o suficiente para importar
            resultados.add(simularCopaNacional(clube, role));

        // Libertadores / Champions — depende do país e nível
        if (clube.getPais().getContinent().name().equals("AMERICA_DO_SUL") && nivel >= 3)
            resultados.add(simularLibertadores(clube, role));

        if (clube.getPais().getContinent().name().equals("EUROPA") && nivel >= 3)
            resultados.add(simularChampions(clube, role));

        return resultados;
    }

    private static ResultadoCampeonato simularCampeonatoNacional(Club clube, PlayerRole role) {
        String nome = nomeCampeonatoNacional(clube.getCountry());
        int nivel   = clube.getNivel();

        // Fase de grupos simulada: pontuação do clube na fase de grupos
        int pontosClube     = simularFaseDeGrupos(nivel, role);
        int pontosAdversario = simularFaseDeGrupos(nivel, role);

        if (pontosClube < pontosAdversario - 10) {
            return new ResultadoCampeonato(nome, "Eliminado na fase de grupos", false);
        }

        // Mata-mata
        String resultado = simularMataMataNacional(nivel, role);
        boolean titulo   = resultado.equals("Campeão");
        return new ResultadoCampeonato(nome, resultado, titulo);
    }

    private static ResultadoCampeonato simularCopaNacional(Club clube, PlayerRole role) {
        String nome = nomeCopaNacional(clube.getCountry());
        int nivel   = clube.getNivel();
        String resultado = simularMataMataNacional(nivel, role);
        boolean titulo   = resultado.equals("Campeão");
        return new ResultadoCampeonato(nome, resultado, titulo);
    }

    private static ResultadoCampeonato simularLibertadores(Club clube, PlayerRole role) {
        int nivel = clube.getNivel();

        // Fase de grupos
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 8) {
            return new ResultadoCampeonato("Libertadores", "Eliminado na fase de grupos", false);
        }

        // Mata-mata continental (mais difícil que nacional)
        String resultado = simularMataMataContinental(nivel, role);
        boolean titulo   = resultado.equals("Campeão");
        return new ResultadoCampeonato("Libertadores", resultado, titulo);
    }

    private static ResultadoCampeonato simularChampions(Club clube, PlayerRole role) {
        int nivel = clube.getNivel();

        // Fase de grupos
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 9) {
            return new ResultadoCampeonato("Champions League", "Eliminado na fase de grupos", false);
        }

        String resultado = simularMataMataContinental(nivel, role);
        boolean titulo   = resultado.equals("Campeão");
        return new ResultadoCampeonato("Champions League", resultado, titulo);
    }

    // Simula pontuação na fase de grupos (0-18 pontos em 6 jogos)
    private static int simularFaseDeGrupos(int nivelClube, PlayerRole role) {
        int base = switch (nivelClube) {
            case 5 -> 12;
            case 4 -> 9;
            case 3 -> 7;
            default -> 5;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 3;
            case TITULAR  -> 1;
            default       -> 0;
        };
        return base + bonus + r.nextInt(5) - 2; // variação de -2 a +2
    }

    // Simula mata-mata nacional (oitavas → quartas → semi → final)
    private static String simularMataMataNacional(int nivel, PlayerRole role) {
        int chanceVencer = chanceVencer(nivel, role);

        // Oitavas
        if (r.nextInt(100) >= chanceVencer + 15) return "Oitavas de final";
        // Quartas
        if (r.nextInt(100) >= chanceVencer + 10) return "Quartas de final";
        // Semi
        if (r.nextInt(100) >= chanceVencer + 5)  return "Semifinal";
        // Final
        if (r.nextInt(100) >= chanceVencer)       return "Vice-campeão";
        return "Campeão";
    }

    // Mata-mata continental (mais difícil)
    private static String simularMataMataContinental(int nivel, PlayerRole role) {
        int chanceVencer = chanceVencer(nivel, role) - 10; // penalidade continental

        if (r.nextInt(100) >= chanceVencer + 15) return "Oitavas de final";
        if (r.nextInt(100) >= chanceVencer + 10) return "Quartas de final";
        if (r.nextInt(100) >= chanceVencer + 5)  return "Semifinal";
        if (r.nextInt(100) >= chanceVencer)       return "Vice-campeão";
        return "Campeão";
    }

    private static int chanceVencer(int nivel, PlayerRole role) {
        int base = switch (nivel) {
            case 5 -> 65;
            case 4 -> 50;
            case 3 -> 38;
            default -> 25;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 10;
            case TITULAR  -> 5;
            default       -> 0;
        };
        return base + bonus;
    }

    private static String nomeCampeonatoNacional(Country pais) {
        return switch (pais) {
            case BRASIL    -> "Brasileirão";
            case ARGENTINA -> "Liga Profesional";
            case PORTUGAL  -> "Primeira Liga";
            case ESPANHA   -> "La Liga";
            case ITALIA    -> "Serie A";
            case URUGUAI   -> "Primeira División";
            case MEXICO    -> "Liga MX";
            case EUA       -> "MLS";
        };
    }

    private static String nomeCopaNacional(Country pais) {
        return switch (pais) {
            case BRASIL    -> "Copa do Brasil";
            case ARGENTINA -> "Copa Argentina";
            case PORTUGAL  -> "Taça de Portugal";
            case ESPANHA   -> "Copa del Rey";
            case ITALIA    -> "Coppa Italia";
            case URUGUAI   -> "Copa Uruguay";
            case MEXICO    -> "Copa MX";
            case EUA       -> "US Open Cup";
        };
    }
}