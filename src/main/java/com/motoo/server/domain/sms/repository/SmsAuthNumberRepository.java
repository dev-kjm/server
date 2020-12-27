package com.motoo.server.domain.sms.repository;

import com.motoo.server.domain.sms.domain.SmsAuthNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsAuthNumberRepository extends CrudRepository<SmsAuthNumber, Long> {
    Optional<SmsAuthNumber> findByPhoneNoAndAuthNo(String id, String AuthNo);
}
