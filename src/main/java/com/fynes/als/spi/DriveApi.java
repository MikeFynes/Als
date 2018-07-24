package com.fynes.als.spi;

import com.fynes.als.Constants;
import com.fynes.als.model.Echo;
import com.fynes.als.model.FileCollection;
import com.fynes.als.service.DriveService;
import com.fynes.als.service.DriveServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Api(name = "driveTesting", version = "v1", scopes = {}, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for testing endpoints with google drive")
public class DriveApi {

    @ApiMethod(name="echoWithName", path = "echo", httpMethod = HttpMethod.GET)
    public Echo echoWithName(@Named("name") String name){
        return new Echo(name);
    }

    @ApiMethod(name="driveFiles", path="files", httpMethod = HttpMethod.GET)
    public FileCollection driveFiles() {
        DriveService driveService = new DriveServiceImpl(getConnection());
        return driveService.getFiles();
    }

    @ApiMethod(name="saveDriveFiles", path="save", httpMethod = HttpMethod.PUT)
    public void saveFiles(FileCollection files){
        DriveService driveService = new DriveServiceImpl(getConnection());
        driveService.saveFiles(files);
    }


    private Connection getConnection(){
        String url = System.getProperty("cloudsql");
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to Cloud SQL", e);
        }
    }
}
