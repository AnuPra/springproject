package com.bank.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value="accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long> {

}
