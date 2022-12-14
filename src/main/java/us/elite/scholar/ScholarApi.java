package us.elite.scholar;

public enum ScholarApi {

    KEY("5e6e7d4b1607cdcf63758da7024b75b5abbf2db3b7cfacef426710753071adb2"),
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
