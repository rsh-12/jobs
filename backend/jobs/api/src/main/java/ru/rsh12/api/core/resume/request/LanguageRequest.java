package ru.rsh12.api.core.resume.request;

public class LanguageRequest {

    private Integer id;
    private String level;

    public LanguageRequest() {
    }

    public LanguageRequest(Integer id, String level) {
        this.id = id;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public LanguageRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public LanguageRequest setLevel(String level) {
        this.level = level;
        return this;
    }
}
