package ru.itis.new_project.dao;

import ru.itis.new_project.models.Person;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoJdbcImpl implements PersonDao {
    private Connection connection;

    //language=sql
    private final String SQL_SELECT_ALL = "SELECT * FROM person";
    //language=sql
    private final String SQL_SELECT_ALL_BY_ID = "SELECT * FROM person WHERE id = ?";

    public PersonDaoJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Person> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String about = resultSet.getString("about");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                return Optional.of(Person.builder()
                        .id(id)
                        .name(name)
                        .surname(surname)
                        .about(about)
                        .email(email)
                        .password(password)
                        .build());
            }
            return Optional.empty();
        }
        catch(SQLException er){
            throw new IllegalStateException(er);
        }
    }



    @Override
    public void save(Person model) {

    }

    @Override
    public void update(Person model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Person> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()){
                List<Person> personList = new ArrayList<>();
                Integer id  = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String about = resultSet.getString("about");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Person person = Person.builder()
                        .id(id)
                        .name(name)
                        .surname(surname)
                        .about(about)
                        .email(email)
                        .password(password)
                        .build();
                personList.add(person);

            }
            return null;
        } catch (SQLException er) {
            throw new IllegalStateException(er);
        }
    }

    @Override
    public List<Person> findAllByName(String name) {
        return null;
    }
}
