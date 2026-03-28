package service;

import model.Club;
import model.Country;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BaseClubFactory {

    private static final Random r = new Random();

    private static final List<Club> CLUBES = List.of(
        new Club("Santos",        Country.BRASIL,    1),
        new Club("Flamengo",      Country.BRASIL,    1),
        new Club("Corinthians",   Country.BRASIL,    1),
        new Club("São Paulo",     Country.BRASIL,    1),
        new Club("Boca Juniors",  Country.ARGENTINA, 1),
        new Club("River Plate",   Country.ARGENTINA, 1),
        new Club("Independiente", Country.ARGENTINA, 1),
        new Club("Sporting",      Country.PORTUGAL,  1),
        new Club("Benfica",       Country.PORTUGAL,  1),
        new Club("Peñarol",       Country.URUGUAI,   1),
        new Club("Nacional",      Country.URUGUAI,   1),
        new Club("Club América",  Country.MEXICO,    1),
        new Club("Chivas",        Country.MEXICO,    1),
        new Club("LA Galaxy",     Country.EUA,       1),
        new Club("New York FC",   Country.EUA,       1)
    );

    public static Club gerarClubeBase(Country paisJogador) {
        List<Club> clubesDoPais = CLUBES.stream()
            .filter(c -> c.getPais() == paisJogador)
            .collect(Collectors.toList());

        if (!clubesDoPais.isEmpty() && r.nextInt(100) < 90)
            return clubesDoPais.get(r.nextInt(clubesDoPais.size()));

        List<Club> clubesContinente = CLUBES.stream()
            .filter(c -> c.getPais().getContinent() == paisJogador.getContinent()
                      && c.getPais() != paisJogador)
            .collect(Collectors.toList());

        if (!clubesContinente.isEmpty())
            return clubesContinente.get(r.nextInt(clubesContinente.size()));

        return CLUBES.get(r.nextInt(CLUBES.size()));
    }
}
