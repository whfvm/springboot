package wthfmv.bandwith.domain.member.dto.res;

import lombok.Data;
import wthfmv.bandwith.domain.member.entity.Member;

import java.time.LocalDate;

@Data
public class MemberRes {
    private String name;
    private LocalDate birth;
    private String introduce;

    public MemberRes(Member member){
        this.name = member.getName();
        this.birth = member.getBirth();
        this.introduce = member.getIntroduce();
    }
}
