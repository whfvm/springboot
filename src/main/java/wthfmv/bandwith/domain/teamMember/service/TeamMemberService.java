package wthfmv.bandwith.domain.teamMember.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.dto.req.TeamMemberPutReq;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.teamMember.repository.TeamMemberRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void put(TeamMemberPutReq teamMemberPutReq, String userUUID) {
        Member member = memberRepository.findById(UUID.fromString(userUUID)).orElseThrow(
                () -> new RuntimeException("")
        );

        Team team = teamRepository.findById(UUID.fromString(teamMemberPutReq.getBandId())).orElseThrow(
                () -> new RuntimeException("")
        );

        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team).orElseThrow(
                () -> new RuntimeException("")
        );

        teamMember.updatePart(teamMemberPutReq.getPart());
    }
}
