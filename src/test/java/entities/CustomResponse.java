package entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {





    private  int category_id;
    private  int seller_id;
    private String category_title;
    private String email;

    List<CustomResponse> responses;
}
