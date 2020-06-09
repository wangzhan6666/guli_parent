package com.atguigu.eduucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.service.UcenterMemberService;
import com.atguigu.eduucenter.utils.ConstantWxUtils;
import com.atguigu.eduucenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuilException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Classname WxApiController
 * @Description TODO
 * @Date 2020/6/5 23:08
 * @Created by wangzhan
 */
//@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    // 2 获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {

        try {
            // 1 获取code值，临时票据，类似于验证码

            // 2 拿着code请求微信固定的地址，得到两个值access_token  和  openid
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接三个参数：  id   秘钥  和  code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 请求这个拼接好的地址，得到返回两个值  access_id   和   openid
            // 使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            // 从accessTokenInfo字符串取出来两个值  access_id   和   openid
            // 把从accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            // 使用json转换工具   Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            // 3 拿着得到的  access_token   和   openid，再去请求微信提供固定的地址，获取扫码人的信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            // 拼接两个参数
            String userInfoURL = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                    );
            // 发送请求
            String userInfo = HttpClientUtils.get(userInfoURL);
            // 获取返回userInfo字符串扫码人信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");  // 昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");  // 头像

            // 把扫码人信息加入到数据库里面
            // 判断数据库里面是否存在相同微信信息,根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {   // member为空，表没有相同微信数据，进行添加
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);

                memberService.save(member);
            }

            // 使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            // 最后，返回首页面
            return "redirect:http://localhost:3000?token="+jwtToken;

        }catch (Exception e) {
            throw new GuilException(20001, "登录失败");
        }
    }

    // 1 生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        // 固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect" +
//                "?appid=" + ConstantWxUtils.WX_OPEN_APP_ID + "&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redierct_url进行URLEncoder编码
        String redierctUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redierctUrl = URLEncoder.encode(redierctUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redierctUrl,
                "atguigu"
        );

        // 重定向到请求微信地址里面
        // 老师后台写了一个接口(就是上面的callback)用来返回数据，实际开发中不用返回，直接发布到该服务器中
        return "redirect:" + url;
    }
}
