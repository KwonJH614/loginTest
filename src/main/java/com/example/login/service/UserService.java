package com.example.login.service;

import com.example.login.dto.JoinRequest;
import com.example.login.dto.LoginRequest;
import com.example.login.entity.User;
import com.example.login.entity.UserRole;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
//  // 비밀번호 암호화 시 사용
//  private final BCryptPasswordEncoder encoder;

  /**
    * loginId 중복 체크
    * 회원가입 기능 구현 시 사용
    * 중복되면 true return
   */
  public boolean CheckLoginIdDuplicate(String loginId) {
    return userRepository.existsByLoginId(loginId);
  }

  /**
   * nickname 중복 체크
   * 회원가입 기능 구현 시 사용
   * 중복되면 true return
   */
  public boolean CheckNicknameDuplicate(String nickname) {
    return userRepository.existsByNickname(nickname);
  }

  /**
   * 회원가입 기능 1
   * 화면에서 JoinRequest (loginId, password, nickname) 입력받아서 User로 변환 후 저장
   * loginId, nickname 중복 체크는 Controller에서 진행 -> 에러 메세지 출력을 위해서
   */
  public void join(JoinRequest req) {
    if (req.getLoginId().equals("admin")) req.setRole(UserRole.ADMIN);
    else req.setRole(UserRole.USER);
    userRepository.save(req.toEntity());
  }

  /**
   * 회원가입 기능 2
   * 화면에서 JoinRequest(loginId, password, nickname)을 입력받아 User로 변환 후 저장
   * 회원가입 1과는 달리 비밀번호를 암호화해서 저장
   * loginId, nickname 중복 체크는 Controller에서 진행 => 에러 메세지 출력을 위해
   */
//  public void join2(JoinRequest req) {
//    userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
//  }

  /**
   *  로그인 기능
   *  화면에서 LoginRequest(loginId, password)을 입력받아 loginId와 password가 일치하면 User return
   *  loginId가 존재하지 않거나 password가 일치하지 않으면 null return
   */
  public User login(LoginRequest req) {
    Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

    if (optionalUser.isEmpty()) {
      return null;
    }

    User user = optionalUser.get();

    if (!user.getPassword().equals(req.getPassword())) {
      return null;
    }

    return user;
  }

  /**
   * userId(Long)를 입력받아 User을 return 해주는 기능
   * 인증, 인가 시 사용
   * userId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
   * userId로 찾아온 User가 존재하면 User return
   */
  public User getLoginUserById(Long userId) {
    if(userId == null) return null;

    Optional<User> optionalUser = userRepository.findById(userId);
    if(optionalUser.isEmpty()) return null;

    return optionalUser.get();
  }

  /**
   * loginId(String)을 입력받아 User 를 return 하는 기능
   * 인증, 인가 시 사용
   * loginId가 null 이거나 (로그인 X) userId로 찾아온 User 가 없으면 null return
   * loginId 로 찾아온 User 가 존재하면 User return
   */
  public User getLoginUserByLoginId(String loginId) {
    if (loginId == null) return null;

    Optional<User> optionalUser = userRepository.findByLoginId(loginId);
    if (optionalUser.isEmpty()) return null;

    return optionalUser.get();
  }
}
