package com.motoo.server.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    // 등록 날짜
    @CreatedDate
    @Column(name = "register_date", updatable = false)
    private LocalDateTime registerDate;

    // 수정 날짜
    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

}
