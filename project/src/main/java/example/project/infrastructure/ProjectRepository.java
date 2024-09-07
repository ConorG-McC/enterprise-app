package example.project.infrastructure;

import example.project.api.BaseProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    //Used in application service to avoid coupling of api to infrastructure layer
    //Needs the explicit use of @Query when returning an interface rather than the concrete type (Restaurant)
    @Query("FROM project")
    Iterable<BaseProject> findAllProjects();

    //Used in application service to avoid coupling of application layer to infrastructure layer
    BaseProject save(BaseProject project);
}