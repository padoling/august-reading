package com.padoling.portfolio.august.service.user;

import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import com.padoling.portfolio.august.web.dto.user.UserNickUpdateRequestDto;
import com.padoling.portfolio.august.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean isNicknameExist(String nickname) {
        User entity = userRepository.findByNickname(nickname);
        return entity != null;
    }

    @Transactional
    public User updateNickname(UserNickUpdateRequestDto requestDto) {
        User entity = userRepository.findById(requestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + requestDto.getId()));

        return entity.updateNickname(requestDto.getNickname());
    }

    public UserResponseDto findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new UserResponseDto(entity);
    }
}
