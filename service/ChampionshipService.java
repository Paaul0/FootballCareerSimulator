package service;

import model.Club;
import model.Country;
import model.Player;
import model.PlayerRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChampionshipService {

    private static final Random r = new Random();

    public record ResultadoCampeonato(
            String nomeCampeonato,
            String resultado,
            boolean titulo
    ) {}

    public static List<ResultadoCampeonato> simularCampeonatos(Player jogador, PlayerRole role) {
        List<ResultadoCampeonato> resultados = new ArrayList<>();
        Club clube = jogador.getClubeAtual();
        int nivel  = clube.getNivel();
        Country pais = clube.getCountry();

        // Campeonato nacional (todos disputam)
        resultados.add(simularCampeonatoNacional(clube, role));

        // Copa nacional (todos disputam)
        if (r.nextInt(100) < 70)
            resultados.add(simularCopaNacional(clube, role));

        // Libertadores — apenas clubes da América do Sul nível 3+
        if (pais.getContinent() == model.Continent.AMERICA_DO_SUL && nivel >= 3)
            resultados.add(simularLibertadores(clube, role));

        // Champions — apenas clubes da Europa nível 3+
        if (pais.getContinent() == model.Continent.EUROPA && nivel >= 3)
            resultados.add(simularChampions(clube, role));

        return resultados;
    }

    private static ResultadoCampeonato simularCampeonatoNacional(Club clube, PlayerRole role) {
        String nome = nomeCampeonatoNacional(clube.getCountry());
        int nivel   = clube.getNivel();

        // Brasileirão e ligas de pontos corridos — sem mata-mata, só campeão ou não
        if (isPontosCorridos(clube.getCountry())) {
            return simularPontosCorridos(nome, nivel, role);
        }

        // Outros países — fase de grupos + mata-mata
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < limiteEliminacao(nivel))
            return new ResultadoCampeonato(nome, "Eliminado na fase de grupos", false);

        String resultado = simularMataMataNacional(nivel, role);
        return new ResultadoCampeonato(nome, resultado, resultado.equals("Campeão"));
    }

    // Pontos corridos: só diz se foi campeão ou não, sem etapas intermediárias
    private static ResultadoCampeonato simularPontosCorridos(String nome, int nivel, PlayerRole role) {
        int chanceTitle = switch (nivel) {
            case 5 -> 45;
            case 4 -> 25;
            case 3 -> 10;
            case 2 -> 3;  // Guarani campeão é raríssimo
            default -> 1;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 10;
            case TITULAR  -> 5;
            default       -> 0;
        };

        if (r.nextInt(100) < chanceTitle + bonus)
            return new ResultadoCampeonato(nome, "Campeão", true);

        // Posição narrativa baseada no nível
        String posicao = switch (nivel) {
            case 5 -> randomEntre("Vice-campeão", "3º lugar", "Top 4");
            case 4 -> randomEntre("Top 4", "Top 6", "Meio da tabela");
            case 3 -> randomEntre("Top 6", "Meio da tabela", "Zona de rebaixamento");
            case 2 -> randomEntre("Meio da tabela", "Zona de rebaixamento", "Rebaixado");
            default -> "Meio da tabela";
        };

        boolean rebaixado = posicao.equals("Rebaixado");
        return new ResultadoCampeonato(nome, posicao, false);
    }

    private static ResultadoCampeonato simularCopaNacional(Club clube, PlayerRole role) {
        String nome = nomeCopaNacional(clube.getCountry());
        int nivel   = clube.getNivel();
        String resultado = simularMataMataNacional(nivel, role);
        return new ResultadoCampeonato(nome, resultado, resultado.equals("Campeão"));
    }

    private static ResultadoCampeonato simularLibertadores(Club clube, PlayerRole role) {
        int nivel  = clube.getNivel();
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 8)
            return new ResultadoCampeonato("Libertadores", "Eliminado na fase de grupos", false);
        String resultado = simularMataMataContinental(nivel, role);
        return new ResultadoCampeonato("Libertadores", resultado, resultado.equals("Campeão"));
    }

    private static ResultadoCampeonato simularChampions(Club clube, PlayerRole role) {
        int nivel  = clube.getNivel();
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 9)
            return new ResultadoCampeonato("Champions League", "Eliminado na fase de grupos", false);
        String resultado = simularMataMataContinental(nivel, role);
        return new ResultadoCampeonato("Champions League", resultado, resultado.equals("Campeão"));
    }

    private static int simularFaseDeGrupos(int nivelClube, PlayerRole role) {
        int base = switch (nivelClube) {
            case 5 -> 12;
            case 4 -> 9;
            case 3 -> 7;
            default -> 4;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 3;
            case TITULAR  -> 1;
            default       -> 0;
        };
        return base + bonus + r.nextInt(5) - 2;
    }

    private static int limiteEliminacao(int nivel) {
        return switch (nivel) {
            case 5 -> 6;
            case 4 -> 7;
            default -> 8;
        };
    }

    private static String simularMataMataNacional(int nivel, PlayerRole role) {
        int chance = chanceVencer(nivel, role);
        if (r.nextInt(100) >= chance + 15) return "Oitavas de final";
        if (r.nextInt(100) >= chance + 10) return "Quartas de final";
        if (r.nextInt(100) >= chance + 5)  return "Semifinal";
        if (r.nextInt(100) >= chance)      return "Vice-campeão";
        return "Campeão";
    }

    private static String simularMataMataContinental(int nivel, PlayerRole role) {
        int chance = chanceVencer(nivel, role) - 10;
        if (r.nextInt(100) >= chance + 15) return "Oitavas de final";
        if (r.nextInt(100) >= chance + 10) return "Quartas de final";
        if (r.nextInt(100) >= chance + 5)  return "Semifinal";
        if (r.nextInt(100) >= chance)      return "Vice-campeão";
        return "Campeão";
    }

    private static int chanceVencer(int nivel, PlayerRole role) {
        int base = switch (nivel) {
            case 5 -> 65;
            case 4 -> 48;
            case 3 -> 32;
            case 2 -> 12; // Guarani dificilmente chega na final
            default -> 5;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 10;
            case TITULAR  -> 5;
            default       -> 0;
        };
        return base + bonus;
    }

    // Ligas de pontos corridos (sem mata-mata)
    private static boolean isPontosCorridos(Country pais) {
        return switch (pais) {
            case BRASIL, ESPANHA, ITALIA, PORTUGAL, URUGUAI, MEXICO, EUA -> true;
            default -> false;
        };
    }

    private static String randomEntre(String a, String b, String c) {
        return switch (r.nextInt(3)) {
            case 0 -> a;
            case 1 -> b;
            default -> c;
        };
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