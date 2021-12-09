package gxj.study.activiti.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 16:15
 * @description
 */
@Component
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //mock 应作数据库配置
        clients//.withClientDetails(ClientDetails)
                .inMemory()
                //appId
                .withClient("myClient")
                //appSecret
                .secret(passwordEncoder.encode("123456"))
                //授权码、密码、刷新token、简化、客户端
                .authorizedGrantTypes("authorization_code","password","refresh_token",
                        "implicit","client_credentials")
                //作用域
                .scopes("all")
                //资源ID
                .resourceIds("myResource")
                //回调地址
                .redirectUris("http://www.baidu.com","http://www.sina.com/aabc/def?num=1");

    }


    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
//                授权表单提交页面映射（自定义）
//                .pathMapping("/oauth/confirm_assess","my/confirm_assess")
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//                .tokenStore(new JdbcTokenStore(dataSource));
    }

}
