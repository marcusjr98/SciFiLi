package library;

public class Book {
    private String name;
    private String author;
    private int status;
    private int importance;
	
	// getter and setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

	// getter and setter for author
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
	
	// getter and setter for status
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

	// getter and setter for importance
    public int getImportance() {
        return importance;
    }
    public void setImportance(int importance) {
        this.importance = importance;
    }
}
