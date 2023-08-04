-- 테이블의 제약조건 삭제
alter table post drop foreign key const_foreign_key_post_to_member;

-- ========================================================================

-- 테이블 삭제
drop table if exists member;

drop table if exists post;

-- ========================================================================

-- 테이블 생성
create table member (
    member_id bigint not null auto_increment,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key (member_id)
) engine=InnoDB;

create table post (
    post_id bigint not null auto_increment,
    writer varchar(255) not null,
    write_date datetime(6) not null,
    title varchar(255),
    content TEXT,
    member_id bigint not null,
    primary key (post_id)
) engine=InnoDB;

-- ========================================================================

-- 테이블 제약조건 생성

alter table post add constraint const_foreign_key_post_to_member
   foreign key (member_id) references member (member_id);