package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
     * 后台banner的管理接口
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    // 1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){

        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        bannerService.page(bannerPage, null);

        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    // 2 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);

        return R.ok();
    }

    // 3 根据bannerID查询数据
    @GetMapping("getBannerById/{bannerId}")
    public R getBannerById(@PathVariable String bannerId) {
        CrmBanner crmBanner = bannerService.getById(bannerId);
        return R.ok().data("banner", crmBanner);
    }

    // 4 修改banner
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    // 5 删除banner
    @DeleteMapping("removeBanner/{bannerId}")
    public R removeBanner(@PathVariable String bannerId) {
        bannerService.removeById(bannerId);
        return R.ok();
    }

}

