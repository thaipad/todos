package pro.thaipad.todos.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pro.thaipad.todos.objects.Duration;
import pro.thaipad.todos.objects.SimpleTask;
import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component("postgreTasksDao")
public class PostgreTasksDao implements TasksDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertTask;

    public PostgreTasksDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertTask = new SimpleJdbcInsert(dataSource)
                .withTableName(PostgreNamesDbsFields.DB_TASKS)
                .usingColumns(PostgreNamesDbsFields.DB_TASKS_NAME,
                        PostgreNamesDbsFields.DB_TASKS_DESCR,
                        PostgreNamesDbsFields.DB_TASKS_DATE,
                        PostgreNamesDbsFields.DB_TASKS_TIME,
                        PostgreNamesDbsFields.DB_TASKS_DURATION,
                        PostgreNamesDbsFields.DB_TASKS_COMPLETE,
                        PostgreNamesDbsFields.DB_TASKS_OWNER)
                .usingGeneratedKeyColumns(PostgreNamesDbsFields.DB_TASKS_KEY);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public int insertTask(Task task, User owner) {

        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PostgreNamesDbsFields.DB_TASKS_NAME, task.getName());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DESCR, task.getDescription());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DATE, task.getDate());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_TIME, task.getTime());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DURATION, task.getDuration(), Types.OTHER, "DURATION_TYPE");
        params.addValue(PostgreNamesDbsFields.DB_TASKS_COMPLETE, task.isComplete());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_OWNER, owner.getId());

        return (int) insertTask.executeAndReturnKey(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void deleteTaskById(int id, User owner) {

        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());

        String sql = "DELETE FROM tasks WHERE " + PostgreNamesDbsFields.DB_TASKS_KEY + " = :id AND " +
                PostgreNamesDbsFields.DB_TASKS_OWNER + " = :idOwner";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("idOwner", owner.getId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteTask(Task task, User owner) {
        deleteTaskById(task.getId(), owner);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void updateTask(Task task, User owner) {

        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());

        String sql = "UPDATE tasks SET (" + PostgreNamesDbsFields.DB_TASKS_NAME + ", " +
                PostgreNamesDbsFields.DB_TASKS_DESCR + ", " + PostgreNamesDbsFields.DB_TASKS_DURATION + ", " +
                PostgreNamesDbsFields.DB_TASKS_DATE + ", " + PostgreNamesDbsFields.DB_TASKS_TIME + ", " +
                PostgreNamesDbsFields.DB_TASKS_COMPLETE + ") = (:" +
                PostgreNamesDbsFields.DB_TASKS_NAME + ", :" +
                PostgreNamesDbsFields.DB_TASKS_DESCR + ", :" + PostgreNamesDbsFields.DB_TASKS_DURATION + ", :" +
                PostgreNamesDbsFields.DB_TASKS_DATE + ", :" + PostgreNamesDbsFields.DB_TASKS_TIME + ", :" +
                PostgreNamesDbsFields.DB_TASKS_COMPLETE + ") WHERE " + PostgreNamesDbsFields.DB_TASKS_KEY + " = :" +
                PostgreNamesDbsFields.DB_TASKS_KEY + " AND " + PostgreNamesDbsFields.DB_TASKS_OWNER + " = " + owner.getId();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PostgreNamesDbsFields.DB_TASKS_KEY, task.getId());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_NAME, task.getName());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DESCR, task.getDescription());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DATE, task.getDate());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_TIME, task.getTime());
        params.addValue(PostgreNamesDbsFields.DB_TASKS_DURATION, task.getDuration(), Types.OTHER, "DURATION_TYPE");
        params.addValue(PostgreNamesDbsFields.DB_TASKS_COMPLETE, task.isComplete());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Task getTaskById(int id, User owner) {
        String sql = "SELECT * FROM tasks WHERE " +
                PostgreNamesDbsFields.DB_TASKS_KEY + "=:id AND " +
                PostgreNamesDbsFields.DB_TASKS_OWNER + "=:idOwner";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("idOwner", owner.getId());
        try {
            return jdbcTemplate.queryForObject(sql, params, new TaskRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Task> getTasks(boolean onlyActive, User owner) {
        String sql = "SELECT * FROM tasks WHERE " + PostgreNamesDbsFields.DB_TASKS_OWNER + " = " + owner.getId();
        if (onlyActive) {
            sql += " AND NOT " + PostgreNamesDbsFields.DB_TASKS_COMPLETE;
        }

        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public int countTask(boolean onlyActive, User owner) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE " + PostgreNamesDbsFields.DB_TASKS_OWNER + " = " + owner.getId();
        if (onlyActive) {
            sql += " AND NOT " + PostgreNamesDbsFields.DB_TASKS_COMPLETE;
        }
        return jdbcTemplate.getJdbcOperations().queryForObject(sql, Integer.class);
    }

    private static final class TaskRowMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            Task task = new SimpleTask(resultSet.getInt(PostgreNamesDbsFields.DB_TASKS_KEY), resultSet.getString(PostgreNamesDbsFields.DB_TASKS_NAME));
            task.setDescription(resultSet.getString(PostgreNamesDbsFields.DB_TASKS_DESCR));
            if (resultSet.getDate(PostgreNamesDbsFields.DB_TASKS_DATE) != null) {
                task.setDate(resultSet.getDate(PostgreNamesDbsFields.DB_TASKS_DATE).toLocalDate());
            }
            if (resultSet.getTime(PostgreNamesDbsFields.DB_TASKS_TIME) != null) {
                task.setTime(resultSet.getTime(PostgreNamesDbsFields.DB_TASKS_TIME).toLocalTime());
            }
            task.setComplete(resultSet.getBoolean(PostgreNamesDbsFields.DB_TASKS_COMPLETE));
            task.setDuration(Duration.valueOf(resultSet.getString(PostgreNamesDbsFields.DB_TASKS_DURATION)));
            return task;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM " + PostgreNamesDbsFields.DB_USERS +
                " WHERE " + PostgreNamesDbsFields.DB_USERS_EMAIL + " = :email";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        try {
            User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt(PostgreNamesDbsFields.DB_USERS_KEY),
                                resultSet.getString(PostgreNamesDbsFields.DB_USERS_NAME),
                                resultSet.getString(PostgreNamesDbsFields.DB_USERS_EMAIL));
                }
            });
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
