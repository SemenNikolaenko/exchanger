package com.example.exchanger.repository;

import com.example.exchanger.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account,Long> {
    @EntityGraph(value ="getAccountWithOrders")
    @Query("select i from Account i where i.id=?1")
    public Account findAccountById(Long id);
}
