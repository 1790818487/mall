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
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Night
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("litemall_role")
@ApiModel(value="Role对象", description="角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Null
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "找好名称标识不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String desc;

    @ApiModelProperty(value = "是否启用")
    @Pattern(regexp = "^[01]$",message = "是否启用账户参数不正确")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @Past
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    @Past
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    @Pattern(regexp = "^[01]$",message = "是否删除账户的参数不正确")
    private Boolean deleted;


}
