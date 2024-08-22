//package com.backend.carhub.SecurityConfig;
//
//
//
//import com.backend.carhub.Model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class UserPrincipal implements UserDetails {
//
//    private final User user;
//
//    public UserPrincipal(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return Collections.emptyList();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUserName(); // Ensure your User class has this method
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Implement if your User class has expiration logic
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Implement if your User class has locking logic
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Implement if your User class has expiration logic
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.isEnabled(); // Ensure your User class has an isEnabled method
//    }
//}
