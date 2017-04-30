package com.bamboo.jercn;

import com.bamboo.jercn.common.ApplicationContextHelper;
import com.bamboo.jercn.domain.User;
import com.bamboo.jercn.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan("com.bamboo.jercn")
public class SpringSecurityApplication {

	public static void main(String[] args) {

		SpringApplication springApplication = new SpringApplication(SpringSecurityApplication.class);

		ApplicationContextHelper.ctx = springApplication.run(args); //将密码加密 必须保证数据库s_user中有id为1的用户 //code 14

//		UserService userService = (UserService)ApplicationContextHelper.ctx.getBean("userService");
//		User su = userService.findUserById(1);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
//		su.setPassword(encoder.encode("111111"));
//		userService.update(su);


	}
}
