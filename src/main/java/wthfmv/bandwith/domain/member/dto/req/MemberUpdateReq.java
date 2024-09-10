package wthfmv.bandwith.domain.member.dto.req;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberUpdateReq {
    private LocalDate birth;
    private String name;
    private String introduce;
}
