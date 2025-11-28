package repository;

import domain.Student;
import domain.Teacher;
import domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordRepository {

    private final Map<String, User> mapUserPass = new HashMap<>();

    public boolean addUser(String login, String password, User user) {
        if (mapUserPass.containsKey(login)) {
            return false;
        } else {
            mapUserPass.put(login, user);
            return true;
        }
    }

    public void removeUser(String login) {
        if (login != null) {
            mapUserPass.remove(login);
        }
    }

    public User getUserLogin(String login) {
        return mapUserPass.get(login);
    }


    public List<User> getAllUser() {
        return new ArrayList<>(mapUserPass.values());
    }


    public User getUserName(String name) {
        for (Map.Entry<String, User> entry : mapUserPass.entrySet()) {
            String key = entry.getKey();
            User user = entry.getValue();
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
