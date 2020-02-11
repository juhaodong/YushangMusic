package us.haodongandyushang.yushangmusic.Models;


import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SearchResult {
    private String videoId, title, description, publishedAt, channelId, channeTitle;
    private Thumbnails thumbnail;

    public SearchResult(String videoId, String title, String description, String publishedAt, String channelId, String channeTitle, Thumbnails thumbnail) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.channeTitle = channeTitle;
        this.thumbnail = thumbnail;
    }

    public SearchResult(JsonObject j) throws JsonParseException {
        this.videoId = j.get("id").getAsJsonObject().get("videoId").getAsString();
        JsonObject snippet = j.get("snippet").getAsJsonObject();
        this.title = snippet.get("title").getAsString();
        this.description = snippet.get("description").getAsString();
        this.publishedAt = snippet.get("publishedAt").getAsString();
        this.channelId = snippet.get("channelId").getAsString();
        this.channeTitle = snippet.get("channelTitle").getAsString();
        this.thumbnail = new Thumbnails(snippet.get("thumbnails").getAsJsonObject().get("high").
                getAsJsonObject().get("url").getAsString());
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChanneTitle() {
        return channeTitle;
    }

    public void setChanneTitle(String channeTitle) {
        this.channeTitle = channeTitle;
    }

    public Thumbnails getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnails thumbnail) {
        this.thumbnail = thumbnail;
    }

    public class Thumbnails {
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String url;

        public Thumbnails(String url) {
            this.url = url;
        }
    }
}
