package app.actionnation.actionapp.Database_Content;

public class Product_Description
{
        int Product_id;
        String note;
        int status;
        String created_at;

        // constructors
        public Product_Description() {
        }

        public Product_Description(String note, int status) {
            this.note = note;
            this.status = status;
        }

        public Product_Description(int id, String note, int status) {
            this.Product_id = id;
            this.note = note;
            this.status = status;
        }

        // setters
        public void setId(int id) {
            this.Product_id = id;
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
            return this.Product_id;
        }

        public String getNote() {
            return this.note;
        }

        public int getStatus() {
            return this.status;
        }
}
