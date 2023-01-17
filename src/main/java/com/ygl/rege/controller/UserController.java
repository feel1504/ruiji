package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.User;
import com.ygl.rege.service.UserService;
import com.ygl.rege.utils.SendMailUtil;
import com.ygl.rege.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/sendMsg")
  public R<String> sendMsg(@RequestBody User user, HttpSession session){
    String email = user.getPhone();

    if(email!=null){
      String code = ValidateCodeUtils.generateValidateCode(4).toString();
      session.setAttribute(email,code);

      SendMailUtil.sendEmailCode(user.getPhone(), code);
      log.info("key是：{}",email);
      return R.success("发送成功");
    }else {
      return R.error("发送失败");
    }

  }

  @PostMapping("/login")
  public R<String> login(@RequestBody Map map, HttpSession session){
    //获取手机号
    String email = map.get("phone").toString();
    //获取验证码
    String code = map.get("code").toString();
    log.info("key是：{}",email);
    //从session获取code
    String codeInSession = (String)session.getAttribute(email);

    if(codeInSession!=null && codeInSession.equals(code)){
      //登录
      LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(User::getPhone,email);
      User user = userService.getOne(queryWrapper);
      if(user==null){
        // 判断是否第一次登录，是就注册在登录
        user = new User();
        user.setPhone(email);
        user.setStatus(1);
        userService.save(user);
      }
      session.setAttribute("user",user.getId());
      return R.success("登录成功");

    }
    return R.error("登录失败");
  }
}
