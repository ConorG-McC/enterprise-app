package example.order.domain;

import example.common.domain.*;
import example.order.api.BaseMenuItem;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
public class MenuItem extends ValueObject implements BaseMenuItem { //BaseMenuItem (lives in api to avoid coupling to domain
    private long id;
    private String name;
    private Hours price;

    public MenuItem(long id, String name, Hours price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    public long id(){return id;}

    public String name() {
        return name;
    }

    public Hours price() {
        return price;
    }

    private void setId(long id) {
        assertArgumentNotEmpty(id, "Id cannot be empty");
        this.id = id;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setPrice(Hours price) {
        assertArgumentNotEmpty(price, "Price cannot be empty");
        this.price = price;
    }

    public String toString(){
        return String.format("id=%s, name=%s, price=%s", id(), name, price);
    }

}