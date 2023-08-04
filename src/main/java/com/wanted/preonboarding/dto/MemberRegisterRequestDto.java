package com.wanted.preonboarding.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter /*@Setter*/
public class MemberRegisterRequestDto {
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
