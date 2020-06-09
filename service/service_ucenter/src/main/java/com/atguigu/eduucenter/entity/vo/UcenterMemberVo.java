package com.atguigu.eduucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname UcenterMemberVo
 * @Description TODO
 * @Date 2020/6/6 16:08
 * @Created by wangzhan
 */
@Data
public class UcenterMemberVo {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
