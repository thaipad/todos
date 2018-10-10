package pro.thaipad.todos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.thaipad.todos.dao.TasksDao;
import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;

import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    private TasksDao tasksDao;

    @Autowired
    private User loginUser;

    @Override
    public User getLoginUser() {
        return loginUser;
    }

    public boolean registeringUser(User user) {
        if (findUser(user)) {
            loginUser.copy(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean findUser(User user) {
        User foundUser = findUserByEmail(user.getEmail());
        if (foundUser != null && foundUser.getId() != 0) {
            user.copy(foundUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return tasksDao.findUserByEmail(email);
    }

    @Override
    public List<Task> getActiveTask() {
        return tasksDao.getTasks(true, loginUser);
    }

    @Override
    public List<Task> getAllTask() {
        return tasksDao.getTasks(false, loginUser);
    }

}
