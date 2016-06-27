package beans;

public enum SearchCriteria {
	TIEFENSUCHE("TIEFENSUCHE"),
	BREITENSUCHE("BREITENSUCHE"),
	DIJKSTRA("DIJKSTRA");
	
	private final String text;

    private SearchCriteria(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
