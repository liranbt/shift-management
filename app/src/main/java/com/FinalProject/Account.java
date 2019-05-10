package com.FinalProject;

public class Account {
    private String email, password;
    private Boolean isActive, isAdmin;
    private Long id;
    private String image;

    public Account(){}

    public Account(String email, String password, Boolean isActive, Boolean isAdmin, Long id, String image) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.id = id;
        this.image = image;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public Boolean getIsAdmin(){
        return isAdmin;
    }

    public String getImage(){
        return image;
    }

}
