package en.sd.chefmgmt.security.service.auth;

import java.util.UUID;

public interface AuthService {

    boolean isSelf(UUID userId);
}
