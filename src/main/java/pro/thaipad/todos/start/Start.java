package pro.thaipad.todos.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pro.thaipad.todos.dao.PostgreTasksDao;
import pro.thaipad.todos.objects.Duration;
import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;
import pro.thaipad.todos.services.TasksService;

import java.time.LocalDate;
import java.time.LocalTime;

public class Start {
    public static final String CURRENT_USER_EMAIL = "me@thaipad.pro";

    public static void main(String[] argv) {

        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/spring/root-context.xml");

        PostgreTasksDao postgreTasksDao = (PostgreTasksDao) context.getBean("postgreTasksDao");
        TasksService tasksService = (TasksService) context.getBean("tasksServiceImpl");
        User user = tasksService.findUserByEmail(CURRENT_USER_EMAIL);
        if (user == null){
            System.out.println("User not found!");
            return;
        }

        System.out.println(user);

//        Task newTask = (SimpleTask) context.getBean("simpleTask", "test for user thaipad 2");
//        newTask.setDescription("descr");
//        newTask.setDuration(Duration.FOUR_HOURS);
//        newTask.setDate(LocalDate.of(2018, 9, 15));
//        newTask.setTime(LocalTime.of(14, 00));
//        newTask.setComplete(true);
//        int newId = postgreTasksDao.insertTask(newTask);
//        System.out.println(postgreTasksDao.getTaskById(newId));

//        postgreTasksDao.deleteTaskById(22);

//        for (Task task : postgreTasksDao.getTasks(false )) {
//            System.out.println(task);
//        }
//
        Task task = postgreTasksDao.getTaskById(24, user);
        if (task != null) {
            System.out.println(task);
            task.setName("Name New");
            task.setDescription("Description for New");
            task.setDate(LocalDate.of(2018, 10, 22));
            task.setTime(LocalTime.of(20, 30));
            task.setDuration(Duration.ONE_HOUR);
            postgreTasksDao.updateTask(task, user);
            task = postgreTasksDao.getTaskById(24, user);
            System.out.println(task);
        } else {
            System.out.println("Task ont found!");
        }

//        System.out.println(postgreTasksDao.countTask(true));
    }
}
