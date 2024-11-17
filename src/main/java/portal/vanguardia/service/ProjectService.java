package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Project;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project saveProject(Project project, MultipartFile file) throws IOException;
    Project updateProject(Project project);
    List<Project> getProjects();
    Optional<Project> getProjectById(Long id);
    void deleteProject(Project project) throws IOException;
    Project updateProjectImage(MultipartFile file, Project project) throws IOException;
    List<Project> getProjectsByType(Project.ProjectType type);
}
