package com.gtf.library.service;

import com.gtf.library.entity.Member;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.model.MemberDto;
import com.gtf.library.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public long addMember(final MemberDto memberDto) {
        return memberRepository.save(createMember(memberDto)).getId();
    }

    public List<Member> findMember(final String name) {
        return memberRepository.searchMember(name).stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if(result.isEmpty()) throw new NotFoundException("Unable to find member with search name: " + name);
                    return result;
                }));
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    private Member createMember(final MemberDto memberDto) {
        return modelMapper.map(memberDto, Member.class);
    }
}
