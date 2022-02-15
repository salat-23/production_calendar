package com.example.demo.component;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StorageComponent {

    private final Path storageLocation;
    private final String storageLocationString = "./documents";


    //Initializing storageLocation and creating necessary directories;
    public StorageComponent() throws IOException {
        this.storageLocation = Paths.get(storageLocationString).toAbsolutePath().normalize();
        if (Files.exists(storageLocation)) return;
        Files.createDirectory(this.storageLocation);
    }

    public Path getStorageLocation() {
        return storageLocation;
    }
}
