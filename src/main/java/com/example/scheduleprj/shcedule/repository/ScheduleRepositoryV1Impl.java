package com.example.scheduleprj.shcedule.repository;

import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScheduleRepositoryV1의 JDBC 구현체입니다.
 * - 일정의 저장, 조회, 수정, 삭제 기능을 수행합니다.
 */
@Repository
public class ScheduleRepositoryV1Impl implements ScheduleRepositoryV1 {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryV1Impl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 일정 저장
     * - SimpleJdbcInsert를 통해 자동으로 insert 쿼리를 생성하고 실행
     * - 생성된 PK(id)를 반환 받아 ScheduleResponseDto로 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedules")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", schedule.getMemberId());
        parameters.put("title", schedule.getTitle());
        parameters.put("contents", schedule.getContents());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("modifiedAt", schedule.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getMemberId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getModifiedAt());
    }

    /**
     * 일정 전체 조회
     * - 조건: memberId, modifiedAt (nullable)
     * - 페이징 처리 포함 (LIMIT / OFFSET)
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt, int page, int size) {
        // offset: 앞에서 건너뛸 데이터 수를 계산
        // 예: page = 2, size = 10이면 -> offset = 20 (즉, 21번째 데이터부터 시작)
        int offset = page * size;

        if (memberId != null && modifiedAt == null) {
            return jdbcTemplate.query("SELECT * FROM schedules WHERE member_id = ? ORDER BY modified_at DESC LIMIT ? OFFSET ?",
                    scheduleMapper(), memberId, size, offset); // size : 가져올 개수(한 페이지에 몇개를 보여줄건가?), offset : 앞에서 건너뛸 개수(앞에서 몇개 건너뛸건가?)
        } else if (memberId == null && modifiedAt != null) {
            return jdbcTemplate.query("SELECT * FROM schedules WHERE DATE(modified_at) = ? ORDER BY modified_at DESC LIMIT ? OFFSET ?",
                    scheduleMapper(), modifiedAt, size, offset);
        } else if (memberId != null && modifiedAt != null) {
            return jdbcTemplate.query("SELECT * FROM schedules WHERE member_id = ? AND DATE(modified_at) = ? ORDER BY modified_at DESC LIMIT ? OFFSET ?",
                    scheduleMapper(), memberId, modifiedAt, size, offset);
        } else {
            return jdbcTemplate.query("SELECT * FROM schedules ORDER BY modified_at DESC LIMIT ? OFFSET ?",
                    scheduleMapper(), size, offset);
        }
    }

    /**
     * 일정 단건 조회
     * - ID로 조회하고, 없을 경우 404 예외 반환
     */
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleMapperV2(), id);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    /**
     * 일정 수정
     * - 제목, 내용, 수정일 갱신
     * - 수정된 행 수 반환
     */
    @Override
    public int updateSchedule(Long id, String title, String contents) {
        return jdbcTemplate.update("update schedules set contents = ? , title = ?, modified_at = ? where id = ?", contents, title, LocalDateTime.now(), id);
    }

    /**
     * 일정 삭제
     * - 삭제된 행 수 반환
     */
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedules where id = ? " , id);
    }

    /**
     * ScheduleResponseDto 매핑용 RowMapper
     * - 클라이언트에 전달할 조회 결과 DTO로 매핑
     */
    private RowMapper<ScheduleResponseDto> scheduleMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }

    /**
     * Schedule 엔티티 매핑용 RowMapper
     * - 서비스 및 내부 로직에서 사용할 도메인 객체로 변환
     */
    private RowMapper<Schedule> scheduleMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }
}
