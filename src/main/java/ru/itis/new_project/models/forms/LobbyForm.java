package ru.itis.new_project.models.forms;

import lombok.Data;
import ru.itis.new_project.models.enums.Categories;

import java.time.LocalDate;

@Data
public class LobbyForm {
    private String eventName;
    private String brieflyInfo;
    public String category;
    private String date;
    private Integer countOfMembers;
    private Integer capacity;
    private String aboutEvent;
    private String chatLink;
    private boolean actual;
    private boolean isFull;
}
