package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto;

public class DiscussionCreateDto {
    private String title;
    private String content;
    private Long userId;
    private String category;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public Long getUserId() { return userId; }
}
