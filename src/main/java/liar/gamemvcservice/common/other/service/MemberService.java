package liar.gamemvcservice.common.other.service;

import liar.gamemvcservice.common.other.dao.MemberNameOnly;
import liar.gamemvcservice.common.other.repository.MemberRepository;
import liar.gamemvcservice.common.other.dao.MemberIdOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberIdOnly> findByUsername(String username) {
        return memberRepository.findProjectionByUsername(username);
    }

    public MemberNameOnly findUsernameById(String userId) {
        return memberRepository.findProjectionByUserId(userId);
    }

}
