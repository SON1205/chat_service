package com.yeahpeu.backend.member.service;

import com.yeahpeu.backend.member.service.command.MemberCreateCommand;
import com.yeahpeu.backend.member.service.dto.MemberCreateDto;
import com.yeahpeu.backend.member.service.dto.MemberDto;
import java.util.List;

public interface MemberService {
    MemberCreateDto create(MemberCreateCommand command);

    List<MemberDto> findAll();
}
