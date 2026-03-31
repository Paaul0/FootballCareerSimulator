package service;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransferMarketService {

    private static final Random r = new Random();

    private static final List<Club> CLUBES_NIVEL2 = List.of(
            new Club("Guarani",             Country.BRASIL,    2),
            new Club("Ponte Preta",         Country.BRASIL,    2),
            new Club("Ceará",               Country.BRASIL,    2),
            new Club("Atlético Goianiense", Country.BRASIL,    2),
            new Club("Godoy Cruz",          Country.ARGENTINA, 2),
            new Club("Lanús",               Country.ARGENTINA, 2),
            new Club("Braga",               Country.PORTUGAL,  2),
            new Club("Vitória SC",          Country.PORTUGAL,  2),
            new Club("Defensor",            Country.URUGUAI,   2),
            new Club("Toluca",              Country.MEXICO,    2),
            new Club("FC Dallas",           Country.EUA,       2)
    );

    private static final List<Club> CLUBES_NIVEL3 = List.of(
            new Club("Athletico-PR",  Country.BRASIL,    3),
            new Club("Internacional", Country.BRASIL,    3),
            new Club("Grêmio",        Country.BRASIL,    3),
            new Club("Racing Club",   Country.ARGENTINA, 3),
            new Club("San Lorenzo",   Country.ARGENTINA, 3),
            new Club("Porto",         Country.PORTUGAL,  3),
            new Club("Monterrey",     Country.MEXICO,    3),
            new Club("Seattle",       Country.EUA,       3)
    );

    private static final List<Club> CLUBES_NIVEL4 = List.of(
            new Club("Palmeiras",   Country.BRASIL,   4),
            new Club("Atlético-MG", Country.BRASIL,   4),
            new Club("Valencia",    Country.ESPANHA,  4),
            new Club("Sevilla",     Country.ESPANHA,  4),
            new Club("Lazio",       Country.ITALIA,   4),
            new Club("Napoli",      Country.ITALIA,   4),
            new Club("Lyon",        Country.ESPANHA,  4)
    );

    private static final List<Club> CLUBES_NIVEL5 = List.of(
            new Club("Real Madrid",   Country.ESPANHA, 5),
            new Club("Barcelona",     Country.ESPANHA, 5),
            new Club("Manchester C.", Country.ESPANHA, 5),
            new Club("PSG",           Country.ESPANHA, 5),
            new Club("Bayern",        Country.ESPANHA, 5),
            new Club("Juventus",      Country.ITALIA,  5),
            new Club("Liverpool",     Country.ESPANHA, 5)
    );

    public static List<TransferOffer> gerarPropostas(Player jogador) {
        List<TransferOffer> propostas = new ArrayList<>();
        int score = PerformanceService.calcularScore(jogador);
        int nivelAtual = jogador.getClubeAtual().getNivel();

        int chance;
        if      (score < 40)  chance = 20;
        else if (score < 80)  chance = 45;
        else if (score < 130) chance = 70;
        else                  chance = 90;

        if (r.nextInt(100) > chance) return propostas;

        int quantidade;
        if      (score < 80)  quantidade = 1;
        else if (score < 130) quantidade = 1 + r.nextInt(2);
        else                  quantidade = 2 + r.nextInt(2);

        List<String> clubesJaAdicionados = new ArrayList<>();
        clubesJaAdicionados.add(jogador.getClubeAtual().getNome());

        int tentativas = 0;
        while (propostas.size() < quantidade && tentativas < 20) {
            tentativas++;
            Club clube = sortearClube(score, nivelAtual);
            if (clubesJaAdicionados.contains(clube.getNome())) continue;
            clubesJaAdicionados.add(clube.getNome());
            PlayerRole role = definirRole(score, clube.getNivel());
            propostas.add(new TransferOffer(clube, role));
        }

        return propostas;
    }

    private static Club sortearClube(int score, int nivelAtual) {
        int nivelMaximo;
        if      (score < 40)  nivelMaximo = Math.min(nivelAtual, 2);
        else if (score < 80)  nivelMaximo = Math.min(nivelAtual + 1, 3);
        else if (score < 130) nivelMaximo = Math.min(nivelAtual + 1, 4);
        else                  nivelMaximo = 5;

        int diff = Math.max(1, nivelMaximo - nivelAtual + 1);
        int nivelSorteado = nivelAtual + r.nextInt(diff);
        nivelSorteado = Math.max(2, Math.min(nivelSorteado, 5));

        List<Club> pool = switch (nivelSorteado) {
            case 2  -> CLUBES_NIVEL2;
            case 3  -> CLUBES_NIVEL3;
            case 4  -> CLUBES_NIVEL4;
            default -> CLUBES_NIVEL5;
        };

        return pool.get(r.nextInt(pool.size()));
    }

    private static PlayerRole definirRole(int score, int nivelClube) {
        if (score >= 150 && nivelClube >= 4) return PlayerRole.DESTAQUE;
        if (score >= 100)                    return PlayerRole.TITULAR;
        if (score >= 60)                     return PlayerRole.ROTACAO;
        return PlayerRole.JOVEM_PROMESSA;
    }
}