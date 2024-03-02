package com.dawn.service.impl;

import com.dawn.pojo.Role;
import com.dawn.mapper.RoleMapper;
import com.dawn.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 黎明
 * @since 2024-03-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
