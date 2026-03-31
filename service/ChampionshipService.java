package service;

import model.Continent;
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
            boolean titulo,
            boolean rebaixado
    ) {}

    public static List<ResultadoCampeonato> simularCampeonatos(Player jogador, PlayerRole role) {
        List<ResultadoCampeonato> resultados = new ArrayList<>();
        Club clube   = jogador.getClubeAtual();
        int nivel    = clube.getNivel();
        Country pais = clube.getCountry();

        resultados.add(simularCampeonatoNacional(clube, role));

        if (r.nextInt(100) < 70)
            resultados.add(simularCopaNacional(clube, role));

        if (pais.getContinent() == Continent.AMERICA_DO_SUL && nivel >= 3)
            resultados.add(simularLibertadores(clube, role));

        if (pais.getContinent() == Continent.EUROPA && nivel >= 3)
            resultados.add(simularChampions(clube, role));

        return resultados;
    }

    private static ResultadoCampeonato simularCampeonatoNacional(Club clube, PlayerRole role) {
        String nome = nomeCampeonatoNacional(clube.getCountry());
        int nivel   = clube.getNivel();

        if (isPontosCorridos(clube.getCountry()))
            return simularPontosCorridos(nome, nivel, role);

        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < limiteEliminacao(nivel))
            return new ResultadoCampeonato(nome, "Eliminado na fase de grupos", false, false);

        String resultado = simularMataMataNacional(nivel, role);
        return new ResultadoCampeonato(nome, resultado, resultado.equals("Campeão"), false);
    }

    private static ResultadoCampeonato simularPontosCorridos(String nome, int nivel, PlayerRole role) {
        int chanceTitle = switch (nivel) {
            case 5 -> 45;
            case 4 -> 25;
            case 3 -> 10;
            case 2 -> 3;
            default -> 1;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 10;
            case TITULAR  -> 5;
            default       -> 0;
        };

        if (r.nextInt(100) < chanceTitle + bonus)
            return new ResultadoCampeonato(nome, "Campeão", true, false);

        int chanceRebaixamento = switch (nivel) {
            case 2 -> 25;
            case 3 -> 12;
            case 4 -> 4;
            case 5 -> 1;
            default -> 0;
        };

        if (r.nextInt(100) < chanceRebaixamento)
            return new ResultadoCampeonato(nome, "Rebaixado", false, true);

        String posicao = switch (nivel) {
            case 5 -> randomEntre("Vice-campeão", "3º lugar", "Top 4");
            case 4 -> randomEntre("Top 4", "Top 6", "Meio da tabela");
            case 3 -> randomEntre("Top 6", "Meio da tabela", "Zona de rebaixamento");
            case 2 -> randomEntre("Meio da tabela", "Zona de rebaixamento", "Meio da tabela");
            default -> "Meio da tabela";
        };

        return new ResultadoCampeonato(nome, posicao, false, false);
    }

    private static ResultadoCampeonato simularCopaNacional(Club clube, PlayerRole role) {
        String nome  = nomeCopaNacional(clube.getCountry());
        int nivel    = clube.getNivel();
        String resultado = simularMataMataNacional(nivel, role);
        return new ResultadoCampeonato(nome, resultado, resultado.equals("Campeão"), false);
    }

    private static ResultadoCampeonato simularLibertadores(Club clube, PlayerRole role) {
        int nivel  = clube.getNivel();
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 8)
            return new ResultadoCampeonato("Libertadores", "Eliminado na fase de grupos", false, false);
        String resultado = simularMataMataContinental(nivel, role);
        return new ResultadoCampeonato("Libertadores", resultado, resultado.equals("Campeão"), false);
    }

    private static ResultadoCampeonato simularChampions(Club clube, PlayerRole role) {
        int nivel  = clube.getNivel();
        int pontos = simularFaseDeGrupos(nivel, role);
        if (pontos < 9)
            return new ResultadoCampeonato("Champions League", "Eliminado na fase de grupos", false, false);
        String resultado = simularMataMataContinental(nivel, role);
        return new ResultadoCampeonato("Champions League", resultado, resultado.equals("Campeão"), false);
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
            case 2 -> 12;
            default -> 5;
        };
        int bonus = switch (role) {
            case DESTAQUE -> 10;
            case TITULAR  -> 5;
            default       -> 0;
        };
        return base + bonus;
    }

    private static boolean isPontosCorridos(Country pais) {
        return switch (pais) {
            case BRASIL, ESPANHA, ITALIA, PORTUGAL, URUGUAI,
                 MEXICO, EUA, FRANCA, ALEMANHA, INGLATERRA -> true;
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
            case FRANCA    -> "Ligue 1";
            case ALEMANHA  -> "Bundesliga";
            case INGLATERRA-> "Premier League";
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
            case FRANCA    -> "Coupe de France";
            case ALEMANHA  -> "DFB Pokal";
            case INGLATERRA-> "FA Cup";
        };
    }
}