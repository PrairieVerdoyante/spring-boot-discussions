package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto;

public class DiscussionDto {
    private String title;
    private String content;
    private String category;
    private UserDTO author;
    private Long id;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public UserDTO getAuthor() { return author; }
    public Long getUserId() { return id; };
}
