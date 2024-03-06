package com.dawn.service.impl;

import com.dawn.pojo.Admin;
import com.dawn.mapper.AdminMapper;
import com.dawn.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Night
 * @since 2024-03-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
