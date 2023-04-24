package com.toolman.ruler.config.security.loader;

import com.toolman.ruler.dao.RoleRepository;
import com.toolman.ruler.dao.UserRepository;
import com.toolman.ruler.dao.UserRoleRepository;
import com.toolman.ruler.entity.Role;
import com.toolman.ruler.entity.User;
import com.toolman.ruler.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Load the user information such as name, pwd, authority from back-end.
 * 此 service 使預設的 {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
 *  DaoAuthenticationProvider} 完整使用
 *
 * @author toolman
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------------MyUserDetailsService-------------");

        User user = userRepository.findByUsernameAndEnabled(username, true);

        // 沒有這個 user
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), getAuthorities(user.getId()));
    }

    // fill Authorities
    private List<GrantedAuthority> getAuthorities(Integer userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        List<Integer> roleIds = userRoles.stream().map(ur -> ur.getId().getRoleId()).collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllById(roleIds);

        List<GrantedAuthority> result = roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return result;
    }

}
