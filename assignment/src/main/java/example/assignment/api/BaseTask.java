package example.assignment.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.assignment.api.events.TaskDeserializer;
import example.common.domain.Hours;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseTask {
     long id();

     String name() ;

     Hours hours();
}