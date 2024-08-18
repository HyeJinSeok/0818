package team.woo.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Schedule {

    private Long id;
    private String name;
    private LocalDate startTime;
    private LocalDate deadLine;
    private int difficulty;
    private int importance; // 중요도
    private int urgency;    // 긴급도
    private String restTime;
    private int stress;
    private String preferenceTime;

    // Getters and setters
    @Setter
    @Getter
    private List<String> selectedDates;

    // 새로 추가된 필드
    private int adjustDays;
    private double adjustTime;

    public Schedule() {
    }

    public Schedule(String name, LocalDate startTime, LocalDate deadLine, int difficulty,
                    int importance, int urgency, String restTime, String preferenceTime, int stress) {
        this.name = name;
        this.startTime = startTime;
        this.deadLine = deadLine;
        this.difficulty = difficulty;
        this.importance = importance;
        this.urgency = urgency;
        this.restTime = restTime;
        this.preferenceTime = preferenceTime;
        this.stress = stress;
    }

}