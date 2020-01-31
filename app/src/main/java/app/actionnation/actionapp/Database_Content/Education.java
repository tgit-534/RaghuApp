package app.actionnation.actionapp.Database_Content;

import androidx.annotation.NonNull;

public class Education {
    String Edu_name;
    int status;
    String created_at;
    String Fb_Id;
    String Category_ID;
    String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    // constructors
    public Education() {
    }


    public String getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(String category_ID) {
        Category_ID = category_ID;
    }

    public String getEdu_name() {
        return Edu_name;
    }

    public void setEdu_name(String edu_name) {
        Edu_name = edu_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFb_Id() {
        return Fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        Fb_Id = fb_Id;
    }

    @NonNull
    @Override
    public String toString() {
        return Edu_name;
    }

}
