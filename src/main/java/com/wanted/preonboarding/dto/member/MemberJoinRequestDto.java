package com.wanted.preonboarding.dto.member;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Getter /*@Setter*/
public class MemberJoinRequestDto {
    // 주어진 요구 사항을 제외한 유효성 검사 기능은 넣지 않기 위해, @NotNull, @NotEmpty, @NotBlank 어노테이션도 제외함.
//    @NotEmpty
    @Email(message = "이메일은 \'@\'를 포함한 이메일 형식을 입력해 주세요.")
    private String email;
//    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
