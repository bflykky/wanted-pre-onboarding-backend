package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
