package alkedr.sitt.storage;

import alkedr.sitt.storage.things.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import static javax.jdo.JDOHelper.getPersistenceManagerFactory;

public class DatabaseConnection implements AutoCloseable {
    private static final PersistenceManagerFactory PERSISTENCE_MANAGER_FACTORY = getPersistenceManagerFactory("transactions-optional");
    private PersistenceManager pm = null;

    private PersistenceManager pm() {
        if (pm == null) {
            pm = PERSISTENCE_MANAGER_FACTORY.getPersistenceManager();
        }
        return pm;
    }

    @Override
    public void close() {
        if (pm != null) {
            pm.close();
        }
    }

    public void insertTask(@NotNull Task task) {
        pm().makePersistent(task);
    }

    public void insertTask(@NotNull String userId, @NotNull String name) {
        insertTask(new Task(userId, name));
    }


    @Nullable
    public Task getTask(@NotNull Long id, @NotNull String userId) {
        Task task = pm().getObjectById(Task.class, id);
        return (userId.equals(task.getUserId())) ? task : null;
    }


    public void updateTask(@NotNull Task task) {

    }


    public void deleteTask(@NotNull Long id) {
        Query q = pm().newQuery(Task.class);
        q.setFilter("id = idParam");
        q.declareParameters("long idParam");
        q.deletePersistentAll(id);
    }
}
