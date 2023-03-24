package com.mumy.basicboard.repository;

import com.mumy.basicboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
