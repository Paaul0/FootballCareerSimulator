package service;

import model.Club;
import model.Country;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class BaseClubFactory {

    private static final Random r = new Random();

    // Clubes de base (nível 1)
    private static final List<Club> CLUBES_BASE = List.of(
            new Club("Base Corinthians",   Country.BRASIL,    1),
            new Club("Base Flamengo",      Country.BRASIL,    1),
            new Club("Base Santos",        Country.BRASIL,    1),
            new Club("Base São Paulo",     Country.BRASIL,    1),
            new Club("Base Boca Juniors",  Country.ARGENTINA, 1),
            new Club("Base River Plate",   Country.ARGENTINA, 1),
            new Club("Base Independiente", Country.ARGENTINA, 1),
            new Club("Base Sporting",      Country.PORTUGAL,  1),
            new Club("Base Benfica",       Country.PORTUGAL,  1),
            new Club("Base Peñarol",       Country.URUGUAI,   1),
            new Club("Base Nacional",      Country.URUGUAI,   1),
            new Club("Base Club América",  Country.MEXICO,    1),
            new Club("Base Chivas",        Country.MEXICO,    1),
            new Club("Base LA Galaxy",     Country.EUA,       1),
            new Club("Base New York FC",   Country.EUA,       1)
    );

    // Mapeamento base → clube profissional
    private static final Map<String, Club> MAPA_PROFISSIONAL = Map.ofEntries(
            Map.entry("Base Corinthians",   new Club("Corinthians",   Country.BRASIL,    4)),
            Map.entry("Base Flamengo",      new Club("Flamengo",      Country.BRASIL,    4)),
            Map.entry("Base Santos",        new Club("Santos",        Country.BRASIL,    3)),
            Map.entry("Base São Paulo",     new Club("São Paulo",     Country.BRASIL,    3)),
            Map.entry("Base Boca Juniors",  new Club("Boca Juniors",  Country.ARGENTINA, 4)),
            Map.entry("Base River Plate",   new Club("River Plate",   Country.ARGENTINA, 4)),
            Map.entry("Base Independiente", new Club("Independiente", Country.ARGENTINA, 3)),
            Map.entry("Base Sporting",      new Club("Sporting",      Country.PORTUGAL,  3)),
            Map.entry("Base Benfica",       new Club("Benfica",       Country.PORTUGAL,  4)),
            Map.entry("Base Peñarol",       new Club("Peñarol",       Country.URUGUAI,   3)),
            Map.entry("Base Nacional",      new Club("Nacional",      Country.URUGUAI,   3)),
            Map.entry("Base Club América",  new Club("Club América",  Country.MEXICO,    3)),
            Map.entry("Base Chivas",        new Club("Chivas",        Country.MEXICO,    3)),
            Map.entry("Base LA Galaxy",     new Club("LA Galaxy",     Country.EUA,       3)),
            Map.entry("Base New York FC",   new Club("New York FC",   Country.EUA,       3))
    );

    public static Club gerarClubeBase(Country paisJogador) {
        List<Club> clubesDoPais = CLUBES_BASE.stream()
                .filter(c -> c.getPais() == paisJogador)
                .collect(Collectors.toList());

        if (!clubesDoPais.isEmpty() && r.nextInt(100) < 90)
            return clubesDoPais.get(r.nextInt(clubesDoPais.size()));

        List<Club> clubesContinente = CLUBES_BASE.stream()
                .filter(c -> c.getPais().getContinent() == paisJogador.getContinent()
                             && c.getPais() != paisJogador)
                .collect(Collectors.toList());

        if (!clubesContinente.isEmpty())
            return clubesContinente.get(r.nextInt(clubesContinente.size()));

        return CLUBES_BASE.get(r.nextInt(CLUBES_BASE.size()));
    }

    // Retorna o clube profissional correspondente ao clube de base
    public static Club getClubeProfissional(Club clubeBase) {
        return MAPA_PROFISSIONAL.getOrDefault(clubeBase.getNome(), clubeBase);
    }
}