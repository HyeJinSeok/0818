package team.woo.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepository {

    private static final Map<Long, Schedule> store = new HashMap<>();
    private static long sequence = 0L;

    public Schedule save(Schedule schedule) {
        schedule.setId(++sequence);
        store.put(schedule.getId(), schedule);
        return schedule;
    }

    public Schedule findById(Long id) {
        return store.get(id);
    }

    public List<Schedule> findAll() {
        return new ArrayList<>(store.values());
    }


    public void clearStore() {
        store.clear();
    }
}