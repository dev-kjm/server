package com.motoo.server.domain.member.application;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.FindMemberResponse;
import com.motoo.server.domain.member.dto.MemberExistenceType;
import com.motoo.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public boolean isExistTarget(MemberExistenceType type, String value) {
        switch (type) {
            case EMAIL:
                return memberRepository.existsByEmail(value);
            case NICKNAME:
                return memberRepository.existsByNickname(value);
            default:
                throw new IllegalArgumentException(String.format("%s is not valid", type.name()));
        }
    }

    public FindMemberResponse findByMemberNo(Long memberNo) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow();

        return new FindMemberResponse(findMember);
    }
}
