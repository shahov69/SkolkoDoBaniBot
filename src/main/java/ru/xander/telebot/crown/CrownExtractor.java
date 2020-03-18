package ru.xander.telebot.crown;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;
import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Shakhov
 */
public class CrownExtractor {

    //    private static final String DATASOURCE = "https://en.wikipedia.org/wiki/2019%E2%80%9320_Wuhan_coronavirus_outbreak_by_country_and_territory";
    private static final String DATASOURCE = "https://en.wikipedia.org/wiki/2019%E2%80%9320_Wuhan_coronavirus_outbreak";
    private static final int TIMEOUT_MILLIS = 10000;
    private final ConcurrentHashMap<String, Image> flagsCache = new ConcurrentHashMap<>();

    private FlagExtractor flagExtractor;

    /**
     * Метод для тестов
     */
    void setFlagExtractor(FlagExtractor flagExtractor) {
        this.flagExtractor = flagExtractor;
    }

    public Crown extract() {
        try {
            Document document = Jsoup.parse(new URL(DATASOURCE), TIMEOUT_MILLIS);
            return extract(document);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Crown extract(InputStream inputStream) {
        try {
            Document document = Jsoup.parse(inputStream, "UTF-8", "");
            return extract(document);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Crown extract(Document document) {
        try {
            if (flagExtractor == null) {
                flagExtractor = new FlagExtractorImpl();
            }
            Crown crown = new Crown();
            List<Crown.Region> regions = new ArrayList<>();
            Element table = document.getElementsByAttributeValueStarting("class", "wikitable").first();
            boolean first = true;
            for (Element tr : table.getElementsByTag("tr")) {
                if (first) {
                    // скипаем первую строку
                    first = false;
                    continue;
                }
                if (StringUtils.isEmpty(tr.attr("class"))) {
                    Crown.Region region = new Crown.Region();
                    int item = 0;
                    for (Element td : tr.children()) {
                        if (!"td".equals(td.tagName()) && !"th".equals(td.tagName())) {
                            continue;
                        }
                        if (item == 0) {
                            Element flagElement = td.getElementsByTag("img").first();
                            if (flagElement == null) {
                                // скипаем территорию без флага
                                break;
                            }
                            region.setFlag(flagExtractor.extractFlag(flagElement));
                        } else if (item == 1) {
                            region.setName(getName(td));
                        } else if (item == 2) {
                            region.setConfirmed(parseInteger(td));
                        } else if (item == 3) {
                            region.setDeaths(parseInteger(td));
                        } else if (item == 4) {
                            region.setRecoveries(parseInteger(td));
                            break;
                        }
                        item++;
                    }
                    if (region.getName() != null) {
                        regions.add(region);
                    }
                }
            }
            regions.sort((r1, r2) -> {
                int compareConfirmed = Utils.compareInteger(r2.getConfirmed(), r1.getConfirmed());
                if (compareConfirmed != 0) {
                    return compareConfirmed;
                }
                int compareDeath = Utils.compareInteger(r2.getDeaths(), r1.getDeaths());
                if (compareDeath != 0) {
                    return compareDeath;
                }
                return Utils.compareInteger(r2.getRecoveries(), r1.getConfirmed());
            });
            crown.setRegions(regions);
            return crown;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String getName(Element td) {
        String name = td.text()
//                .replace(" ", Utils.EMPTY_STRING)
                .replace("&nbsp;", " ")
                .replaceAll("(\\[(.*?)\\]|\\((.*?)\\))", Utils.EMPTY_STRING)
                .trim();
        if (name.toUpperCase().contains("DIAMOND")) {
            return "Diamond Princess";
        }
        return name;
    }

    private static Integer parseInteger(Element td) {
        try {
            String value = td.text().replaceAll("[^\\d]", Utils.EMPTY_STRING).trim();
            if (StringUtils.isEmpty(value)) {
                return 0;
            }
            int space = value.indexOf(' ');
            if (space > 0) {
                return Integer.parseInt(value.substring(0, space).trim());
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    interface FlagExtractor {
        Image extractFlag(Element flagElement) throws IOException;
    }

    private class FlagExtractorImpl implements FlagExtractor {
        @Override
        public Image extractFlag(Element flagElement) throws IOException {
            if (flagElement != null) {
                String imgUrl = flagElement.attr("src");
                if (imgUrl.startsWith("//")) {
                    imgUrl = "https:" + imgUrl;
                }
                if (flagsCache.containsKey(imgUrl)) {
                    return flagsCache.get(imgUrl);
                } else {
                    Image flag = ImageIO.read(new URL(imgUrl).openStream());
                    flagsCache.put(imgUrl, flag);
                    return flag;
                }
            }
            return null;
        }
    }
}
