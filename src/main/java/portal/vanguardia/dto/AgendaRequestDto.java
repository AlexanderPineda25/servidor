package portal.vanguardia.dto;

import lombok.Data;

@Data
public class AgendaRequestDto {
    private Long teacherId;
    private Long studentId;
    private String parentName;
    private String contactInfo;
    private String meetingDate;
    private String startTime;
    private String endTime;
}
