package com.fynes.als.service;

import com.fynes.als.model.FileCollection;

public interface DriveService {

    void saveFiles(FileCollection fileCollection);

    FileCollection getFiles();

}
