package com.github.losemy.simple.web.controller;

import com.github.losemy.simple.common.mapper.BeanMapper;
import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.service.UserService;
import com.github.losemy.simple.service.dto.LoginDTO;
import com.github.losemy.simple.service.dto.UserDTO;
import com.github.losemy.simple.web.vo.ResultVO;
import com.github.losemy.sso.client.Constant;
import com.github.losemy.sso.client.bean.User;
import com.github.losemy.sso.client.jwt.JwtTokenUtil;
import com.github.losemy.sso.client.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Description: simple
 * Created by lose on 2019/8/29 21:35
 * todo 请求跟返回参数 规范化
 * @author lose
 */
@Controller
public class LoginController {
    private static final String LOGIN_PATH = "/login";

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        //页面跳转
        String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
        if (token == null || JwtTokenUtil.isExpired(token) ) {
            return "redirect:/login";
        } else {
            return "index";
        }
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResultVO<User> getUserInfo(@RequestParam("userToken") String userToken){
        ResultVO<User> result = new ResultVO<>();
        result.setData(JwtTokenUtil.getUser(userToken));
        return result;
    }

    @GetMapping("/login")
    public String login(Model model,@RequestParam("backUrl") String backUrl, HttpServletRequest request) {
        // 需要base64 一下 避免？ get形式的参数无法被获取
        String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
        if (StringUtils.isNotBlank(token) && !JwtTokenUtil.isExpired(token)) {
            return "redirect:" + authBackUrl(backUrl, token);
        } else {
            //到页面
            model.addAttribute(Constant.BACK_URL,backUrl);
            return goLoginPath(backUrl, request);
        }
    }

    @GetMapping("/logout")
    public void logout( HttpServletRequest request,HttpServletResponse response,String backUrl) throws IOException {
        //清除当前 同时也需要清除 远程
        CookieUtils.invalidate(request);
        response.sendRedirect(backUrl);
    }

    @PostMapping("/login")
    public String login(@Validated LoginDTO loginDTO,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDO user = userService.findByNameAndPassword(BeanMapper.map(loginDTO,UserDTO.class));
        String backUrl = loginDTO.getBackUrl();
        if(user == null){
            request.setAttribute("errorMessage", "用户不存在");
            return goLoginPath(backUrl, request);
        } else {
            String token = CookieUtils.getCookie(request, Constant.SSO_TOKEN_NAME);
            // 没有登录的情况
            User tokenUser = BeanMapper.map(user,User.class);
            //或者存放在redis？
            if (StringUtils.isBlank(token) || JwtTokenUtil.isExpired(token)) {
                token = JwtTokenUtil.generate(tokenUser);
                addTokenInCookie(token, request, response);
            }

            // 跳转到原请求
            backUrl = URLDecoder.decode(backUrl, "utf-8");
            return "redirect:" + authBackUrl(backUrl, token);
        }
    }

    private String goLoginPath(String backUrl, HttpServletRequest request) {
        request.setAttribute("backUrl", backUrl);
        return LOGIN_PATH;
    }

    private String authBackUrl(String backUrl, String token) {
        StringBuilder sbf = new StringBuilder(backUrl);
        if (backUrl.indexOf("?") > 0) {
            sbf.append("&");
        } else {
            sbf.append("?");
        }
        sbf.append(Constant.SSO_TOKEN_NAME).append("=").append(token);
        return sbf.toString();
    }

    private void addTokenInCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        // Cookie添加token
        Cookie cookie = new Cookie(Constant.SSO_TOKEN_NAME, token);
        cookie.setPath("/");
        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
