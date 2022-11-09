package quangnvph25768.poly.demothiandroidnc;

public class BaiBao {
    int id;
    String title,link;

    public BaiBao(int id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public BaiBao(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public BaiBao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
