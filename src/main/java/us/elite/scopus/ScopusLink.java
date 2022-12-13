package us.elite.scopus;

import com.google.gson.annotations.SerializedName;

public record ScopusLink(@SerializedName("@ref")
                         String reference,

                         @SerializedName("@href")
                         String link) {

    public boolean isValid() {
        return !link().isBlank() && !reference().isBlank();
    }

}
