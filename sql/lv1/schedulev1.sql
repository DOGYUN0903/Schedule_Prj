create table scheduleV1(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    writer VARCHAR(100) NOT NULL COMMENT '작성자',
    title VARCHAR(100) NOT NULL COMMENT '제목',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    contents VARCHAR(255) NOT NULL Comment '작성 내용',
    createAt DATETIME NOT NULL COMMENT '생성 시간',
    modifiedAt DATETIME NOT NULL COMMENT '수정 시간'
)