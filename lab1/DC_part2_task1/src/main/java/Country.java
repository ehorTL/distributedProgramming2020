public class Country {
    private String code;
    private String name;
    private long id;

    public Country(String code, String name, long id) {
        this.code = code;
        this.name = name;
        this.id = id;
    }

    public Country() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name + " --- COUNTRYCODE: " + this.code + " --- ID: " + this.id;
    }
}
