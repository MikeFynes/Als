package com.fynes.als.service;

import com.google.api.client.googleapis.apache.GoogleApacheHttpTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

public class DriveServiceImpl implements DriveService {
    private static final String APPLICATION_NAME = "Als Drive interactions";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required
     */
    private static final List<String> SCOPES = Lists.newArrayList(DriveScopes.DRIVE);

    @Override
    public List<File> findAllOwnedAndShared() throws GeneralSecurityException, IOException {
        ApacheHttpTransport HTTP_TRANSPORT = GoogleApacheHttpTransport.newTrustedTransport();
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        AppIdentityCredential credential = new AppIdentityCredential(SCOPES);

        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .execute();

        System.out.print("Found some!");
        List<File> filteredList = result.getFiles().stream().filter(f -> {

            boolean owned = f.getOwnedByMe() != null ? f.getOwnedByMe() : false;
            boolean shared = f.getShared() != null ? f.getShared() : false;

            return owned && shared;
        }).collect(Collectors.toList());

        if (filteredList == null || filteredList.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : filteredList) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }

        return filteredList;
    }
}
