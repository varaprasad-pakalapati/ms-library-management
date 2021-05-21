package com.gtf.library.controller;

import com.gtf.library.entity.Member;
import com.gtf.library.model.MemberDto;
import com.gtf.library.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@Slf4j
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/member")
    public ResponseEntity<Void> addMember(@RequestBody @Valid MemberDto memberDto) {
        log.info("Add new member: Request received to add a new member with data {}", memberDto);
        final long id = memberService.addMember(memberDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/member")
    public ResponseEntity<List<Member>> searchMember(@RequestParam final String name) {
        return ResponseEntity.ok(memberService.findMember(name));
    }

    @GetMapping(value = "/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
}
