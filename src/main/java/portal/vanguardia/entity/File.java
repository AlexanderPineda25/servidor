package portal.vanguardia.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String fileUrl;

    @NotBlank
    private String fileId;

    public File(String name, String fileUrl, String fileId) {
        this.name = name;
        this.fileUrl = fileUrl;
        this.fileId = fileId;
    }
}
