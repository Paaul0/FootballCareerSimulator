package model;

import java.util.Random;

public class BaseCareer {
    private int anosBase = 2;
    private int titulosBase;
    private int golsBase;
    private int assistenciasBase;

    public BaseCareer() {
        simularBase();
    }

    private void simularBase() {
        Random r = new Random();
        for (int i = 0; i < anosBase; i++) {
            titulosBase += r.nextInt(3);
            golsBase += r.nextInt(6) + 1;
            assistenciasBase += r.nextInt(5);
        }
    }

    public int getTitulosBase()      { return titulosBase; }
    public int getGolsBase()         { return golsBase; }
    public int getAssistenciasBase() { return assistenciasBase; }
}
