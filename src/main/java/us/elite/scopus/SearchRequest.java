package us.elite.scopus;

import com.google.gson.JsonObject;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.TreeSet;

import static us.elite.Application.GSON;

public interface SearchRequest {

    Collection<String> fields();

    HttpRequest getHttpRequest();

    default ScopusSearchResult getSearchResult(HttpResponse<String> response) {
        System.out.println(response.body());
        final JsonObject wrapper = GSON.fromJson(response
                .body(), JsonObject.class);

        return GSON.fromJson(wrapper.get("search-results"), ScopusSearchResult.class);
    }

    class Builder {
        private String query, content;
        private final Collection<String> fields = new TreeSet<>();
        private int count;
        private URI uri;
        private final String API_KEY;

        public Builder(String API_KEY) {
            this.API_KEY = API_KEY;
        }


        public Builder setQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public SearchRequest build() throws URISyntaxException {
            final URIBuilder builder = new URIBuilder(ScopusApi.SCOPUS_API.url());
            if (query != null && !query.isBlank())
                builder.addParameter("query", query);
            if (content != null && !content.isBlank())
                builder.addParameter("content", content);
            if (count >= 0)
                builder.addParameter("count", String.valueOf(count));
            if (!fields.isEmpty())
                builder.addParameter("field", fields.stream()
                        .reduce("", (subtotal, element) -> subtotal + ", " + element));
            uri = builder.build();
            return new SearchRequest() {

                @Override
                public Collection<String> fields() {
                    return fields;
                }

                @Override
                public HttpRequest getHttpRequest() {
                    return HttpRequest.newBuilder(uri)
                            .header("X-ELS-APIKey", API_KEY)
                            .header("Accept", "application/json")
                            .build();
                }
            };
        }
    }

}
