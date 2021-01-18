package com.example.helper;

public class Items {
    private String title;
    private String channelTitle;
    private String imageUrl;
    private String playlistId;

    public Items(String title, String channelTitle, String imageUrl, String playlistId) {
        this.title = title;
        this.channelTitle = channelTitle;
        this.imageUrl = imageUrl;
        this.playlistId = playlistId;
    }

    public Items(String title, String channelTitle, String imageUrl) {
        this.title = title;
        this.channelTitle = channelTitle;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlaylistId() {
        return playlistId;
    }
}
