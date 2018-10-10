package pro.thaipad.todos.objects;

public class Project {
    private Integer id;
    private String name;

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(String name) {
        this(0, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
