package us.elite.scholar;

public enum ScholarApi {

    KEY("KEY_HERE"),
    PROFILES("https://serpapi.com/search?engine=google_scholar_profiles"),
    AUTHORS("https://serpapi.com/search?engine=google_scholar_author");

    private final String value;

    ScholarApi(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
