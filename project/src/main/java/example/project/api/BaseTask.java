package example.project.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import example.common.domain.Hours;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api

public interface BaseTask {
     long id();

     String name() ;

     Hours hours();
}