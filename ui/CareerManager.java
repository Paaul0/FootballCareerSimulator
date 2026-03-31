package ui;

import model.*;
import service.*;
import service.ChampionshipService.ResultadoCampeonato;

import java.util.List;

public class CareerManager {

    private Player jogador;
    private CareerStats stats;
    private int temporadaAtual = 1;
    private PlayerRole roleAtual = PlayerRole.JOVEM_PROMESSA;

    public CareerManager(Player jogador) {
        this.jogador = jogador;
        this.stats   = new CareerStats();
        simularFaseBase();
    }

    public CareerManager(Player jogador, CareerStats stats, int temporada, PlayerRole role) {
        this.jogador        = jogador;
        this.stats          = stats;
        this.temporadaAtual = temporada;
        this.roleAtual      = role;
    }

    private void simularFaseBase() {
        BaseCareer base    = jogador.getBaseCareer();
        BaseOutcome outcome = BaseOutcomeService.decidir(base);

        System.out.println("\n══════════ FASE DE BASE ══════════");
        System.out.println("Clube base    : " + jogador.getClubeBase().getNome());
        System.out.println("Anos na base  : 2");
        System.out.println("Títulos       : " + base.getTitulosBase());
        System.out.println("Gols          : " + base.getGolsBase());
        System.out.println("Assistências  : " + base.getAssistenciasBase());

        switch (outcome) {
            case DISPENSADO -> {
                System.out.println("\n😞 Você foi dispensado da base...");
                Club novoClube = ClubMarketService.buscarNovoClube(jogador.getPais());
                jogador.setClubeAtual(novoClube);
                System.out.println("Mas encontrou uma chance no " + novoClube.getNome() + ".");
            }
            case CONTINUA -> {
                Club profissional = BaseClubFactory.getClubeProfissional(jogador.getClubeBase());
                jogador.setClubeAtual(profissional);
                System.out.println("\n👍 Você foi promovido para o profissional do "
                                   + profissional.getNome() + "!");
            }
            case SAI_POR_ESCOLHA -> {
                System.out.println("\n🌟 Sua base foi tão boa que outros clubes te querem!");
                Club novoClube = ClubMarketService.buscarNovoClube(jogador.getPais());
                jogador.setClubeAtual(novoClube);
                System.out.println("Você escolheu ir para o " + novoClube.getNome() + ".");
            }
        }

        jogador.setIdade(19);
        MenuUI.pausar();
    }

    public void rodar() {
        boolean jogando = true;
        while (jogando) {
            int opcao = MenuUI.menuTemporada(
                    jogador.getNome(), temporadaAtual,
                    jogador.getIdade(), jogador.getClubeAtual().getNome(), roleAtual
            );
            switch (opcao) {
                case 1 -> jogando = simularTemporada();
                case 2 -> StatsUI.exibirResumoCarreira(jogador, stats);
                case 3 -> SaveService.salvar(jogador, stats, temporadaAtual, roleAtual);
                case 4 -> {
                    if (MenuUI.confirmar("Tem certeza que deseja se aposentar?")) {
                        StatsUI.exibirTelaAposentadoria(jogador, stats);
                        jogando = false;
                    }
                }
            }
        }
    }

    private boolean simularTemporada() {
        // 1. Simular temporada
        Season temporada = SeasonService.simularTemporada(jogador, temporadaAtual, roleAtual);

        // 2. Sortear e aplicar eventos
        int score = PerformanceService.calcularScore(jogador);
        List<CareerEvent> eventos = EventService.sortearEventos(jogador, score);
        for (CareerEvent e : eventos)
            EventService.aplicarEvento(e, jogador, temporada, stats);

        // 3. Registrar temporada
        stats.registrarTemporada(temporada);

        // 4. Exibir resultado
        SeasonUI.exibirResultadoTemporada(temporada, eventos);

        // 5. Simular campeonatos
        List<ResultadoCampeonato> campeonatos = ChampionshipService.simularCampeonatos(jogador, roleAtual);
        SeasonUI.exibirCampeonatos(campeonatos);

        boolean foiRebaixado = false;
        for (ResultadoCampeonato rc : campeonatos) {
            if (rc.titulo())
                stats.registrarTituloComClube(
                        rc.nomeCampeonato() + " (T" + temporadaAtual + ")",
                        jogador.getClubeAtual().getNome()
                );
            if (rc.rebaixado()) foiRebaixado = true;
        }

        // 6. Tratar rebaixamento
        if (foiRebaixado) {
            System.out.println("\n⬇️  Seu clube foi rebaixado!");
            System.out.println("Você pode ficar e tentar o acesso ou buscar um novo clube.");
            boolean ficar = MenuUI.confirmar("Deseja ficar no " + jogador.getClubeAtual().getNome() + "?");
            if (!ficar) {
                List<TransferOffer> propostas = TransferMarketService.gerarPropostas(jogador);
                if (!propostas.isEmpty()) {
                    TransferOffer escolhida = TransferUI.exibirPropostas(jogador, propostas);
                    if (escolhida != null) {
                        jogador.setClubeAtual(escolhida.getClube());
                        roleAtual = escolhida.getRoleOferecido();
                    }
                } else {
                    System.out.println("😞 Nenhuma proposta chegou. Você permanece no clube.");
                }
            }
        }

        // 7. Prêmios individuais
        score = PerformanceService.calcularScore(jogador);
        List<String> premios = AwardService.calcularPremios(jogador, roleAtual, stats, score);
        SeasonUI.exibirPremios(premios);
        for (String p : premios)
            stats.registrarPremioIndividual(p);

        // 8. Atualizar role
        PlayerRole novoRole = SeasonService.calcularNovoRole(roleAtual, score);
        if (novoRole != roleAtual) {
            System.out.println("\n🔄 Seu role mudou: " + roleAtual + " → " + novoRole);
            roleAtual = novoRole;
        }
        SeasonUI.exibirMensagemRole(roleAtual);

        // 9. Propostas de transferência (se não foi rebaixado)
        if (!foiRebaixado) {
            List<TransferOffer> propostas = TransferMarketService.gerarPropostas(jogador);
            TransferOffer escolhida = TransferUI.exibirPropostas(jogador, propostas);
            if (escolhida != null) {
                jogador.setClubeAtual(escolhida.getClube());
                roleAtual = escolhida.getRoleOferecido();
            }
        }

        // 10. Avançar idade e temporada
        jogador.setIdade(jogador.getIdade() + 1);
        temporadaAtual++;

        // 11. Verificar aposentadoria voluntária
        String msgAposent = RetirementService.mensagemAposentadoriaVoluntaria(jogador, roleAtual);
        if (msgAposent != null) {
            System.out.println("\n⚠️  " + msgAposent);
            if (MenuUI.confirmar("Deseja se aposentar?")) {
                StatsUI.exibirTelaAposentadoria(jogador, stats);
                return false;
            }
        }

        // 12. Verificar aposentadoria automática — só se não perguntou antes
        if (msgAposent == null &&
            RetirementService.verificarAposentadoria(jogador, roleAtual, score)) {
            System.out.println("\n🎽 Sua carreira chegou ao fim naturalmente.");
            StatsUI.exibirTelaAposentadoria(jogador, stats);
            return false;
        }

        MenuUI.pausar();
        return true;
    }
}