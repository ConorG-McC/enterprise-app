package example.project.domain;

import example.common.domain.Identity;
import example.common.domain.Hours;
import example.project.api.BaseTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTests {
    //Generate controlled UUID rather than rely on UniqueIDFactory dependency
    private Identity VALID_ID = new Identity("0000-00-00-00-000001");
    private String VALID_NAME="name";
    private List<BaseTask> VALID_MENU_ITEMS=new ArrayList<>();

    private long VALID_MENU_ITEM_ID = 1L;
    @Test
    @DisplayName("A new Restaurant is created with valid details")
    void test01(){
        assertDoesNotThrow(() -> {
            new Project(VALID_ID,VALID_NAME,VALID_MENU_ITEMS);
        });
    }

    //Note I had to add a validate id check to the Entity class constructor for this to work (missing previously)
    @Test
    @DisplayName("A Restaurant will reject an invalid identity")
    void test02(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(null,VALID_NAME,VALID_MENU_ITEMS);
        });
    }

    @Test
    @DisplayName("A Restaurant will reject an invalid name")
    void test03(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(VALID_ID,"",VALID_MENU_ITEMS);
        });
    }

    @Test
    @DisplayName("A Restaurant will reject invalid menu items")
    void test04(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(VALID_ID,VALID_NAME,null);
        });
    }

    //Note - I had to add a setMenuItems method to Restaurant to validate menu items (missing previously)
    @Test
    @DisplayName("A Restaurant can locate a menu item that it contains")
    void test05(){
        List<BaseTask> menuItems =new ArrayList<>();
        BaseTask newItem = new Task(VALID_MENU_ITEM_ID, "item name", new Hours(1));

        menuItems.add(newItem);
        Project sut=new Project(VALID_ID,VALID_NAME,menuItems);

        assertTrue(sut.findTask(VALID_MENU_ITEM_ID));
    }

    @Test
    @DisplayName("A Restaurant cannot locate a menu item that it does not contain")
    void test06(){
        //No need to arrange - just act and assert
        Project sut=new Project(VALID_ID,VALID_NAME,VALID_MENU_ITEMS);

        assertFalse(sut.findTask(VALID_MENU_ITEM_ID));
    }
}
