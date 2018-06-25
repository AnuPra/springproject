package com.bank.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value="accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Modifying
	@Query("update Account acc set acc.amt=?2 where acc.acctId = ?1")
	int updateBalance(Long Id, Double newBalance);
}
