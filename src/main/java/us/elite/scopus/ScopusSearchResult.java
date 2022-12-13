package us.elite.scopus;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static us.elite.Application.GSON;

@Getter
public class ScopusSearchResult {

    @SerializedName("opensearch:totalResults")
    private int totalResults;

    @SerializedName("opensearch:startIndex")
    private int startIndex;

    @SerializedName("opensearch:itemsPerPage")
    private int itemsPerPage;

    @Getter(AccessLevel.NONE)
    private JsonArray entry;

    @Getter(AccessLevel.NONE)
    private final List<ScopusEntry> ENTRIES = new ArrayList<>();

    public List<ScopusEntry> entries() {
        if (ENTRIES.isEmpty() && !entry.isEmpty())
            entry.asList()
                .stream()
                .map(element -> GSON.fromJson(element, ScopusEntry.class))
                .forEach(ENTRIES::add);
        return ENTRIES;
    }

    @Override
    public String toString() {
        return "ScopusSearchResult{" +
                "totalResults=" + totalResults +
                ", startIndex=" + startIndex +
                ", itemsPerPage=" + itemsPerPage +
                ", ENTRIES=" + entries() +
                '}';
    }
}
