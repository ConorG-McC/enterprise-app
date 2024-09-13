package example.assignment.application;


import example.assignment.api.BaseProject;
import example.assignment.api.GetProjectResponse;
import example.assignment.api.GetProjectTaskResponse;
import example.assignment.infrastructure.ProjectRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectQueryHandler {
    private ProjectRepository projectRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetProjectResponse> getProject(String projectId) {
        return projectRepository.findById(projectId).map(project ->
                modelMapper.map(project, GetProjectResponse.class));
    }

    public Iterable<BaseProject> getAllProjects() {
        return projectRepository.findAllProjects();
    }

    public Optional<GetProjectTaskResponse> getProjectTasks(String projectId) {
        return projectRepository.findById(projectId).map(project ->
                modelMapper.map(project, GetProjectTaskResponse.class));
    }
}
