package com.future.cinemaxx.services.imdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.cinemaxx.dtos.ActorsInfo;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.MovieRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.PushBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImdbMovieServiceImpl implements ImdbMovieServiceInteface{
    @Value("${app.ImdbApiKey}")
    private String apiKey;

    MovieRepo movieRepo;
    ObjectMapper objectMapper;
    RestTemplate restTemplate;

    public ImdbMovieServiceImpl(MovieRepo movieRepo, ObjectMapper objectMapper){
        this.movieRepo = movieRepo;
        this.objectMapper = objectMapper;
        restTemplate = new RestTemplate();
    }

    @SneakyThrows
    @Override
    public List<String> getImagesForImdbId(String imdbId) {
        String getImagesUrl = "https://imdb-api.com/en/API/Images/" + apiKey + "/" + imdbId;
        ResponseEntity<String> imagesResponse = restTemplate.getForEntity(getImagesUrl, String.class);
        JsonNode imagesRoot = objectMapper.readTree(imagesResponse.getBody());
        JsonNode images = imagesRoot.get("items");
        List<String> urls = new ArrayList<>();
        int i = 0;
        while (i <= 5) {
            urls.add(images.get(i).get("image").asText());
            i++;
        }
        return urls;
    }

    @SneakyThrows
    @Override
    public String getTrailerForImdbId(String imdbId) {
        String getTrailersUrl = "https://imdb-api.com/en/API/Trailer/" + apiKey + "/" + imdbId;
        ResponseEntity<String> trailersResponse = restTemplate.getForEntity(getTrailersUrl, String.class);
        JsonNode trailersRoot = objectMapper.readTree(trailersResponse.getBody());
        String trailer = trailersRoot.get("link").asText();
        return trailer;
    }

    @SneakyThrows
    @Override
    public List<String> getPostersForImdbId(String imdbId) {
        String getPostersUrl = "https://imdb-api.com/en/API/Posters/" + apiKey + "/" + imdbId;
        ResponseEntity<String> getPostersResponse = restTemplate.getForEntity(getPostersUrl, String.class);
        JsonNode postersRoot = objectMapper.readTree(getPostersResponse.getBody());
        JsonNode posters = postersRoot.get("posters");
        List<String> urls = new ArrayList<>();
            int i = 0;
            while (i <= 5) {
                urls.add(posters.get(i).get("link").asText());
                i++;
            }
        return urls;
    }

    @Override
    public MovieDetails getMovieDetails(int movieId) throws JsonProcessingException {
        String imDbId = getImdbIdFromMovieId(movieId);

        String getDetailsUrl = "https://imdb-api.com/en/API/Title/" + apiKey + "/" + imDbId;
        ResponseEntity<String> detailsResponse = restTemplate.getForEntity(getDetailsUrl, String.class);
        JsonNode detailsRoot = objectMapper.readTree(detailsResponse.getBody());
        MovieDetails details = new MovieDetails(detailsRoot);
        details.setImageUrls(getImagesForImdbId(imDbId));
        details.setTrailerUrl(getTrailerForImdbId(imDbId));
        details.setPosterUrls(getPostersForImdbId(imDbId));
        return details;
    }

    @SneakyThrows
    @Override
    public String getImdbIdFromMovieId(int movieId) {
        Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException());
        String searchForMovieUrl = "https://imdb-api.com/en/API/Search/" + apiKey + "/" + movie.getName();
        ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchForMovieUrl, String.class);
        JsonNode searchRoot = objectMapper.readTree(searchResponse.getBody());
        JsonNode searchResults = searchRoot.get("results");
        return searchResults.get(0).get("id").asText();
    }
}
