package com.fynes.als.model;

import java.io.Serializable;
import java.util.Objects;

public class FileDTO implements Serializable {

    private String name;
    private String id;
    private String webViewLink;

    public FileDTO() {
    }

    public FileDTO(String name, String id, String webViewLink) {
        this.name = name;
        this.id = id;
        this.webViewLink = webViewLink;
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

    public String getWebViewLink() {
        return webViewLink;
    }

    public void setWebViewLink(String webViewLink) {
        this.webViewLink = webViewLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Objects.equals(name, fileDTO.name) &&
                Objects.equals(id, fileDTO.id) &&
                Objects.equals(webViewLink, fileDTO.webViewLink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, webViewLink);
    }
}
