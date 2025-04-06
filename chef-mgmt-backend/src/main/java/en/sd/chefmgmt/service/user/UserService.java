package en.sd.chefmgmt.service.user;

import en.sd.chefmgmt.model.dto.user.UserResponseDTO;

public interface UserService {

    UserResponseDTO getUserInfo(String email);
}
