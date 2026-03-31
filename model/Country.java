package model;

public enum Country {
    BRASIL(Continent.AMERICA_DO_SUL),
    ARGENTINA(Continent.AMERICA_DO_SUL),
    URUGUAI(Continent.AMERICA_DO_SUL),
    ESPANHA(Continent.EUROPA),
    ITALIA(Continent.EUROPA),
    PORTUGAL(Continent.EUROPA),
    FRANCA(Continent.EUROPA),
    ALEMANHA(Continent.EUROPA),
    INGLATERRA(Continent.EUROPA),
    EUA(Continent.AMERICA_DO_NORTE),
    MEXICO(Continent.AMERICA_DO_NORTE);

    private final Continent continent;

    Country(Continent continent) {
        this.continent = continent;
    }

    public Continent getContinent() { return continent; }
}