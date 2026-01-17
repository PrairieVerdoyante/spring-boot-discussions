package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto;

public class AnswerCreateDto {

    private String content;

    public AnswerCreateDto() {}

    public AnswerCreateDto(String content) {
        this.content = content;
    }

    // getter / setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
