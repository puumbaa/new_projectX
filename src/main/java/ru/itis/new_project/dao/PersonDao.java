package ru.itis.new_project.dao;

import ru.itis.new_project.models.Person;

import java.util.List;

public interface PersonDao extends CrudDao<Person> {
    List<Person> findAllByName(String name);

}
