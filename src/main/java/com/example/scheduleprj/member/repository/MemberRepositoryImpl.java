package com.example.scheduleprj.member.repository;

import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemberResponseDto createMember(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        parameters.put("created_at", member.getCreatedAt());
        parameters.put("modified_at", member.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(parameters);

        return new MemberResponseDto(
                key.longValue(),
                member.getName(),
                member.getEmail(),
                member.getModifiedAt()
        );
    }
}
