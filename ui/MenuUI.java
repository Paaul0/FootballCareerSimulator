package ui;

import model.*;
import service.BaseClubFactory;
import service.SaveService;

import java.util.Scanner;

public class MenuUI {

    private static final Scanner sc = new Scanner(System.in);

    public static void exibirBoasVindas() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       FOOTBALL CAREER MANAGER        ║");
        System.out.println("║         Sua lenda começa aqui        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }

    public static int menuInicial() {
        System.out.println("1. Nova carreira");
        if (SaveService.existeSave())
            System.out.println("2. Continuar carreira salva");
        System.out.println("0. Sair");
        System.out.print("\nEscolha: ");
        return lerInt();
    }

    public static Player criarJogador() {
        System.out.println("\n══════════════ NOVO JOGADOR ══════════════");

        System.out.print("Nome do jogador: ");
        String nome = sc.nextLine().trim();

        System.out.println("\nEscolha seu país:");
        Country[] paises = Country.values();
        for (int i = 0; i < paises.length; i++)
            System.out.println((i + 1) + ". " + paises[i]);
        System.out.print("Escolha: ");
        Country pais = paises[lerInt() - 1];

        System.out.println("\nEscolha sua posição:");
        Position[] posicoes = Position.values();
        for (int i = 0; i < posicoes.length; i++)
            System.out.println((i + 1) + ". " + posicoes[i]);
        System.out.print("Escolha: ");
        Position position = posicoes[lerInt() - 1];

        Club clubeBase = BaseClubFactory.gerarClubeBase(pais);
        System.out.println("\n✅ Você começou nas categorias de base do " + clubeBase.getNome() + "!");

        return new Player(nome, pais, clubeBase, position);
    }

    public static int menuTemporada(String nomeJogador, int temporada, int idade, String clube, PlayerRole role) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  " + nomeJogador + " | Temporada " + temporada + " | " + idade + " anos");
        System.out.println("  " + clube + " | " + role);
        System.out.println("══════════════════════════════════════════");
        System.out.println("1. Simular temporada");
        System.out.println("2. Ver estatísticas da carreira");
        System.out.println("3. Salvar jogo");
        System.out.println("4. Aposentar");
        System.out.print("\nEscolha: ");
        return lerInt();
    }

    public static boolean confirmar(String mensagem) {
        System.out.print(mensagem + " (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        return resp.equals("s") || resp.equals("sim");
    }

    public static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    public static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
