package example.project.domain;

import example.common.domain.Hours;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTests {
    private long VALID_MENU_ITEM_ID = 1L;
    private String VALID_NAME="name";
    private Hours VALID_Hours = new Hours(1);

    @Test
    @DisplayName("A new task is created with valid details")
    void test01(){
        assertDoesNotThrow(() -> {
            new Task(VALID_MENU_ITEM_ID,VALID_NAME, VALID_Hours);
        });
    }

    //Note I had to add a validate id check to the Entity class constructor for this to work (missing previously)
    @Test
    @DisplayName("A task will reject an invalid identity")
    void test02(){
        //Menu Item id is a long
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(-2,VALID_NAME, VALID_Hours);
        });
    }

    @Test
    @DisplayName("A task will reject an invalid name")
    void test03(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(VALID_MENU_ITEM_ID,"", VALID_Hours);
        });
    }

    //Note I had to add a validate menuItems check to the MenuItems class constructor for this to work (missing previously)
    @Test
    @DisplayName("A task will reject an invalid Hour value")
    void test04(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(VALID_MENU_ITEM_ID,VALID_NAME,null);
        });
    }
}
