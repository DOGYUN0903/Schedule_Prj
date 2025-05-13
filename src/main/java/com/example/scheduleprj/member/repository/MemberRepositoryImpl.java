package com.example.scheduleprj.member.repository;

import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Member findMemberByIdOrElseThrow(Long id) {
        List<Member> result = jdbcTemplate.query("select * from members where id = ?", MemberRowMapper(), id);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<Member> MemberRowMapper() {
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }
}
