package com.example.demo.service;

import com.example.demo.component.StorageComponent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DownloadResourceService {

    private final String HTML_PAGE_RESOURCE = "https://data.gov.ru/opendata/7708660670-proizvcalendar";
    private final Path documentStoragePath;
    private final Pattern PATTERN =
            Pattern.compile("(https://data\\.gov\\.ru/opendata/7708660670-proizvcalendar/data-)([0-9A-Z]*)(-structure-)([0-9A-Z]*)(\\.csv\\?encoding=UTF-8)");


    public DownloadResourceService (StorageComponent storageComponent) throws IOException {
        this.documentStoragePath = storageComponent.getStorageLocation();
    }

    public String getLatestFileURL() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(HTML_PAGE_RESOURCE))
                    .GET()
                    .build();
            String documentString = client.send(request,
                    HttpResponse.BodyHandlers.ofString()).body();
            Matcher matcher = PATTERN.matcher(documentString);
            String url;
            if (matcher.find()) {
                url = matcher.group();
                return url;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLatestFilePath() throws IOException {

        String fileUrl = getLatestFileURL();

        InputStream fileStream = new URL(fileUrl).openStream();

        String path = storeDocument(fileStream, "somefile.csv");

        return path;
    }

    private String storeDocument(InputStream stream, String name) throws IOException {
        Path targetLocation = this.documentStoragePath.resolve(name);
        Files.deleteIfExists(targetLocation);
        Files.copy(stream, targetLocation);
        return targetLocation.toString();
    }

}
