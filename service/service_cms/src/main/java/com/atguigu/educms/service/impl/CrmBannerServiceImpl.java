package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 1 查询所有banner
    @Cacheable(key = "'selectIndexList'", value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {

        // 根据id进行降序排列，显示排列之后前2条记录
        QueryWrapper<CrmBanner> wrapperBanner = new QueryWrapper<>();
        wrapperBanner.orderByDesc("id");
        // last方法，拼接sql语句
        wrapperBanner.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(wrapperBanner);
        return list;
    }
}
