package com.example.scheduleprj.lv1.repository;

import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;
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
import java.util.Optional;

@Repository
public class ScheduleRepositoryV1Impl implements ScheduleRepositoryV1 {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryV1Impl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("scheduleV1")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", schedule.getWriter());
        parameters.put("title", schedule.getTitle());
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("modifiedAt", schedule.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getTitle(),
                schedule.getWriter(),
                schedule.getContents(),
                schedule.getModifiedAt());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String writer, String modifiedAt) {
        if (writer != null && modifiedAt == null) {
            return jdbcTemplate.query("SELECT * FROM scheduleV1 WHERE writer = ? ORDER BY modifiedAt DESC", scheduleMapper(), writer);
        } else if (writer == null && modifiedAt != null) {
            return jdbcTemplate.query("SELECT * FROM scheduleV1 WHERE DATE(modifiedAt) = ? ORDER BY modifiedAt DESC", scheduleMapper(), modifiedAt);
        } else if (writer != null && modifiedAt != null) {
            return jdbcTemplate.query("SELECT * FROM scheduleV1 WHERE writer = ? AND DATE(modifiedAt) = ? ORDER BY modifiedAt DESC", scheduleMapper(), writer, modifiedAt);
        } else {
            return jdbcTemplate.query("SELECT * FROM scheduleV1 ORDER BY modifiedAt DESC", scheduleMapper());
        }
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedulev1 where id = ?", scheduleMapperV2(), id);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String writer, String contents) {
        return jdbcTemplate.update("update schedulev1 set contents = ? , writer = ?, modifiedAt = ? where id = ?", contents, writer, LocalDateTime.now(), id);
    }

    private RowMapper<ScheduleResponseDto> scheduleMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
    }
}
