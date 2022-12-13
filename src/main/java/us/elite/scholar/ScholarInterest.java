package us.elite.scholar;

import com.google.gson.annotations.SerializedName;

public record ScholarInterest(
        String title,

        @SerializedName("serpapi_link")
        String serpApiLink,

        String link) {


}
