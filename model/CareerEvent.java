package model;

public enum CareerEvent {
    LESAO_GRAVE("Lesão grave", "Você sofreu uma lesão séria e ficou meses fora. Jogos reduzidos em 40%."),
    ARTILHEIRO_DO_CAMPEONATO("Artilheiro do campeonato", "Você foi o artilheiro! +30% de gols na temporada e propostas melhores."),
    MELHOR_DO_MUNDO("Melhor jogador do mundo", "Você foi eleito o melhor do mundo! Bônus enorme de pontuação."),
    REBAIXAMENTO("Rebaixamento", "Seu clube foi rebaixado. Você pode aceitar a situação ou procurar novo clube."),
    TITULO_NACIONAL("Título nacional", "Seu clube venceu o campeonato nacional! +1 título na carreira."),
    TITULO_CONTINENTAL("Título continental", "Seu clube venceu a competição continental! +1 título especial."),
    CONVOCACAO_SELECAO("Convocação para a seleção", "Você foi convocado para a seleção nacional! Prestígio aumentado."),
    ESCANDALO("Escândalo", "Um escândalo envolveu seu clube. Propostas de transferência ficam mais raras."),
    RENOVACAO_FORCADA("Renovação forçada", "Seu clube quer renovar a qualquer custo. Você tem poder de negociação."),
    GOLEADA_HISTORICA("Goleada histórica", "Você participou de uma goleada histórica! Bônus de gols e assistências.");

    private final String titulo;
    private final String descricao;

    CareerEvent(String titulo, String descricao) {
        this.titulo    = titulo;
        this.descricao = descricao;
    }

    public String getTitulo()    { return titulo; }
    public String getDescricao() { return descricao; }
}
