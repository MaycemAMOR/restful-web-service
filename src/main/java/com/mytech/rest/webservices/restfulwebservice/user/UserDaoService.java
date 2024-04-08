package com.mytech.rest.webservices.restfulwebservice.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();
    private static int userCount = 0;

    static {
        users.add(new User(userCount++, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(userCount++, "Eve", LocalDate.now().minusYears(25)));
        users.add(new User(userCount++, "Jim", LocalDate.now().minusYears(20)));
        users.add(new User(userCount++, "kim", LocalDate.now().minusYears(15)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        Predicate<User> userPredicate = user -> user.getId().equals(id);
        return users.stream().filter(userPredicate).findFirst().get();
    }

    public User saveUser(User user) {
        user.setId(userCount++);
        users.add(user);
        return user;
    }
}
