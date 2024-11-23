package wthfmv.bandwith.domain.teamMember.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.dto.req.TeamMemberPutReq;
import wthfmv.bandwith.domain.teamMember.entity.Position;
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
    public void put(TeamMemberPutReq teamMemberPutReq, UUID userUUID) {
        Member member = memberRepository.findById(userUUID).orElseThrow(
                () -> new RuntimeException("해당 멤버 없음")
        );

        Team team = teamRepository.findById(UUID.fromString(teamMemberPutReq.getBandId())).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team).orElseThrow(
                () -> new RuntimeException("해당 팀에 가입되어 있지 않음")
        );

        teamMember.updatePart(teamMemberPutReq.getPart());
    }

    @Transactional
    public void delete(String bandId, UUID uuid) {
        teamMemberRepository.deleteByPositionAndMemberId(Position.MEMBER, uuid);
    }
}
