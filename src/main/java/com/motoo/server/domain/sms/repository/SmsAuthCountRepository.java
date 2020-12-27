package com.motoo.server.domain.sms.repository;

import com.motoo.server.domain.sms.domain.SmsAuthInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsAuthCountRepository extends CrudRepository<SmsAuthInfo, String> {

}
