package example.assignment.infrastructure;

import example.assignment.api.BaseProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    //Used in application service to avoid coupling of api to infrastructure layer
    //Needs the explicit use of @Query when returning an interface rather than the concrete type (Restaurant)
    @Query("FROM project")
    Iterable<BaseProject> findAllProjects();

    Optional<BaseProject> findByName(String name);


    //Used in application service to avoid coupling of application layer to infrastructure layer
    BaseProject save(BaseProject project);
}