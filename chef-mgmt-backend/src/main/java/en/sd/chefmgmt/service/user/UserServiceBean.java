package en.sd.chefmgmt.service.user;

import en.sd.chefmgmt.exception.model.DataNotFoundException;
import en.sd.chefmgmt.exception.model.ExceptionCode;
import en.sd.chefmgmt.model.dto.user.UserResponseDTO;
import en.sd.chefmgmt.model.entity.ChefEntity;
import en.sd.chefmgmt.model.entity.UserEntity;
import en.sd.chefmgmt.model.mapper.UserMapper;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceBean implements UserService {

    private final ChefRepository chefRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO getUserInfo(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.USER_NOT_FOUND, email));
        ChefEntity chef = chefRepository.findById(user.getId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, user.getId()));

        return userMapper.userEntityToUserResponseDTO(user, chef);
    }
}
