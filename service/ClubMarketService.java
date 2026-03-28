package service;

import model.Club;
import model.Country;

import java.util.List;
import java.util.Random;

public class ClubMarketService {

    private static final List<Club> CLUBES_MENORES = List.of(
        new Club("Guarani",             Country.BRASIL,    2),
        new Club("Ponte Preta",         Country.BRASIL,    2),
        new Club("Ceará",               Country.BRASIL,    2),
        new Club("Atlético Goianiense", Country.BRASIL,    2),
        new Club("Godoy Cruz",          Country.ARGENTINA, 2),
        new Club("Lanús",               Country.ARGENTINA, 2),
        new Club("Braga",               Country.PORTUGAL,  2),
        new Club("Vitória SC",          Country.PORTUGAL,  2)
    );

    public static Club buscarNovoClube(Country paisJogador) {
        Random r = new Random();
        List<Club> possiveis = CLUBES_MENORES.stream()
            .filter(c -> c.getPais() == paisJogador)
            .toList();
        return possiveis.get(r.nextInt(possiveis.size()));
    }
}
