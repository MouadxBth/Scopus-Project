package us.elite.scopusjavaproject.scopus;

public enum ScopusApi {

    SCOPUS_API("https://api.elsevier.com/content/search/scopus"),
    AUTHOR_API("https://api.elsevier.com/content/author");

    private final String url;

    ScopusApi(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
