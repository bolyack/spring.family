package com.bamboo.jercn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * 步骤2: 配置SecurityConfig
 * Created by bamboo on 2017/4/30.
 * @see http://www.cnblogs.com/softidea/p/5991897.html
 * @see http://www.w2bc.com/article/221107
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired  //必须要通过注入(让Spring托管， 因为这个类里面调用service层， service调用repository层，他们都是spring托管的)
    private CustomUserDetailsService customUserDetailsService;  //code 1

    @Autowired @Qualifier("dataSource1")  // 指定记住登录信息所使用的数据源
    private DataSource dataSource; //code 2

    /**
     * 设置不拦截规则
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 关闭csrf
        httpSecurity.csrf().disable()
        //允许所有用户访问”/”和”/home” 和 允许所有用户访问”/login”
        .authorizeRequests().antMatchers("/", "/home", "/login", "login.html").permitAll()
        //其他地址的访问均需验证权限
        .anyRequest().authenticated()

        .and()
        .formLogin()    //.usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login")
        //指定登录页是”/login”
        .loginPage("/login").permitAll()
        //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
        .successHandler(loginSuccessHandler())  //code 3

        .and()
        .logout()
        .logoutUrl("/logout")
        //退出登录后的默认网址是”/home”
        .logoutSuccessUrl("/home").permitAll()
        .invalidateHttpSession(true)

        .and()
        .exceptionHandling().accessDeniedPage("/403")  // 无权限

        .and()
        //登录后记住用户，下次自动登录
        //数据库中必须存在名为persistent_logins的表
        //建表语句见code 15
        .rememberMe()
        .tokenValiditySeconds(1209600)
        //指定记住登录信息所使用的数据源
        .tokenRepository(tokenRepository())  //code 4

        .and()  // session 管理
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .sessionFixation().changeSessionId()
        .maximumSessions(1)
        .maxSessionsPreventsLogin(false)
        .expiredUrl("/login")
        .sessionRegistry(sessionRegistry());

    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库 //code 13
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()); //code 5

        //添加自定义的userdetailservice认证
        auth.userDetailsService(customUserDetailsService);

        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }


    // Code3----------------------------------------------
    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();//code 6
    }

    // Code4----------------------------------------------
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    // Code5----------------------------------------------
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

}
