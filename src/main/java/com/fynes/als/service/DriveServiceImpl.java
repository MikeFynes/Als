package com.fynes.als.service;

import com.google.api.client.googleapis.apache.GoogleApacheHttpTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.apache.ApacheHttpTransport;
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
import java.util.Set;
import java.util.stream.Collectors;

public class DriveServiceImpl implements DriveService {
    private static final String APPLICATION_NAME = "Als Drive interactions";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required
     */
    private static final Set<String> SCOPES = DriveScopes.all();

    @Override
    public List<File> findAllOwnedAndShared() throws GeneralSecurityException, IOException {
        ApacheHttpTransport HTTP_TRANSPORT = GoogleApacheHttpTransport.newTrustedTransport();

        AppIdentityCredential credential = new AppIdentityCredential(SCOPES);

        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
//                .setFields("files(id, name, ownedByMe, shared, permissionIds)")
                .execute();

        if(result.getFiles().size() < 1){
            throw new RuntimeException("No results!");
        }

        List<File> filteredList = result.getFiles().stream().filter(f -> {
            boolean owned = f.getOwnedByMe() != null ? f.getOwnedByMe() : false;
//            boolean shared = f.getPermissionIds() != null ?  f.getPermissionIds().size() > 1 : false;
            return true; // && shared;

        }).collect(Collectors.toList());

        return filteredList;
    }
}
