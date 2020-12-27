package com.motoo.server.domain.member.repository;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.model.MemberState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    
    Optional<Member> findByEmailAndMemberState(String email, MemberState state);

    Optional<Member> findByNickname(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
