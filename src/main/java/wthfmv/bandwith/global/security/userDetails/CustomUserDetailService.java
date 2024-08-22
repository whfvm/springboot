package wthfmv.bandwith.global.security.userDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 이 메소드 사용하지 말 것
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Use loadUserByUUID(UUID uuid) instead");
    }

    @Transactional
    public UserDetails loadUserByUUID(UUID uuid) throws UsernameNotFoundException {

        Member member = memberRepository.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));

        return new CustomUserDetails(
                member.getProvider(),
                member.getId()
        );
    }
}
