import model.*;
import service.SaveService;
import ui.CareerManager;
import ui.MenuUI;

public class Main {
    public static void main(String[] args) {
        MenuUI.exibirBoasVindas();

        boolean rodando = true;
        while (rodando) {
            int opcao = MenuUI.menuInicial();

            switch (opcao) {
                case 1 -> {
                    Player jogador = MenuUI.criarJogador();
                    new CareerManager(jogador).rodar();
                }
                case 2 -> {
                    Object[] dados = SaveService.carregar();
                    if (dados != null) {
                        Player jogador    = (Player)      dados[0];
                        CareerStats stats = (CareerStats) dados[1];
                        int temporada     = (int)         dados[2];
                        PlayerRole role   = (PlayerRole)  dados[3];
                        new CareerManager(jogador, stats, temporada, role).rodar();
                    }
                }
                case 0 -> {
                    System.out.println("\nAté logo!");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
