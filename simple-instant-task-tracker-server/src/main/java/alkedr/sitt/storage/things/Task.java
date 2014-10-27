package alkedr.sitt.storage.things;

import com.google.appengine.datanucleus.annotations.Unowned;
import org.jetbrains.annotations.NotNull;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

@PersistenceCapable
public class Task {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent @NotNull private String userId;
    @Persistent @NotNull private String name;
    @Persistent @NotNull private List<String> tags = new ArrayList<>();

    public Task(@NotNull String userId, @NotNull String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NotNull String userId) {
        this.userId = userId;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public List<String> getTags() {
        return unmodifiableList(tags);
    }

    public void setTags(@NotNull List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }
}
