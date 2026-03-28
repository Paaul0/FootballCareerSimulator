package service;

import model.Player;
import model.PlayerRole;

import java.util.Random;

public class RetirementService {

    private static final Random r = new Random();

    public static boolean verificarAposentadoria(Player jogador, PlayerRole role, int score) {
        int idade = jogador.getIdade();
        if (idade >= 40) return true;
        if (idade >= 35) {
            int chance = (idade - 34) * 20;
            if (r.nextInt(100) < chance) return true;
        }
        if (idade >= 28 && role == PlayerRole.JOVEM_PROMESSA && score < 20)
            if (r.nextInt(100) < 30) return true;
        return false;
    }

    public static String mensagemAposentadoriaVoluntaria(Player jogador, PlayerRole role) {
        int idade = jogador.getIdade();
        if (idade >= 36 && role == PlayerRole.JOVEM_PROMESSA)
            return "Você tem " + idade + " anos e ainda é reserva. Talvez seja hora de pendurar as chuteiras.";
        if (idade >= 38)
            return "Você tem " + idade + " anos. Sua carreira foi longa — deseja se aposentar?";
        return null;
    }
}
