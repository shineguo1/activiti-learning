package gxj.study.activiti.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 17:00
 * @description
 */
//@Component 使用DemoApplicationConfiguration里的bean
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查数据库 return user.username = xxx, user.password = xxx, 返回 UserDetails对象

//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ACTIVITI_USER");
        return new User("username", new BCryptPasswordEncoder().encode("password"), authorities);
    }
}
