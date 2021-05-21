package com.gtf.library.repository;

import com.gtf.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("Select m from Member m where m.name = ?1")
    List<Member> searchMember(String memberName);

    @Modifying
    @Query("Update Member m set m.totalBooksCheckout = ?1 where m.id = ?2")
    void updateBooksCheckedOut(final int totalBooksCheckout, final long id);
}
