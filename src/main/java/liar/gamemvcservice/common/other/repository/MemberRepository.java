package liar.gamemvcservice.common.other.repository;

import liar.gamemvcservice.common.other.dao.MemberNameOnly;
import liar.gamemvcservice.common.other.dao.MemberIdOnly;
import liar.gamemvcservice.common.other.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    MemberNameOnly findProjectionByUserId(String userId);

    List<MemberIdOnly> findProjectionByUsername(String userName);
}
