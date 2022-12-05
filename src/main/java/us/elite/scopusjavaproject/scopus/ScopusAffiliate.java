package us.elite.scopusjavaproject.scopus;

import com.google.gson.annotations.SerializedName;

public record ScopusAffiliate(@SerializedName("affilname")
                              String affiliateName,

                              @SerializedName("affiliation-city")
                              String affiliationCity,

                              @SerializedName("affiliation-country")
                              String affiliationCountry) {

}
