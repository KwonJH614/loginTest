package com.example.login.dto;

import com.example.login.entity.User;
import com.example.login.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

  @NotBlank(message = "로그인 아이디가 비어있습니다.")
  private String loginId;

  @NotBlank(message = "비밀번호가 비어있습니다.")
  private String password;
  private String passwordCheck;

  @NotBlank(message = "닉네임이 비어있습니다.")
  private String nickname;

  private UserRole role;

  // 비밀번호 암호화 X
  public User toEntity() {
    return User.builder()
            .loginId(this.loginId)
            .password(this.password)
            .nickname(this.nickname)
            .role(this.role)
            .build();
  }

  // 비밀번호 암호화 O
  public User toEntity(String encodedPassword) {
    return User.builder()
            .loginId(this.loginId)
            .password(this.password)
            .nickname(this.nickname)
            .role(UserRole.USER)
            .build();
  }
}
