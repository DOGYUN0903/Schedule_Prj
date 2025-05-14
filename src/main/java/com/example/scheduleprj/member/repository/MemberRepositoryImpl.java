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

/**
 * 회원 저장소 구현체 (JDBC 방식)
 * - DB에 직접 접근하여 회원 데이터를 삽입하거나 조회하는 역할
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    // 생성자 매개변수값이 Datasource이므로 @RequiredArgsConstructor 사용x
    // 자동으로 Autowired 해줍니다. 생성자 1개밖에 없기 때문에
    public MemberRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 회원을 DB에 저장합니다.
     * - SimpleJdbcInsert를 사용하여 자동으로 INSERT 쿼리를 구성하고 실행
     * - 생성된 PK(id)를 반환받아 응답 DTO로 생성해 반환
     *
     * @param member 저장할 회원 객체
     * @return 저장된 회원 정보를 담은 응답 DTO
     */
    @Override
    public MemberResponseDto createMember(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("members") // 테이블명 지정
                .usingGeneratedKeyColumns("id"); // 자동 생성되는 PK 컬럼 지정

        // INSERT에 사용할 컬럼값들 설정
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        parameters.put("created_at", member.getCreatedAt());
        parameters.put("modified_at", member.getModifiedAt());

        // INSERT 실행 후 생성된 ID(PK) 반환
        Number key = jdbcInsert.executeAndReturnKey(parameters);

        // 응답 DTO 생성 및 반환
        return new MemberResponseDto(
                key.longValue(),
                member.getName(),
                member.getEmail(),
                member.getModifiedAt()
        );
    }

    /**
     * ID를 기반으로 회원을 조회합니다.
     * - 결과가 없으면 404 NOT_FOUND 예외 발생
     *
     * @param id 조회할 회원 ID
     * @return 조회된 회원 엔티티
     */
    @Override
    public Member findMemberByIdOrElseThrow(Long id) {
        List<Member> result = jdbcTemplate.query("select * from members where id = ?", MemberRowMapper(), id);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    /**
     * ResultSet을 Member 객체로 매핑하는 RowMapper입니다.
     * - 각 컬럼 값을 Member 생성자에 주입
     */
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
