package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽을 전용 모드 -> 성능 최적화, 등록/변경/삭제 예방
@RequiredArgsConstructor    // final 필드들의 생성자 주입 -> memberRepository 의존성 주입 해줌
public class MemberService {

    private final MemberRepository memberRepository;
    /**
     * 회원가입
     */
    @Transactional  // 따로 설정해 두어서 우선권을 가짐. 따라서 readOnly = false 가됨.
    public Long join(Member member){
        ValidateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void ValidateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId){
         return memberRepository.findOne(memberId);
    }

}
