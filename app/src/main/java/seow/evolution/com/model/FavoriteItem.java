package seow.evolution.com.model;

/**
 * Created by Arthur on 7/7/2017.
 */

public class FavoriteItem {
    private int id;
    private int contentID;
    private String title;
    private int slideNo;

    public FavoriteItem() {
        id = 0;
        contentID = 0;
        title = "";
        slideNo = 0;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setContentID(int value) {
        this.contentID = value;
    }

    public int getContentID() {
        return contentID;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }

    public void setSlideNo(int value) {
        this.slideNo = value;
    }

    public int getSlideNo() {
        return slideNo;
    }

    @Override
    public String toString() {
        return title;
    }
}
