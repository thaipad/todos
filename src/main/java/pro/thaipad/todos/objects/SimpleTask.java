package pro.thaipad.todos.objects;

import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTask implements Task {
    private Integer id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private Duration duration;
    private boolean complete;
    private Project project;

    public SimpleTask(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.duration = Duration.ONE_HOUR;
    }

    public SimpleTask(String name) {
        this(0, name);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                " " + time +
                ", duration=" + duration +
                ", complete=" + complete +
                "}";
    }
}
