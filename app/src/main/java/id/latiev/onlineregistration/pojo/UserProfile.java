package id.latiev.onlineregistration.pojo;

/**
 * Created by latiev on 4/11/17.
 */

public class UserProfile {

    private String id;
    private String text;
    private String name;
    private String photoUrl;
    private String imageUrl;
    private String email;

    public UserProfile(){}

    public UserProfile(String name, String photoUrl, String email) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
    }

    public UserProfile(String text, String name, String photoUrl, String imageUrl) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }

    public UserProfile(String text, String name, String photoUrl, String imageUrl, String email) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
