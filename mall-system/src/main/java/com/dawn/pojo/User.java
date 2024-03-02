package com.dawn.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.dawn.annotation.valid.phone.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 黎明
 * @since 2024-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("litemall_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Null
    private Integer id;

    @ApiModelProperty(value = "用户名称")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "性别：0 未知， 1男， 2 女")
    @Pattern(regexp = "^[012]$",message = "性别参数不正确")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    @Past(message = "生日信息不能晚于当前时间")
    private LocalDate birthday;

    @ApiModelProperty(value = "最近一次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最近一次登录IP地址")
    private String lastLoginIp;

    @ApiModelProperty(value = "0 普通用户，1 VIP用户，2 高级VIP用户")
    @Pattern(regexp = "^[012]$",message = "用户身份信息输入错误")
    private Integer userLevel;

    @ApiModelProperty(value = "用户昵称或网络名称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "用户手机号码")
    @Phone
    private String mobile;

    @ApiModelProperty(value = "用户头像图片")
    @NotBlank(message = "用户需要上传头像")
    private String avatar;

    @ApiModelProperty(value = "微信登录openid")
    @NotBlank(message = "拒绝登录,未获取到微信登录ID",groups = WXLogin.class)
    private String weixinOpenid;

    @ApiModelProperty(value = "微信登录会话KEY")
    @NotBlank(message = "拒绝登录,未获取到微信登录ID",groups = WXLogin.class)
    private String sessionKey;

    @ApiModelProperty(value = "0 可用, 1 禁用, 2 注销")
    @Pattern(regexp = "^[012]$",message = "登录状态参数有误")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @Past(message = "时间不能早于当前时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    @Past(message = "时间不能早于当前时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    /**
     * 用来做微信分组的验证开启
     */
    public interface WXLogin{}


}
