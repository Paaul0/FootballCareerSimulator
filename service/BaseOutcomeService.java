package service;

import model.BaseCareer;
import model.BaseOutcome;

import java.util.Random;

public class BaseOutcomeService {
    public static BaseOutcome decidir(BaseCareer baseCareer) {
        int titulos = baseCareer.getTitulosBase();
        Random r = new Random();
        int chance = r.nextInt(100);

        if (titulos <= 1) {
            if (chance < 60) return BaseOutcome.DISPENSADO;
            return BaseOutcome.CONTINUA;
        }
        if (titulos <= 3) {
            if (chance < 10) return BaseOutcome.DISPENSADO;
            if (chance < 80) return BaseOutcome.CONTINUA;
            return BaseOutcome.SAI_POR_ESCOLHA;
        }
        if (chance < 70) return BaseOutcome.CONTINUA;
        return BaseOutcome.SAI_POR_ESCOLHA;
    }
}
