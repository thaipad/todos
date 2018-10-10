package pro.thaipad.todos.services;

import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;

import java.util.List;

public interface TasksService {

    User getLoginUser();

    boolean registeringUser(User user);

    boolean findUser(User user);

    User findUserByEmail(String email);

    List<Task> getActiveTask();

    List<Task> getAllTask();
}
