package com.fynes.als.service;

import com.fynes.als.model.FileCollection;
import com.google.api.server.spi.auth.common.User;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.List;

public interface DriveService {

    void saveFiles(FileCollection fileCollection);

    FileCollection getFiles();

}
