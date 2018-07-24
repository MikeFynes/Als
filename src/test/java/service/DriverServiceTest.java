package service;

import com.fynes.als.model.FileCollection;
import com.fynes.als.model.FileDTO;
import com.fynes.als.service.DriveService;
import com.fynes.als.service.DriveServiceImpl;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DriverServiceTest {

    DriveService driveService;

    @Before
    public void init(){
        this.driveService = new DriveServiceImpl(getConnection());
    }


    @Ignore
    @Test
    public void saveFileEntityTest(){
        FileDTO fileDTO1 = new FileDTO("file_1", "id_1", "url_1");
        FileDTO fileDTO2 = new FileDTO("file_2", "id_2", "url_2");

        FileCollection fileCollection = new FileCollection(ImmutableList.of(fileDTO1, fileDTO2));
        saveFiles(fileCollection);

        FileCollection retrievedFiles = getFiles();

        assertEquals(fileCollection, retrievedFiles);
    }

    private FileCollection getFiles(){
        return this.driveService.getFiles();
    }

    private void saveFiles(FileCollection fileCollection){
        this.driveService.saveFiles(fileCollection);
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
