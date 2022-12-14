package us.elite.scholar;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

@Service
public class ScholarSearchResultService {

    private final SearchRequest.Builder requestBuilder;

    public ScholarSearchResultService() {
        requestBuilder = new SearchRequest.Builder();
    }

    public SearchRequest.Builder requestBuilder() {
        return requestBuilder;
    }

    public Collection<ScholarProfileResult> result() throws URISyntaxException, IOException, InterruptedException {
        final SearchRequest searchRequest = requestBuilder.build();
        return searchRequest.getScholarResults();
    }

}
