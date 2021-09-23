package bifast.corebank.exception;

/**
 *
 * @author ErwinSn
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    
    public DataNotFoundException() {
        super("Data not foundaaaaaa");
    }
    
    public DataNotFoundException(Long id) {
        super("Data with id '" + id + "' not found");
    }
    
    public DataNotFoundException(String id) {
        super("Data '" + id + "' not found");
    }
    
}
