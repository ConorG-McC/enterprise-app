package example.order.api;

import example.common.domain.Hours;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseMenuItem {
     long id();

     String name() ;

     Hours price();
}