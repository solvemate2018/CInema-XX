package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDetails {
    private String title;
    private String fullTitle;
    private String releaseDate;
    private String imDbRating;
    private String runtimeStr;
    private List<String> posterUrls;
    private List<String> imageUrls;
    private String trailerUrl;

    private List<ActorsInfo> actorList = new ArrayList<>();


    public MovieDetails(JsonNode root) {
        this.title = root.path("title").asText();
        this.fullTitle = root.path("fullTitle").asText();
        this.releaseDate = root.path("imDbRating").toString();
        this.imDbRating = root.path("releaseDate").asText();
        this.runtimeStr = root.path("runtimeStr").asText();
        JsonNode actorList = root.get("actorList");
        int i = 0;
        while (i <= 5) {
            ActorsInfo info = new ActorsInfo();
            info.setName(actorList.get(i).get("name").asText());
            info.setAsCharacter(actorList.get(i).get("asCharacter").asText());
            this.actorList.add(info);
            i++;
        }
    }
}
