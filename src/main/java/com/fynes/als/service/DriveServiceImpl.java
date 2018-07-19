package com.fynes.als.service;


import com.fynes.als.model.FileCollection;
import com.fynes.als.model.FileDTO;
import com.fynes.als.model.FileEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.stream.Stream;

public class DriveServiceImpl implements DriveService {

    private SessionFactory sessionFactory;

    public DriveServiceImpl() {
        this.sessionFactory = new Configuration().configure("hibernate-config.xml").buildSessionFactory();
    }

    @Override
    public void saveFiles(FileCollection fileCollection) {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();

        Stream<FileEntity> fileEntityStream = fileCollection.getFiles().stream().map(FileDTO::toEntity);



        session.flush();
        session.close();

    }

    @Override
    public FileCollection getFiles() {
        return null;
    }
}
