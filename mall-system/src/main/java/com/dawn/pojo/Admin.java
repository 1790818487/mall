package com.dawn.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author Night
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("litemall_admin")
@ApiModel(value="Admin对象", description="管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "管理员名称")
    @NotBlank(message = "管理员名称不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,11}$",message = "账号含有非法字符")
    private String username;

    @ApiModelProperty(value = "管理员密码")
    @NotBlank(message ="密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9.@#_]{6,}$",message = "密码格式不支持")
    private String password;

    @ApiModelProperty(value = "最近一次登录IP地址")
    private String lastLoginIp;

    @ApiModelProperty(value = "最近一次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "头像图片")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    @ApiModelProperty(value = "角色列表")
    private String roleIds;


}
