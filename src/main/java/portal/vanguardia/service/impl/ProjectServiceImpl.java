package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.repository.ProjectRepository;
import portal.vanguardia.entity.Image;
import portal.vanguardia.entity.Project;
import portal.vanguardia.service.ImageService;
import portal.vanguardia.service.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ImageService imageService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ImageService imageService) {
        this.projectRepository = projectRepository;
        this.imageService = imageService;
    }

    @Override
    public Project saveProject(Project project, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()){
            Image image = imageService.uploadImage(file);
            project.setImage(image);
        }
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project){
        return projectRepository.save(project);
    }
    @Override
    public List<Project> getProjects(){
        return projectRepository.findAll();
    }
    @Override
    public List<Project> getProjectsByType(Project.ProjectType type) {
        return projectRepository.findByType(type);
    }
    @Override
    public Optional<Project> getProjectById(Long id){
        return projectRepository.findById(id);
    }
    @Override
    public void deleteProject(Project project) throws IOException {
        if (project.getImage() != null) {
            imageService.deleteImage(project.getImage());
        }
        projectRepository.deleteById(project.getId());
    }
    @Override
    public Project updateProjectImage(MultipartFile file, Project project) throws IOException {
        if (project.getImage() != null) {
            imageService.deleteImage(project.getImage());
        }
        Image newImage = imageService.uploadImage(file);
        project.setImage(newImage);
        return projectRepository.save(project);
    }
}

