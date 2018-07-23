package com.fynes.als.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class FileDTO implements Serializable {

    @NotNull
    private String name;
    @NotNull
    private String id;
    @NotNull
    private String url;

    public FileDTO(String name, String id, String url) {
        this.name = name;
        this.id = id;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Objects.equals(name, fileDTO.name) &&
                Objects.equals(id, fileDTO.id) &&
                Objects.equals(url, fileDTO.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, url);
    }
}
