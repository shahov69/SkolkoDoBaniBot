package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.xander.telebot.search.GoogleSearch;
import ru.xander.telebot.search.GoogleSearchResult;
import ru.xander.telebot.util.Utils;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Shakhov
 */
@Service
public class SearchService {

    private static final String[] DOBTO_QUERIES = {
            "Positive gif",
            "positive mental attitude gif",
            "gay unicorn pink",
            "gay unicorn pink gif",
            "house of air music video",
            "positive gif",
    };

    @Value("${google.applicationName}")
    private String applicationName;
    @Value("${google.apiKey}")
    private String googleApiKey;
    @Value("${google.engineKey}")
    private String googleEngineKey;
    private GoogleSearch googleSearch;

    @PostConstruct
    private void init() {
        googleSearch = GoogleSearch.builder()
                .applicationName(applicationName)
                .apiKey(googleApiKey)
                .engineKey(googleEngineKey)
                .build();
    }

    public GoogleSearchResult searchDobro() {
        for (int i = 0; i < 3; i++) {
            String query = Utils.randomArray(DOBTO_QUERIES);
            int start = Utils.randomInt(50);
            GoogleSearchResult searchResult = googleSearch.search(query, start);
            if (searchResult != null) {
                return searchResult;
            }
        }
        return null;
    }
}
