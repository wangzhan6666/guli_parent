package com.atguigu.eduservice.entity.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
