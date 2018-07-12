package com.fynes.als.spi;

import com.fynes.als.Constants;
import com.fynes.als.model.Echo;
import com.fynes.als.service.DriveService;
import com.fynes.als.service.DriveServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Api(name = "driveTesting", version = "v1", scopes = { Constants.EMAIL_SCOPE, DriveScopes.DRIVE_READONLY}, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for testing endpoints with google drive")
public class DriveApi {

    @ApiMethod(name="echoWithName", path = "echo", httpMethod = HttpMethod.GET)
    public Echo echoWithName(@Named("name") String name){
        return new Echo(name);
    }

    @ApiMethod(name="driveFiles", path="files", httpMethod = HttpMethod.GET)
    public List<File> driveFiles(HttpServletRequest req) throws IOException, GeneralSecurityException {


        DriveService driveService = new DriveServiceImpl();
            return driveService.findAllOwnedAndShared();
    }
}
