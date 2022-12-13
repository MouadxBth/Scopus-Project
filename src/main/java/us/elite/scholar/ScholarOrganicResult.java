package us.elite.scholar;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static us.elite.Application.GSON;

public record ScholarOrganicResult(
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

}
