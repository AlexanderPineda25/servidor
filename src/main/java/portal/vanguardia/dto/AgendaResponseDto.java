package portal.vanguardia.dto;

import lombok.Data;

@Data
public class AgendaResponseDto {
    private Long id;
    private UserResponseDto teacher;
    private UserResponseDto student;
    private String parentName;
    private String contactInfo;
    private String meetingDate;
    private String startTime;
    private String endTime;
    private String status;
}
