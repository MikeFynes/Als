package com.fynes.als.service;


import com.fynes.als.model.FileCollection;
import com.fynes.als.model.FileDTO;
import com.google.common.collect.ImmutableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriveServiceImpl implements DriveService {

    private Connection connection;

    public DriveServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveFiles(FileCollection fileCollection) {
        fileCollection.getFiles().forEach(file -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO file_entity(name, drive_id, url) VALUES (?, ?, ?)");
                preparedStatement.setString(1, file.getName());
                preparedStatement.setString(2, file.getId());
                preparedStatement.setString(3, file.getWebViewLink());

                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public FileCollection getFiles() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM file_entity");
            ResultSet resultSet = preparedStatement.executeQuery();
            ImmutableList.Builder<FileDTO> builder = new ImmutableList.Builder<>();
            while(resultSet.next()){
                FileDTO fileDTO = new FileDTO(
                        resultSet.getString("name"),
                        resultSet.getString("drive_id"),
                        resultSet.getString("url")
                );

                builder.add(fileDTO);
            }

            return new FileCollection(builder.build());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
