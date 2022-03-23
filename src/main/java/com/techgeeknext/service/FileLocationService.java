package com.techgeeknext.service;

import com.techgeeknext.repository.FileSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileLocationService {
    @Autowired
    FileSystemRepository fileSystemRepository;


    public String save(byte[] bytes, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, imageName);

        return location;
    }

    public FileSystemRepository getFileSystemRepository() {
        return fileSystemRepository;
    }

    public boolean deleteFile(String location) throws IOException {
        return fileSystemRepository.deleteFile(location);
    }


}
