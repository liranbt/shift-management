package com.FinalProject;

public class Child {
    private String image;
    private String userName;
    private boolean isDeleteButtonClicked;
    private Long id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isDeleteButtonClicked() {
        return isDeleteButtonClicked;
    }

    public void setDeleteButtonClicked(boolean deleteClicked) {
        isDeleteButtonClicked = deleteClicked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
