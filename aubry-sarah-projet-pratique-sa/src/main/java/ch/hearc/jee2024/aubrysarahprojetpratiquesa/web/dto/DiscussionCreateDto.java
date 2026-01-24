package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto;

public class DiscussionCreateDto {

    private String title;
    private String content;
    private String category;

    public DiscussionCreateDto() {
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
