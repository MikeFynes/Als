package com.fynes.als.model;

import com.google.api.services.drive.model.File;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileCollection implements Serializable {

    private List<FileDTO> files;

    public FileCollection() {
        this.files = new ArrayList<>();
    }

    public FileCollection(List<FileDTO> files) {
        this.files = files;
    }

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileCollection that = (FileCollection) o;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {

        return Objects.hash(files);
    }
}
