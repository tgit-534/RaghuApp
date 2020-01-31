package app.actionnation.actionapp.Database_Content;

import androidx.annotation.NonNull;

public class Category {
    String CategoryName;
    int Status;
    String Fb_Id;


    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }



    public String getFb_Id() {
        return Fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        Fb_Id = fb_Id;
    }



    // constructors
    public Category() {
    }



    public Category(String Category_Name, int status) {

        this.CategoryName = Category_Name;
        this.Status = status;
    }

    public Category(String Fb_id, String Category_Name, int status) {
        this.Fb_Id = Fb_id;
        this.CategoryName = Category_Name;
        this.Status = status;
    }

    // setters



    public void setStatus(int status) {
        this.Status = status;
    }



    public int getStatus() {
        return this.Status;
    }

    @NonNull
    @Override
    public String toString() {
        return CategoryName;
    }
}
