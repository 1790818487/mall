package com.dawn.controller.litemalluser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDto {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z@_#]{6,15}$",message = "用户名格式不正确")
    private String username;

    @NotBlank(message = "密码不能为空弄")
    @Pattern(regexp = "^[0-9a-zA-Z@_#]{6,}$",message = "密码格式不正确")
    private String password;

    private Integer[] ids;
}
