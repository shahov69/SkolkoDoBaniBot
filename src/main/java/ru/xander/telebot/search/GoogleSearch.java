package ru.xander.telebot.search;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.xander.telebot.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Builder
public class GoogleSearch {
    private final String applicationName;
    private final String apiKey;
    private final String engineKey;
    @Builder.Default
    private final int httpRequestTimeout = 5000;

    private Customsearch customsearch;

    private Customsearch getCustomsearch() {
        if (customsearch == null) {
            customsearch = new Customsearch.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    httpRequest -> {
                        httpRequest.setConnectTimeout(httpRequestTimeout);
                        httpRequest.setReadTimeout(httpRequestTimeout);
                    })
                    .setApplicationName(applicationName)
                    .build();
        }
        return customsearch;
    }

    public GoogleSearchResult search(String query, long start) {
        try {
            log.debug("Search for '{}' (start: {})", query, start);
            Search search = getCustomsearch().cse()
                    .list(query)
                    .setKey(apiKey)
                    .setCx(engineKey)
                    .setSearchType("image")
//                .setImgSize("large")
                    .setStart(start)
                    .execute();

            List<Result> items = search.getItems();

            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    Result item = Utils.randomList(items);
                    log.debug("Extract {} (attempt: {})", item.getLink(), attempt + 1);
                    return extract(item.getLink());
                } catch (GoogleSearchException e) {
                    log.error(e.toString(), e);
                }
            }
            throw new GoogleSearchException("Cannot extract result after 3 attempts");
        } catch (Exception e) {
            throw new GoogleSearchException(e.getMessage(), e);
        }
    }

    private GoogleSearchResult extract(String urlString) {
        if (urlString.contains("gifer.com")) {
            throw new GoogleSearchException("Unsupported url: " + urlString);
        }

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            // TODO: отбрасывать слишком большие картинки (см. content-length)

            String contentType = urlConnection.getHeaderField("content-type");
            switch (contentType) {
                case "image/jpeg":
                    return new GoogleSearchResult(GoogleSearchResult.ContentType.JPG, urlConnection.getInputStream());
                case "image/png":
                case "image/apng":
                    return new GoogleSearchResult(GoogleSearchResult.ContentType.PNG, urlConnection.getInputStream());
                case "image/bmp":
                    return new GoogleSearchResult(GoogleSearchResult.ContentType.BMP, urlConnection.getInputStream());
                case "image/gif":
                    return new GoogleSearchResult(GoogleSearchResult.ContentType.GIF, urlConnection.getInputStream());
                default:
                    if (contentType.contains("text/html")) {
                        return processAnimation(urlConnection, urlString);
                    } else {
                        throw new GoogleSearchException("Unsupported content type: " + contentType);
                    }
            }
        } catch (GoogleSearchException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleSearchException("Cannot extract from " + urlString + ", caused by: " + e.getMessage(), e);
        }
    }

    private GoogleSearchResult processAnimation(URLConnection urlConnection, String urlString) throws IOException {
        try (InputStream input = urlConnection.getInputStream()) {
            Document document = Jsoup.parse(input, "UTF-8", urlString);
            Elements video = document.getElementsByAttributeValue("property", "og:video");
            if (!video.isEmpty()) {
                String mp4UrlString = video.first().attr("content");
                URL mp4Url = new URL(mp4UrlString);
                return new GoogleSearchResult(GoogleSearchResult.ContentType.MP4, mp4Url.openStream());
            }
            Elements gif = document.getElementsByAttributeValue("property", "og:url");
            if (!gif.isEmpty()) {
                String gifUrlString = gif.first().attr("content");
                URL gifUrl = new URL(gifUrlString);
                return new GoogleSearchResult(GoogleSearchResult.ContentType.GIF, gifUrl.openStream());
            }
            throw new GoogleSearchException("Unrecognized content");
        }
    }
}
