package example.project.application;

import example.common.domain.Hours;
import example.project.api.BaseProject;
import example.project.api.CreateProjectCommand;
import example.project.domain.Task;
import example.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectApplicationServiceTests {
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    private ProjectApplicationService projectApplicationService;
    private ProjectRepository projectRepository;
    private RabbitTemplate rabbitTemplate;
    private Environment env;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        env = mock(Environment.class);

        projectApplicationService = new ProjectApplicationService(projectRepository, env, rabbitTemplate);
    }

    @Test
    @DisplayName("When project with the same name exists, then throw ProjectDomainException")
    void whenProjectWithSameNameExists_thenThrowException() {
        when(projectRepository.findByName(anyString())).thenReturn(Optional.of(mock(BaseProject.class)));

        Task task = new Task(1L, "Task 1", new Hours(BigDecimal.valueOf(2.0)));
        CreateProjectCommand command = new CreateProjectCommand("Existing Project", List.of(task));

        assertThrows(ProjectDomainException.class, () ->
                projectApplicationService.createProjectWithTasks(command));

        verify(projectRepository, times(0)).save(any(BaseProject.class));
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("When valid project ID is provided, then the project is deleted successfully")
    void whenValidProjectId_thenProjectIsDeleted() throws Exception {
        // Mock repository to return a project by ID
        example.project.infrastructure.Project project = mock(example.project.infrastructure.Project.class);
        when(projectRepository.findById(anyString())).thenReturn(Optional.of(project));
        when(env.getProperty("rabbitmq.exchange")).thenReturn("exchange");
        when(env.getProperty("rabbitmq.deleteProjectKey")).thenReturn("deleteProjectKey");

        String response = projectApplicationService.deleteProjectById("validProjectId");

        verify(projectRepository, times(1)).deleteById("validProjectId");
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), anyString());

        assertEquals("Project with id validProjectId deleted", response);
    }

    @Test
    @DisplayName("When project ID does not exist, then throw ProjectDomainException")
    void whenProjectIdDoesNotExist_thenThrowException() {
        // Mock repository to return empty
        when(projectRepository.findById(anyString())).thenReturn(Optional.empty());

        // Assert that exception is thrown
        assertThrows(ProjectDomainException.class, () ->
                projectApplicationService.deleteProjectById("nonExistentProjectId"));


        // Verify that RabbitMQ event was not sent
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), anyString(), anyString());
    }

}
