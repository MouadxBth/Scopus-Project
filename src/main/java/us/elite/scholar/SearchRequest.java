package us.elite.scholar;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static us.elite.Application.GSON;

public interface SearchRequest {

    Collection<ScholarOrganicResult> getScholarResults();


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

            final StringBuilder stringBuilder = new StringBuilder(ScholarApi.URI.value());

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
                    final List<ScholarOrganicResult> results = wrapper.getAsJsonArray("profiles")
                            .asList()
                            .stream()
                            .map(element -> GSON.fromJson(element, ScholarOrganicResult.class))
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
