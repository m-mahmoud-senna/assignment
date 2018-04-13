package com.ingenico.assignment.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import com.ingenico.assignment.model.Account;

/**
 * JPA Data Repository
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
public interface DataRepository extends JpaRepository<Account, String> {

	/**
	 * This methods is used exclusively to get accounts during transfer request.
	 * Using LockModeType.PESSIMISTIC_WRITE to insure write exclusive lock
	 * @param accountNumber
	 * @return Account
	 */
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Account> findByNumber(String accountNumber);
}
