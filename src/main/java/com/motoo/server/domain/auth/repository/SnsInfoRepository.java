package com.motoo.server.domain.auth.repository;

import com.motoo.server.domain.auth.domain.SnsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsInfoRepository extends JpaRepository<SnsInfo,Long> {
    Optional<SnsInfo> findBySnsProviderAndSnsId(String provider, String snsId);

    boolean existsBySnsProviderAndSnsId(String provider, String snsId);
}
