import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
@Data
@Builder
public class User implements Serializable {

    private String username;
    private String password;
}
