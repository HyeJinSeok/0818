package team.woo.domain;

import lombok.Data;

@Data
public class ScheduleResult {
    private int adjustDays;
    private double adjustTime;

    public ScheduleResult(int adjustDays, double adjustTime) {
        this.adjustDays = adjustDays;
        this.adjustTime = adjustTime;
    }
}
