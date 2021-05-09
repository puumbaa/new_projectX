package ru.itis.new_project.transfer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.new_project.models.Person;

// Преобразует Person к виду, пригодному для отправки на личную страницу
@Data
@AllArgsConstructor
@Builder
public class PersonDto {
    private String name;
    private String surname;
    private String contacts;

    public static PersonDto from(Person person){
        return PersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .contacts(person.getContacts())
                .build();
    }
}
