package org.example.restapi.model;

public class Jobs {
    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;
    private String how_to_apply;
    private String company_logo;

    public Jobs(String id, String type, String url, String created_at, String company, String company_url, String location, String title, String description, String how_to_apply, String company_logo) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.created_at = created_at;
        this.company = company;
        this.company_url = company_url;
        this.location = location;
        this.title = title;
        this.description = description;
        this.how_to_apply = how_to_apply;
        this.company_logo = company_logo;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCompany() {
        return company;
    }

    public String getCompany_url() {
        return company_url;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHow_to_apply() {
        return how_to_apply;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCompany_url(String company_url) {
        this.company_url = company_url;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHow_to_apply(String how_to_apply) {
        this.how_to_apply = how_to_apply;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }
}
