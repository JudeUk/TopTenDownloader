package com.JukanaCodes.toptendownloader;

public class FeedResponseEntry {

    private String name;
    private String imageUrl;
    private String releaseDate;
    private String summary;
    private String artist;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

//    @Override
//    public String toString() {
//        return
//                "name=" + name + '\n' +
//                ", imageUrl=" + imageUrl + '\n' +
//                ", releaseDate=" + releaseDate + '\n' +
//                ", artist=" + artist + '\n';
//    }
}
