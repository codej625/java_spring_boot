package com.template.springboot.domain.account;

import com.template.springboot.domain.account.entity.Account;
import com.template.springboot.domain.login.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {}