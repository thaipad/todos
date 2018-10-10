package pro.thaipad.todos.objects;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Task {

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    LocalDate getDate();

    void setDate(LocalDate date);

    LocalTime getTime();

    void setTime(LocalTime time);

    Duration getDuration();

    void setDuration(Duration duration);

    boolean isComplete();

    void setComplete(boolean complete);

    Project getProject();

    void setProject(Project project);

}
