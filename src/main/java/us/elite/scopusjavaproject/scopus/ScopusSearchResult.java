package us.elite.scopusjavaproject.scopus;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import us.elite.scopusjavaproject.ScopusJavaProjectApplication;

import java.util.ArrayList;
import java.util.List;

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
                .map(element -> ScopusJavaProjectApplication.GSON.fromJson(element, ScopusEntry.class))
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
