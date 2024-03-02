package com.dawn.service.impl;

import com.dawn.pojo.Permission;
import com.dawn.mapper.PermissionMapper;
import com.dawn.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 黎明
 * @since 2024-03-02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
