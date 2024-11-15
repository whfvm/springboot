package wthfmv.bandwith.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.team.dto.req.TeamCreateReq;
import wthfmv.bandwith.domain.team.dto.res.TeamCreateRes;
import wthfmv.bandwith.domain.team.dto.res.TeamListRes;
import wthfmv.bandwith.domain.team.dto.res.TeamRes;
import wthfmv.bandwith.domain.team.dto.res.TeamSignRes;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.teamMember.repository.TeamMemberRepository;
import wthfmv.bandwith.domain.track.entity.Track;
import wthfmv.bandwith.domain.track.repository.TrackRepository;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final TrackRepository trackRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public TeamCreateRes create(TeamCreateReq teamCreateReq) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        Team team = new Team(teamCreateReq);

        Team savedTeam = teamRepository.save(team);

        TeamMember teamMember = new TeamMember(Position.LEADER, member, savedTeam, null);

        teamMemberRepository.save(teamMember);

        return new TeamCreateRes(team.getId().toString(), team.getName());
    }

    @Transactional
    public void delete(String teamId, String userUUID) {
        if(teamMemberRepository.existsByPositionAndTeamIdAndMemberId(Position.LEADER, UUID.fromString(teamId), UUID.fromString(userUUID))){
            teamRepository.deleteById(UUID.fromString(teamId));
        } else {
            throw new RuntimeException("지우려는 팀의 리더가 아닙니다");
        }
    }

    @Transactional
    public String publish(String teamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if(teamMemberRepository.existsByPositionAndTeamIdAndMemberId(Position.LEADER, UUID.fromString(teamId),customUserDetails.getUuid())){
            String randomCode = RandomStringUtils.random(6, true, true);
            redisTemplate.opsForValue().set(randomCode, teamId, 30, TimeUnit.MINUTES);

            return randomCode;
        }

        return "!@#$%^";
    }

    @Transactional
    public TeamSignRes sign(String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        String teamId = (String) redisTemplate.opsForValue().get(code);

        if(teamId == null){
            throw new RuntimeException("Error: 가입 코드 찾을 수 없음: " + code);
        }

        Team team = teamRepository.findById(UUID.fromString(teamId)).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        if(teamMemberRepository.findByMemberAndTeam(member, team).isPresent()){
            throw new RuntimeException("이미 가입된 멤버입니다.");
        } else {
           teamMemberRepository.save(new TeamMember(Position.MEMBER, member, team, null));
           return new TeamSignRes(team.getId().toString(), team.getName());
        }
    }

    @Transactional(readOnly = true)
    public TeamRes team(String teamId) {
        Team team = teamRepository.findById(UUID.fromString(teamId)).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        List<Track> trackList = trackRepository.findByBandId(teamId);

        return new TeamRes(team, trackList);
    }

    @Transactional(readOnly = true)
    public List<TeamListRes> list(String userUUID) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMember(UUID.fromString(userUUID));

        return teamMembers.stream()
                .map(TeamListRes::new)
                .collect(Collectors.toList());
    }
}
