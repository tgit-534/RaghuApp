package app.actionnation.actionapp.Database_Content;

public class Product_Category {
    int id;
    String note;
    int status;
    String created_at;

    // constructors
    public Product_Category() {
    }

    public Product_Category(String note, int status) {
        this.note = note;
        this.status = status;
    }

    public Product_Category(int id, String note, int status) {
        this.id = id;
        this.note = note;
        this.status = status;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getNote() {
        return this.note;
    }

    public int getStatus() {
        return this.status;
    }
}
