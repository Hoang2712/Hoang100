package dev.edmt.android_app.model;

public class BannerCoffee {
    Integer id;
    String coffeName;
    String imageUrl;
    String fileUrl;

    public BannerCoffee(Integer id, String coffeName, String imageUrl, String fileUrl) {
        this.id = id;
        this.coffeName = coffeName;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getCoffeName() {
        return coffeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
