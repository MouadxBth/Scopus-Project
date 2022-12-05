package us.elite.scopusjavaproject.scopus;

import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
public class ScopusSearchResultService {

    private final ScopusSearchResult result;

    public ScopusSearchResultService() throws Exception {

        final SearchRequest searchRequest = new SearchRequest
                .Builder("f1e9a6e0fe4f6d7916830aa83fe4dda1")
                .setQuery("AUTHOR-NAME(Einstein, a)")
                .setCount(10)
                .build();

        final HttpClient client = HttpClient.newHttpClient();

        final HttpResponse<String> response = client
                .send(searchRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());

        result = searchRequest.getSearchResult(response);

    }

    public ScopusSearchResult result() {
        return result;
    }
}
