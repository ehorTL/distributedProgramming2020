public class City {
    private String name;
    private String code;
    private long id;
    private long population;
    private boolean isCapital;

    public City(String name, String code, long id, long population, boolean isCapital) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.population = population;
        this.isCapital = isCapital;
    }

    public City() {
    }

    public boolean isCapital() {
        return isCapital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return this.name + " --- CITYCODE: " + this.code + " --- POPULATION: " + this.population +
                (this.isCapital ? " --- CAPITAL" : "") +
        " --- ID:" + this.id;
    }
}
