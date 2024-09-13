package example.assignment.api;

import example.common.domain.Hours;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseTask {
     long id();

     String name() ;

     Hours hours();
}