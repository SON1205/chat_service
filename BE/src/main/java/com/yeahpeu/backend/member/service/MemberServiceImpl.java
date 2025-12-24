package com.yeahpeu.backend.member.service;

import static com.yeahpeu.backend.common.exception.ExceptionCode.ALREADY_EXIST_EMAIL;

import com.yeahpeu.backend.common.exception.AlreadyExistsException;
import com.yeahpeu.backend.member.domain.Member;
import com.yeahpeu.backend.member.repository.MemberRepository;
import com.yeahpeu.backend.member.service.command.MemberCreateCommand;
import com.yeahpeu.backend.member.service.dto.MemberCreateDto;
import com.yeahpeu.backend.member.service.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public MemberCreateDto create(MemberCreateCommand command) {
        // 동시성 이슈
        // exist vs find
        if (memberRepository.existsByEmail(command.email())) {
            throw new AlreadyExistsException(ALREADY_EXIST_EMAIL, command.email());
        }

        Member newMember = Member.builder()
                .name(command.name())
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .build();
        Member member = memberRepository.save(newMember);
        return MemberCreateDto.of(member.getId(), member.getName(), member.getEmail());
    }

//    public Member login(MemberLoginDto dto) {
//        Member member = memberRepository.findByEmail(dto.getEmail())
//                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일입니다."));
//        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//        return member;
//    }

    @Override
    public List<MemberDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberDto::from)
                .toList();
    }
}

