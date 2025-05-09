package com.example.newsapi.disaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisasterMessageRepository extends JpaRepository<DisasterMessage, Long> {
    Page<DisasterMessage> findAllByOrderByCrtDtDesc(Pageable pageable);

    Page<DisasterMessage> findByMsgCnContainingOrderByCrtDtDesc(String keyword, Pageable pageable);
}
