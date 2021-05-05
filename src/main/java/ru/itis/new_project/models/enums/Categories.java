package ru.itis.new_project.models.enums;

public enum Categories {
    SPORT("Спортивное"),
    BOARD_GAME("Настольные игры"),
    DEVELOPMENT("Развитие"),
    LEISURE("Активный отдых"),
    PARTY("Тусовка"),
    SOCIAL("Социальное"),
    OTHER("Другое");

    public String getTitle() {
        return title;
    }

    private String title;

    Categories(String title) {
        this.title = title;
    }

}