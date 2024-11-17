package portal.vanguardia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
