package us.elite.scopus;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

import static us.elite.Application.GSON;

@Getter
public class ScopusEntry {

    @SerializedName("prism:url")
    private String url;

    @SerializedName("dc:identifier")
    private String identifier;

    @SerializedName("eid")
    private String electronicIdentifier;

    @SerializedName("dc:title")
    private String title;

    @SerializedName("dc:creator")
    private String creator;

    @SerializedName("prism:publicationName")
    private  String publicationName;

    @SerializedName("prism:issn")
    private  long internationStandardSerialNumber;

    @SerializedName("prism:eIssn")
    private  long electronicInternationStandardSerialNumber;

    @SerializedName("prism:volume")
    private  int volume;

    @SerializedName("prism:coverDate")
    private  String coverDate;

    @SerializedName("prism:coverDisplayDate")
    private  String coverDisplayDate;

    @SerializedName("prism:doi")
    private  String digitalObjectIdentifier;

    @SerializedName("citedby-count")
    private long citedByCount;

    @SerializedName("pubmed-id")
    private long pubmedId;

    @SerializedName("prism:aggregationType")
    private String aggregationType;

    private  String subtype;

    private  String subtypeDescription;

    @SerializedName("article-number")
    private  String articleNumber;

    @SerializedName("source-id")
    private  long sourceId;

    @Getter(AccessLevel.NONE)
    private  JsonArray link;

    @Getter(AccessLevel.NONE)
    private  JsonArray affiliation;

    @Getter(AccessLevel.NONE)
    private final List<ScopusLink> LINKS = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    private final List<ScopusAffiliate> AFFILIATES = new ArrayList<>();

    public List<ScopusLink> links() {
        if (LINKS.isEmpty() && link != null && !link.isEmpty()) {
            link.asList()
                    .stream()
                    .map(element -> GSON.fromJson(element, ScopusLink.class))
                    .forEach(LINKS::add);
        }
        return LINKS;
    }

    public List<ScopusAffiliate> affiliates() {
        if (AFFILIATES.isEmpty() && affiliation != null && !affiliation.isEmpty())
                affiliation.asList()
                .stream()
                .map(element ->  GSON.fromJson(element, ScopusAffiliate.class))
                .forEach(AFFILIATES::add);
        return AFFILIATES;
    }

    @Override
    public String toString() {
        return "ScopusEntry{" +
                "url='" + url + '\'' +
                ", identifier='" + identifier + '\'' +
                ", electronicIdentifier='" + electronicIdentifier + '\'' +
                ", title='" + title + '\'' +
                ", creator='" + creator + '\'' +
                ", publicationName='" + publicationName + '\'' +
                ", internationStandardSerialNumber=" + internationStandardSerialNumber +
                ", electronicInternationStandardSerialNumber=" + electronicInternationStandardSerialNumber +
                ", volume=" + volume +
                ", coverDate='" + coverDate + '\'' +
                ", coverDisplayDate='" + coverDisplayDate + '\'' +
                ", digitalObjectIdentifier='" + digitalObjectIdentifier + '\'' +
                ", citedByCount=" + citedByCount +
                ", pubmedId=" + pubmedId +
                ", aggregationType='" + aggregationType + '\'' +
                ", subtype='" + subtype + '\'' +
                ", subtypeDescription='" + subtypeDescription + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", sourceId=" + sourceId +
                ", LINKS=" + links() +
                ", AFFILIATES=" + affiliates() +
                '}';
    }
}
