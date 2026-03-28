package ui;

import model.Player;
import model.TransferOffer;

import java.util.List;

public class TransferUI {

    public static TransferOffer exibirPropostas(Player jogador, List<TransferOffer> propostas) {
        if (propostas.isEmpty()) {
            System.out.println("\n📭 Nenhuma proposta de transferência chegou.");
            return null;
        }

        System.out.println("\n📬 PROPOSTAS DE TRANSFERÊNCIA:");
        System.out.println("Clube atual: " + jogador.getClubeAtual().getNome());
        System.out.println();

        for (int i = 0; i < propostas.size(); i++) {
            TransferOffer p = propostas.get(i);
            System.out.printf("%d. %s | Nível %d | Role: %s%n",
                i + 1, p.getClube().getNome(), p.getClube().getNivel(), p.getRoleOferecido());
        }
        System.out.println("0. Recusar todas e ficar no clube atual");
        System.out.print("\nEscolha: ");

        int escolha = MenuUI.lerInt();
        if (escolha <= 0 || escolha > propostas.size()) {
            System.out.println("✋ Você recusou todas as propostas.");
            return null;
        }

        TransferOffer escolhida = propostas.get(escolha - 1);
        System.out.println("\n✈️  Transferência confirmada para " + escolhida.getClube().getNome() + "!");
        return escolhida;
    }
}
