package example.assignment.infrastructure;

import example.assignment.api.BaseTaskAssignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAssignmentRepository extends CrudRepository<TaskAssignment, String> {
    //Used in application service to avoid coupling of application layer to infrastructure layer
    BaseTaskAssignment save(BaseTaskAssignment order);
}
