package com.yeahpeu.backend.member.controller;

import com.yeahpeu.backend.common.dto.ListResponse;
import com.yeahpeu.backend.member.controller.request.MemberCreateRequest;
import com.yeahpeu.backend.member.controller.response.MemberCreateResponse;
import com.yeahpeu.backend.member.controller.response.MemberResponse;
import com.yeahpeu.backend.member.service.MemberService;
import com.yeahpeu.backend.member.service.command.MemberCreateCommand;
import com.yeahpeu.backend.member.service.dto.MemberCreateDto;
import com.yeahpeu.backend.member.service.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<MemberCreateResponse> createMember(@RequestBody MemberCreateRequest req) {
        MemberCreateCommand command = MemberCreateCommand.of(req.name(), req.email(), req.password());
        MemberCreateDto dto = memberService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberCreateResponse.of(dto.id(), dto.name(), dto.email()));
    }

    @GetMapping("/list")
    public ResponseEntity<ListResponse<MemberResponse>> memberList() {
        List<MemberDto> memberDtos = memberService.findAll();
        return ResponseEntity.ok(
                ListResponse.of(
                        memberDtos.stream()
                                .map(MemberResponse::from)
                                .toList()
                )
        );
    }
}
