package team.woo.controller;

import org.springframework.stereotype.Controller;
import team.woo.algorithm.TaskAllocation;
import team.woo.domain.Schedule;
import team.woo.domain.ScheduleRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.text.html.StyleSheet;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ScheduleRepository scheduleRepository; // Schedule 객체를 저장하고 조회하기 위한 Repository
    private StyleSheet redirectAttributes; // 잘못된 타입, 실제로는 RedirectAttributes 사용


    /**
     * 홈페이지를 보여주는 메소드
     * @return "index" 뷰의 이름
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "메인 페이지 입니다.");
        return "index";
    }


    /**
     * 모든 일정을 조회하여 cabinet 페이지에 전달하는 메소드
     * @return "/cabinet" 뷰의 이름
     */
    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "/cabinet";
    }


    /**
     * Check 페이지를 보여주는 메소드
     * @return "check" 뷰의 이름
     */
    @GetMapping("/check")
    public String check(Model model) {
        model.addAttribute("pageTitle", "Check 페이지 입니다.");
        return "check";
    }


    /**
     * About 페이지를 보여주는 메소드
     * @return "about" 뷰의 이름
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About 페이지 입니다.");
        return "about";
    }


    /**
     * 새로운 일정을 추가하기 위한 폼을 보여주는 메소드
     * @return "create" 뷰의 이름
     */
    @GetMapping("/create")
    public String add() {
        return "create";
    }


    /**
     * 새로운 일정을 저장하는 메소드
     * @param startTime 일정의 시작 날짜
     * @param deadLine 일정의 종료 날짜
     * @param schedule 저장할 일정 객체
     * @param redirectAttributes 리다이렉트 시 URL 파라미터 추가
     * @return 일정 상세 페이지로 리다이렉트
     */
    @PostMapping("/create")
    public String addSchedule(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("deadLine") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadLine,
            Schedule schedule, RedirectAttributes redirectAttributes) {

        // LocalDate를 LocalDateTime으로 변환 (시간은 자정으로 설정)
        LocalDateTime startDateTime = startTime.atStartOfDay();
        LocalDateTime deadlineDateTime = deadLine.atStartOfDay();

        // schedule 객체에 시작 시간과 데드라인을 설정
        schedule.setStartTime(startTime); // LocalDate로 설정
        schedule.setDeadLine(deadLine);   // LocalDate로 설정

        // schedule 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 저장된 schedule의 ID를 URL 파라미터로 전달하여 리다이렉트
        redirectAttributes.addAttribute("id", savedSchedule.getId());
        return "redirect:/result/{id}";
    }


    /**
     * 일정의 결과를 보여주는 페이지로 리다이렉트
     * @param id 조회할 일정의 ID
     * @param model 뷰에 데이터를 전달하기 위한 Model 객체
     * @return "result" 뷰의 이름
     */
    @GetMapping("/result/{id}")
    public String result(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleRepository.findById(id);
        model.addAttribute("schedule", schedule);
        return "result";
    }


    /**
     * 특정 일정을 선택하고 작업을 선택하는 페이지로 이동
     * @param id 조회할 일정의 ID
     * @param model 뷰에 데이터를 전달하기 위한 Model 객체
     * @return "select" 뷰의 이름
     */
    @GetMapping("/select/{id}")
    public String task(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleRepository.findById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("id", id);
        return "select";
    }


    /**
     * 선택된 옵션에 따라 일정 조정 결과를 저장하고 결과 페이지로 리다이렉트
     * @param option 사용자가 선택한 옵션
     * @param id 일정의 ID
     * @param redirectAttributes 리다이렉트 시 URL 파라미터 추가
     * @return 조정 결과 페이지로 리다이렉트
     */
    @PostMapping("/select")
    public String selectOption(@RequestParam("option") String option, @RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Schedule schedule = scheduleRepository.findById(id);

        // 일정 계산
        int totalScore = TaskAllocation.calculateTotalScore(schedule.getDifficulty(), schedule.getUrgency(), schedule.getImportance(), schedule.getStress());

        LocalDate deadLine = schedule.getDeadLine();
        LocalDate startTime = schedule.getStartTime();
        int availableDays = (int) (deadLine.toEpochDay() - startTime.toEpochDay());
        int adjustDays = TaskAllocation.calculateAdjustDays(availableDays, option);
        double adjustTime = TaskAllocation.calculateAdjustTime(totalScore);

        // 계산된 조정일수와 조정시간을 일정에 설정
        schedule.setAdjustDays(adjustDays);
        schedule.setAdjustTime(adjustTime);
        scheduleRepository.save(schedule);

        // 리다이렉트 URL에 id를 추가
        redirectAttributes.addAttribute("id", id);

        // 리다이렉트
        return "redirect:/result_ts/{id}";
    }


    /**
     * 조정된 일정 결과를 보여주는 페이지
     * @param id 조회할 일정의 ID
     * @param model 뷰에 데이터를 전달하기 위한 Model 객체
     * @return "result_ts" 뷰의 이름
     */
    @GetMapping("/result_ts/{id}")
    public String resultTs(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleRepository.findById(id);

        model.addAttribute("schedule", schedule);
        model.addAttribute("adjustDays", schedule.getAdjustDays());
        model.addAttribute("adjustTime", schedule.getAdjustTime());

        return "result_ts";
    }


    /**
     * 일정의 조정된 결과와 일정 정보를 달력 체크 페이지에 전달
     * @param id 조회할 일정의 ID
     * @param model 뷰에 데이터를 전달하기 위한 Model 객체
     * @return "calendarCheck" 뷰의 이름
     */
    @GetMapping("/calendarCheck/{id}")
    public String calendarCheck(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleRepository.findById(id);

        model.addAttribute("adjustDays", schedule.getAdjustDays());
        model.addAttribute("adjustTime", schedule.getAdjustTime());
        model.addAttribute("startTime", schedule.getStartTime());
        model.addAttribute("deadLine", schedule.getDeadLine());
        model.addAttribute("id", id);
        model.addAttribute("scheduleName", schedule.getName()); // 추가

        return "calendarCheck";
    }


    /**
     * 달력 페이지를 보여주는 메소드
     * @param dates 선택된 날짜들 (문자열로 구분됨)
     * @param scheduleName 일정의 이름
     * @param model 뷰에 데이터를 전달하기 위한 Model 객체
     * @return "calendar" 뷰의 이름
     */
    @GetMapping("/calendar")
    public String calendar(@RequestParam(value = "dates", required = false) String dates,
                           @RequestParam(value = "scheduleName", required = false) String scheduleName,
                           Model model) {
        if (dates != null && !dates.isEmpty()) {
            String[] selectedDates = dates.split(",");
            model.addAttribute("selectedDates", selectedDates);
            model.addAttribute("scheduleName", scheduleName); // 추가
        }
        return "calendar"; // calendar.html 템플릿으로 이동
    }

}
