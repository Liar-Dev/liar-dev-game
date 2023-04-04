package liar.gamemvcservice.common.other.domain;

import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(STRING)
    private Authorities authorities;

    @Builder
    public Authority(Member member, Authorities authorities) {
        this.member = member;
        this.authorities = authorities;
    }
}
