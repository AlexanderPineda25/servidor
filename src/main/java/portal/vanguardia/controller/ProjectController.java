package portal.vanguardia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Project;
import portal.vanguardia.service.impl.ProjectServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectServiceImpl projectServiceImpl;

    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestPart("project") Project project, @RequestPart("file")MultipartFile file) {
        try {
            Project savedProject = projectServiceImpl.saveProject(project, file);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Project> updateProjectImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Project> project = projectServiceImpl.getProjectById(id);
        if (project.isPresent()) {
            Project updatedProject = projectServiceImpl.updateProjectImage(file, project.get());
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        try {
            Project savedProject = projectServiceImpl.updateProject(project);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(projectServiceImpl.getProjects(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectId(@PathVariable Long id) {
        Optional<Project> project = projectServiceImpl.getProjectById(id);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/type")
    public ResponseEntity<List<Project>> getProjectsByType(@RequestParam Project.ProjectType type) {
        List<Project> projects = projectServiceImpl.getProjectsByType(type);
        if (!projects.isEmpty()) {
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) throws IOException {
        Optional<Project> project = projectServiceImpl.getProjectById(id);
        if (project.isPresent()){
            projectServiceImpl.deleteProject(project.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
