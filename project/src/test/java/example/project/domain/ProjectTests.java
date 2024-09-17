package example.project.domain;

import example.common.domain.Hours;
import example.common.domain.Identity;
import example.project.api.BaseTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTests {
    //Generate controlled UUID rather than rely on UniqueIDFactory dependency
    private Identity VALID_ID = new Identity("0000-00-00-00-000001");
    private String VALID_NAME = "name";
    private List<BaseTask> VALID_TASKS = new ArrayList<>();

    private long VALID_TASK_ID = 1L;

    @Test
    @DisplayName("A new project is created with valid details")
    void test01() {
        assertDoesNotThrow(() -> {
            new Project(VALID_ID, VALID_NAME, VALID_TASKS);
        });
    }

    //Note I had to add a validate id check to the Entity class constructor for this to work (missing previously)
    @Test
    @DisplayName("A project will reject an invalid identity")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(null, VALID_NAME, VALID_TASKS);
        });
    }

    @Test
    @DisplayName("A project will reject an invalid name")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(VALID_ID, "", VALID_TASKS);
        });
    }

    @Test
    @DisplayName("A project will reject invalid tasks")
    void test04() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(VALID_ID, VALID_NAME, null);
        });
    }

    //Note - I had to add a setMenuItems method to Restaurant to validate menu items (missing previously)
    @Test
    @DisplayName("A project can locate a task that it contains")
    void test05() {
        List<BaseTask> tasks = new ArrayList<>();
        BaseTask newItem = new Task(VALID_TASK_ID, "item name", new Hours(1));

        tasks.add(newItem);
        Project project = new Project(VALID_ID, VALID_NAME, tasks);

        assertTrue(project.findTask(VALID_TASK_ID));
    }

    @Test
    @DisplayName("A project cannot locate a task that it does not contain")
    void test06() {
        //No need to arrange - just act and assert
        Project project = new Project(VALID_ID, VALID_NAME, VALID_TASKS);

        assertFalse(project.findTask(VALID_TASK_ID));
    }
}
