package service;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventService {

    private static final Random r = new Random();

    // Eventos que NÃO são títulos (títulos agora vêm do ChampionshipService)
    private static final CareerEvent[] EVENTOS_DISPONIVEIS = {
            CareerEvent.LESAO_GRAVE,
            CareerEvent.ARTILHEIRO_DO_CAMPEONATO,
            CareerEvent.REBAIXAMENTO,
            CareerEvent.CONVOCACAO_SELECAO,
            CareerEvent.ESCANDALO,
            CareerEvent.RENOVACAO_FORCADA,
            CareerEvent.GOLEADA_HISTORICA
    };

    public static List<CareerEvent> sortearEventos(Player jogador, int score) {
        List<CareerEvent> eventos = new ArrayList<>();
        if (r.nextInt(100) > 40) return eventos;

        CareerEvent evento = EVENTOS_DISPONIVEIS[r.nextInt(EVENTOS_DISPONIVEIS.length)];
        eventos.add(evento);

        if (r.nextInt(100) < 15) {
            CareerEvent segundo;
            do {
                segundo = EVENTOS_DISPONIVEIS[r.nextInt(EVENTOS_DISPONIVEIS.length)];
            } while (segundo == evento);
            eventos.add(segundo);
        }

        return eventos;
    }

    public static void aplicarEvento(CareerEvent evento, Player jogador,
                                     Season temporada, CareerStats stats) {
        switch (evento) {
            case LESAO_GRAVE -> {
                jogador.setJogosUltimaFase((int)(jogador.getJogosUltimaFase() * 0.6));
                jogador.setGolsUltimaFase((int)(jogador.getGolsUltimaFase() * 0.6));
                jogador.setAssistenciasUltimaFase((int)(jogador.getAssistenciasUltimaFase() * 0.6));
            }
            case ARTILHEIRO_DO_CAMPEONATO -> {
                int bonus = (int)(jogador.getGolsUltimaFase() * 0.30);
                jogador.setGolsUltimaFase(jogador.getGolsUltimaFase() + bonus);
            }
            case CONVOCACAO_SELECAO ->
                    jogador.setJogosUltimaFase(jogador.getJogosUltimaFase() + r.nextInt(10) + 5);
            case GOLEADA_HISTORICA -> {
                jogador.setGolsUltimaFase(jogador.getGolsUltimaFase() + r.nextInt(3) + 1);
                jogador.setAssistenciasUltimaFase(jogador.getAssistenciasUltimaFase() + r.nextInt(2) + 1);
            }
            default -> {}
        }
        temporada.setEventoDestaque(evento.getTitulo());
    }
}