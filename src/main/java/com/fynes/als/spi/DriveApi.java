package com.fynes.als.spi;

import com.fynes.als.Constants;
import com.fynes.als.model.Echo;
import com.fynes.als.model.FileCollection;
import com.fynes.als.model.FileDTO;
import com.fynes.als.service.DriveService;
import com.fynes.als.service.DriveServiceImpl;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.services.drive.model.File;
import com.google.auth.oauth2.AccessToken;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.google.api.services.drive.DriveScopes.*;

@Api(name = "driveTesting", version = "v1", scopes = { Constants.EMAIL_SCOPE, DRIVE, DRIVE_APPDATA, DRIVE_FILE, DRIVE_METADATA}, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for testing endpoints with google drive")
public class DriveApi {

    @ApiMethod(name="echoWithName", path = "echo", httpMethod = HttpMethod.GET)
    public Echo echoWithName(@Named("name") String name){
        return new Echo(name);
    }

    @ApiMethod(name="driveFiles", path="files", httpMethod = HttpMethod.GET)
    public List<File> driveFiles() throws IOException, GeneralSecurityException, UnauthorizedException {

        DriveService driveService = new DriveServiceImpl();
            return driveService.findAllOwnedAndShared();
    }

    @ApiMethod(name="saveDriveFiles", path="save", httpMethod = HttpMethod.PUT)
    public List<FileDTO> saveFiles(FileCollection files){

        System.out.println("Files saved!");


        return null;
    }
}
