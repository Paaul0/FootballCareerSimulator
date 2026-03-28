package model;

public class TransferOffer {
    private Club clube;
    private PlayerRole roleOferecido;

    public TransferOffer(Club clube, PlayerRole roleOferecido) {
        this.clube = clube;
        this.roleOferecido = roleOferecido;
    }

    public Club getClube()             { return clube; }
    public PlayerRole getRoleOferecido() { return roleOferecido; }
}
