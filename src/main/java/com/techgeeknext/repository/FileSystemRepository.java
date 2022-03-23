package com.techgeeknext.repository;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {

    String RESOURCES_DIR = FileSystemRepository.class.getResource("/")
            .getPath().substring(1);


    public String save(byte[] content, String imageName) throws Exception {

        File theDir = new File(RESOURCES_DIR+"/images/");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        String q=new Date().getTime()  + imageName;
        String s=(RESOURCES_DIR+"/images/"+ q).trim();
        Path newFile = Paths.get(s);
        Files.createDirectories(newFile.getParent());
        Files.write(newFile, content);

        return q;
    }
    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }

    public boolean deleteFile(String location) throws IOException {
        Path myPath = Paths.get(RESOURCES_DIR+"/images/"+location);
        return  Files.deleteIfExists(myPath);
    }
}