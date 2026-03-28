package model;

public enum Position {
    CENTROAVANTE(1.0, 0.4),
    PONTA(0.85, 0.7),
    MEIA(0.6, 1.0),
    VOLANTE(0.3, 0.6),
    ZAGUEIRO(0.15, 0.2);

    private final double fatorGols;
    private final double fatorAssistencias;

    Position(double fatorGols, double fatorAssistencias) {
        this.fatorGols = fatorGols;
        this.fatorAssistencias = fatorAssistencias;
    }

    public double getFatorGols()         { return fatorGols; }
    public double getFatorAssistencias() { return fatorAssistencias; }
}
