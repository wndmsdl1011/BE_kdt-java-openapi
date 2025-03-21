package com.example.newsapi.disaster;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "disaster_messages")
@Getter
@Setter
@NoArgsConstructor
public class DisasterMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sn", unique = true, nullable = false)
    private Long sn;

    @Column(name = "msg_cn", columnDefinition = "CLOB")
    private String msgCn;

    @Column(name = "rcptn_rgn_nm", columnDefinition = "CLOB")
    private String rcptnRgnNm;

    @Column(name = "crt_dt")
    private String crtDt;

    @Column(name = "reg_ymd")
    private String regYmd;

    @Column(name = "emrg_step_nm")
    private String emrgStepNm;

    @Column(name = "dst_se_nm")
    private String dstSeNm;

    @Column(name = "mdfcn_ymd")
    private String mdfcnYmd;
}
