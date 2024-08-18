
package team.woo.algorithm;

import org.springframework.stereotype.Service;
import team.woo.domain.Schedule;
import team.woo.domain.ScheduleResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TaskAllocation {

    // 총 점수를 계산하는 메서드
    public static int calculateTotalScore(int difficulty, int urgency, int importance, int stress) {
        int totalScore = (difficulty * 2) + (urgency * 3) + (importance * 3) + ((11 - stress) * 1);
        return totalScore;
    }

    // adjustDays 를 계산하는 메서드
    public static int calculateAdjustDays(int availableDays, String option) {
        int adjustDays = 0;
        switch (option) {
            case "concentrate":
                adjustDays = concentrateDays(availableDays);
                break;
            case "distribute":
                adjustDays = distributeDays(availableDays);
                break;
            case "flexible":
                adjustDays = flexibleDays(availableDays);
                break;
            default:
                adjustDays = 0;
                break;
        }

        return adjustDays;
    }

    // 집중 일정
    private static int concentrateDays(int availableDays) {
        int minDays = availableDays / 3;
        int maxDays = availableDays / 2;
        return maxDays;
    }

    // 분산 일정
    private static int distributeDays(int availableDays) {
        return (int) (availableDays / 2 + availableDays) / 2;
    }

    // 유동 일정은 임시
    private static int flexibleDays(int availableDays) {
        int temple = 1004;
        return temple;
    }


    // adjustTime 을 계산하는 메서드
    public static double calculateAdjustTime(int totalScore) {
        double adjustTime = 0.0;
        adjustTime = calculateTimeFromScore(totalScore);
        return adjustTime;
    }


    // 총점수에 따른 adjustTime 도출
    private static double calculateTimeFromScore(int totalScore) {
        double adjustTime;
        if (totalScore <= 9) {
            adjustTime = 0.5; // 30분
        } else if (totalScore <= 14) {
            adjustTime = 0.75; // 45분
        } else if (totalScore <= 19) {
            adjustTime = 1.0; // 1시간
        } else if (totalScore <= 24) {
            adjustTime = 1.25; // 1시간 15분
        } else if (totalScore <= 29) {
            adjustTime = 1.5; // 1시간 30분
        } else if (totalScore <= 34) {
            adjustTime = 1.75; // 1시간 45분
        } else if (totalScore <= 39) {
            adjustTime = 2.0; // 2시간
        } else if (totalScore <= 44) {
            adjustTime = 2.25; // 2시간 15분
        } else if (totalScore <= 49) {
            adjustTime = 2.5; // 2시간 30분
        } else if (totalScore <= 54) {
            adjustTime = 2.75; // 2시간 45분
        } else if (totalScore <= 59) {
            adjustTime = 3.0; // 3시간
        } else if (totalScore <= 64) {
            adjustTime = 3.25; // 3시간 15분
        } else if (totalScore <= 69) {
            adjustTime = 3.5; // 3시간 30분
        } else if (totalScore <= 74) {
            adjustTime = 3.75; // 3시간 45분
        } else if (totalScore <= 79) {
            adjustTime = 4.0; // 4시간
        } else if (totalScore <= 84) {
            adjustTime = 4.25; // 4시간 15분
        } else if (totalScore <= 89) {
            adjustTime = 4.5; // 4시간 30분
        } else {
            adjustTime = 4.5; // 최대 4시간 30분으로 제한
        }

        return adjustTime;
    }
}




