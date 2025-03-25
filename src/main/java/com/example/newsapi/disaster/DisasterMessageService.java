package com.example.newsapi.disaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisasterMessageService {
    private final DisasterMessageRepository disasterMessageRepository;

    @Autowired
    public DisasterMessageService(DisasterMessageRepository disasterMessageRepository) {
        this.disasterMessageRepository = disasterMessageRepository;
    }

    public Page<DisasterMessage> findAllByOrderByCrtDtDesc(Pageable pageable) {
        return disasterMessageRepository.findAllByOrderByCrtDtDesc(pageable);
    }

    public Page<DisasterMessage> searchDisasterMessages(String keyword, Pageable pageable) {
        return disasterMessageRepository.findByMsgCnContainingOrderByCrtDtDesc(keyword, pageable);
    }
}
