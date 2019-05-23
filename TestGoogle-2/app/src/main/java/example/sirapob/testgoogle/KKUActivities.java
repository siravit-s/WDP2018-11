package example.sirapob.testgoogle;

public class KKUActivities {
    private String title;
    private String image;
    private String place;
    private String dateSt;
    private String link;

    public KKUActivities() {

    }

    public KKUActivities(String title, String image, String place, String dateSt, String link) {
        this.title = title;
        this.image = image;
        this.place = place;
        this.dateSt = dateSt;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateSt() {
        return dateSt;
    }

    public void setDateSt(String dateSt) {
        this.dateSt = dateSt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}