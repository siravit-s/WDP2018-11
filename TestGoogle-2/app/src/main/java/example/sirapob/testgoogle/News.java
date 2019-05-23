package example.sirapob.testgoogle;

public class News {
    private String name;
    private String image;
    private String detail;
    private String date;
    private String link;

    public News() {

    }

    public News(String name, String image, String detail, String date, String link) {
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.date = date;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
