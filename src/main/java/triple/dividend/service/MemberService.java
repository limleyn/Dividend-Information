package triple.dividend.service;

import triple.dividend.exception.impl.AlreadyExistUserException;
import triple.dividend.model.Auth;
import triple.dividend.persist.MemberRepository;
import triple.dividend.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }
    public MemberEntity register(Auth.SignUp member) {

        boolean exists = false;
        if (exists) {
            throw new AlreadyExistUserException();
        }

        throw new NotYetImplementedException();
    }

    public MemberEntity authenticate(Auth.SignIn member) {

        throw new NotYetImplementedException();
    }
}
