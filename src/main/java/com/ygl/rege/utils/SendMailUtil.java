package com.ygl.rege.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendMailUtil {
  /** * 发送邮件代码 * @param targetEmail 目标邮箱 * @param authCode 验证码 */
  public static void sendEmailCode(String targetEmail, String authCode) {
    try {

      SimpleEmail mail = new SimpleEmail();
// 发送邮件的服务器
      mail.setHostName("smtp.qq.com");
// 刚刚记录的授权码，是开启SMTP的密码
      mail.setAuthentication("3351878643@qq.com", "byueolalmslfcida");
// 发送邮件的邮箱和发件人
      mail.setFrom("3351878643@qq.com", "ygl");
// 使用安全链接
      mail.setSSLOnConnect(true);
// 接收人的邮箱
      mail.addTo(targetEmail);
// 邮件的主题（标题）
      mail.setSubject("注册验证码");
// 邮件的内容
      mail.setMsg("验证码为:" + authCode);
// 发送
      mail.send();
    } catch (EmailException e) {
      e.printStackTrace();
    }
  }
}
