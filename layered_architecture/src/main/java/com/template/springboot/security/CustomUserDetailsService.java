package com.template.springboot.security;

import com.template.springboot.domain.login.LoginBusinessLogic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private LoginBusinessLogic loginBusinessLogic;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        예시
//        try {
//
//            // 1) 비활성화 계정 체크
//            boolean isActive = loginBusinessLogic.isAccountActive(username);
//
//            if (!isActive) throw new UsernameNotFoundException("Username Not Found");
//
//            // 2) 아이디로 계정 조회
//            Login login = loginBusinessLogic.getUserInfo(username);
//
//            if (login == null) throw new UsernameNotFoundException("Username Not Found");
//
//            return new CustomUserDetails(
//                login.getId(),
//                login.getPassword(),
//                login.getAuthorities()
//            );
//        }
//        catch (Exception e) { throw new RuntimeException("Error message -> ", e); }

        return new CustomUserDetails(
            "test",
            "test",
            new Collection<GrantedAuthority>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<GrantedAuthority> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean add(GrantedAuthority grantedAuthority) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends GrantedAuthority> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }
            }
        );
    }
}