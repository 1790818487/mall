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

import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Night
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("litemall_permission")
@ApiModel(value="Permission对象", description="权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Null
    private Integer id;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "创建时间")
    @Past
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    @Past
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    @Pattern(regexp = "^[01]$",message = "逻辑删除参数不正确")
    private Boolean deleted;


}
