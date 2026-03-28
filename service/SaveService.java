package service;

import model.*;

import java.io.*;
import java.util.List;

public class SaveService {

    private static final String SAVE_FILE = "carreira.txt";

    public static void salvar(Player jogador, CareerStats stats, int temporadaAtual, PlayerRole roleAtual) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            pw.println(jogador.getNome());
            pw.println(jogador.getPais());
            pw.println(jogador.getPosition());
            pw.println(jogador.getIdade());
            pw.println(jogador.getClubeAtual().getNome());
            pw.println(jogador.getClubeAtual().getPais());
            pw.println(jogador.getClubeAtual().getNivel());
            pw.println(roleAtual);
            pw.println(temporadaAtual);
            pw.println(stats.getTotalJogos());
            pw.println(stats.getTotalGols());
            pw.println(stats.getTotalAssistencias());
            pw.println(stats.getTotalTitulos());
            pw.println(stats.getTitulos().size());
            for (String t : stats.getTitulos()) pw.println(t);
            pw.println(stats.getHistorico().size());
            for (Season s : stats.getHistorico()) {
                pw.println(s.getNumero());
                pw.println(s.getClube().getNome());
                pw.println(s.getClube().getPais());
                pw.println(s.getClube().getNivel());
                pw.println(s.getRole());
                pw.println(s.getJogos());
                pw.println(s.getGols());
                pw.println(s.getAssistencias());
                pw.println(s.getIdade());
                pw.println(s.getEventoDestaque() != null ? s.getEventoDestaque() : "NENHUM");
            }
            System.out.println("\n💾 Jogo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static boolean existeSave() {
        return new File(SAVE_FILE).exists();
    }

    public static Object[] carregar() {
        try (BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE))) {
            String nome       = br.readLine();
            Country pais      = Country.valueOf(br.readLine());
            Position position = Position.valueOf(br.readLine());
            int idade         = Integer.parseInt(br.readLine());
            String clubeNome  = br.readLine();
            Country clubePais = Country.valueOf(br.readLine());
            int clubeNivel    = Integer.parseInt(br.readLine());
            PlayerRole role   = PlayerRole.valueOf(br.readLine());
            int temporada     = Integer.parseInt(br.readLine());

            Club clubeAtual = new Club(clubeNome, clubePais, clubeNivel);
            Player jogador = new Player(nome, pais, clubeAtual, position);
            jogador.setClubeAtual(clubeAtual);
            jogador.setIdade(idade);

            CareerStats stats = new CareerStats();
            br.readLine(); br.readLine(); br.readLine(); br.readLine();

            int qtdTitulos = Integer.parseInt(br.readLine());
            for (int i = 0; i < qtdTitulos; i++) stats.registrarTitulo(br.readLine());

            int qtdTemporadas = Integer.parseInt(br.readLine());
            for (int i = 0; i < qtdTemporadas; i++) {
                int num       = Integer.parseInt(br.readLine());
                String cn     = br.readLine();
                Country cp    = Country.valueOf(br.readLine());
                int cnivel    = Integer.parseInt(br.readLine());
                PlayerRole sr = PlayerRole.valueOf(br.readLine());
                int jogos     = Integer.parseInt(br.readLine());
                int gols      = Integer.parseInt(br.readLine());
                int assists   = Integer.parseInt(br.readLine());
                int idadeS    = Integer.parseInt(br.readLine());
                String evento = br.readLine();
                Club sc = new Club(cn, cp, cnivel);
                Season s = new Season(num, sc, sr, jogos, gols, assists, idadeS);
                if (!"NENHUM".equals(evento)) s.setEventoDestaque(evento);
                stats.registrarTemporada(s);
            }

            System.out.println("\n✅ Carreira carregada com sucesso!");
            return new Object[]{ jogador, stats, temporada, role };
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return null;
        }
    }
}
