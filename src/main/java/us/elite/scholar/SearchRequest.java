package us.elite.scholar;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static us.elite.Application.GSON;

public interface SearchRequest {

    Collection<ScholarProfileResult> getScholarResults();

    class Builder {

        private String author;
        private int maxResults;

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setMaxResults(int maxResults) {
            this.maxResults = maxResults;
            return this;
        }

        public Builder setUri(URI uri) {
            return this;
        }

        public SearchRequest build() throws IOException {

            final StringBuilder stringBuilder = new StringBuilder(ScholarApi.PROFILES.value());

            if (author != null && !author.isBlank())
                stringBuilder.append("&mauthors=").append(author);

            stringBuilder.append("&api_key=").append(ScholarApi.KEY.value());

            final URL url = new URL(stringBuilder.toString());
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);

            final InputStream inputStream = urlConnection.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            final StringBuilder result = new StringBuilder();
            String temp = "";

            while ((temp = bufferedReader.readLine()) != null) {
                result.append(temp).append('\n');
            }

            bufferedReader.close();

            return () -> {
                final JsonObject wrapper = GSON.fromJson(result.toString(), JsonObject.class);


                if (wrapper.has("profiles")) {
                    final List<ScholarProfileResult> results = wrapper.getAsJsonArray("profiles")
                            .asList()
                            .stream()
                            .map(element -> GSON.fromJson(element, ScholarProfileResult.class))
                            .toList();
                    if (maxResults > 0 && maxResults < results.size())
                        return results.stream().limit(maxResults).collect(Collectors.toList());
                    return results;
                }
                return new ArrayList<>();

            };
        }
    }

}
