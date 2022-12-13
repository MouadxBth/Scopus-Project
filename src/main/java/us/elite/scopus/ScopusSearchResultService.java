package us.elite.scopus;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
public class ScopusSearchResultService {

    private final SearchRequest.Builder requestBuilder;
    private final HttpClient client;
    private HttpResponse<String> response;

    public ScopusSearchResultService() {
        requestBuilder = new SearchRequest.Builder("f1e9a6e0fe4f6d7916830aa83fe4dda1");
        client = HttpClient.newHttpClient();
    }

    public SearchRequest.Builder requestBuilder() {
        return requestBuilder;
    }

    public ScopusSearchResult result() throws URISyntaxException, IOException, InterruptedException {
        final SearchRequest searchRequest = requestBuilder.build();
        final HttpResponse<String> response = client
                .send(searchRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());

        return searchRequest.getSearchResult(response);
    }
}
