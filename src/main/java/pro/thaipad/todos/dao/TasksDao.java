package pro.thaipad.todos.dao;

import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;

import java.util.List;

public interface TasksDao {

    int insertTask(Task task, User owner);

    void deleteTaskById(int id, User owner);

    void deleteTask(Task task, User owner);

    void updateTask(Task task, User owner);

    Task getTaskById(int id, User owner);

    List<Task> getTasks(boolean onlyActive, User owner);

    int countTask(boolean onlyActive, User owner);

    User findUserByEmail(String email);

}
