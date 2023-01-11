package com.ygl.rege.controller;

import com.ygl.rege.commen.R;
import com.ygl.rege.entity.User;
import com.ygl.rege.utils.SendMailUtil;
import com.ygl.rege.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @PostMapping("/sendMsg")
  public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
    String code = ValidateCodeUtils.generateValidateCode(4).toString();
    SendMailUtil.sendEmailCode(user.getPhone(), code);

    request.getSession().setAttribute("code",code);
    return R.success("发送成功");
  }
}
