package saveOurPlanet;

public enum Field {
    RENEWABLE_ENERGY_INITIATIVES("Renewable Energy Initiatives"),
    SUSTAINABLE_AGRICULTURE("Sustainable Agriculture"),
    WASTE_MANAGEMENT("Waste Management"),
    CLIMATE_CHANGE("Climate Change");

    private final String displayName;

    private Field(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
