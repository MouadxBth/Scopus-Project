package us.elite.scholar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static us.elite.Application.GSON;

public record ScholarProfileResult(
        @SerializedName("name")
        String fullName,

        @SerializedName("author_id")
        String id,

        String affiliations,

        String email,

        String thumbnail,

        @SerializedName("cited_by")
        int citedBy,

        JsonArray interests) {

    public List<ScholarInterest> getScholarInterests() {
        if (interests != null) {
            return interests.asList()
                    .stream()
                    .map(element -> GSON.fromJson(element, ScholarInterest.class))
                    .toList();
        }
        return new ArrayList<>();
    }

    public List<ScholarArticle> getScholarArticles() throws IOException {

        final StringBuilder stringBuilder = new StringBuilder(ScholarApi.AUTHORS.value());

        if (id == null || id.isBlank())
            return new ArrayList<>();
        stringBuilder.append("&author_id=").append(id);

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
        final JsonObject wrapper = GSON.fromJson(result.toString(), JsonObject.class);


        if (wrapper.has("articles")) {
            return wrapper.getAsJsonArray("articles")
                    .asList()
                    .stream()
                    .map(element -> GSON.fromJson(element, ScholarArticle.class))
                    .toList();
        }
        return new ArrayList<>();

    }

}
