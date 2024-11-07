package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal.vanguardia.entity.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByType(Project.ProjectType type);

}
