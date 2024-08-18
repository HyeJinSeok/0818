document.addEventListener('DOMContentLoaded', function() {
    const adjustDays = parseInt(document.getElementById('adjustDays').textContent, 10);
    const adjustTime = document.getElementById('adjustTime').textContent;

    // Thymeleaf에서 전달받은 startTime과 deadline 값을 JavaScript에서 사용
    const startDateStr = document.getElementById('startDate').value;
    const endDateStr = document.getElementById('endDate').value;

    // 날짜 객체 생성
    let startDate = new Date(startDateStr);
    let endDate = new Date(endDateStr);

    // 시간을 조정하여 날짜 범위 정확하게 설정
    startDate.setHours(0, 0, 0, 0); // 시작일을 자정으로 설정
    endDate.setHours(23, 59, 59, 999); // 종료일을 하루의 끝으로 설정

    window.calendarConfig = {
        adjustDays: adjustDays,
        adjustTime: adjustTime
    };

    initializeCalendar(startDate, endDate);
});

function initializeCalendar(startDate, endDate) {
    const calendarContainer = document.querySelector('.calendar');
    const daysInMonth = new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getDate(); // 현재 월의 마지막 날까지 생성
    const days = Array.from({ length: daysInMonth }, (_, i) => i + 1);

    // 기존 날짜 셀 제거
    calendarContainer.querySelectorAll('div:not(.header)').forEach(el => el.remove());

    days.forEach(day => {
        const cell = document.createElement('div');
        cell.textContent = day;

        // 날짜 객체 생성
        const cellDate = new Date(new Date().getFullYear(), new Date().getMonth(), day);

        // 하이라이트 범위 내의 날짜인지 확인
        if (cellDate >= startDate && cellDate <= endDate) {
            cell.classList.add('highlight');
        }

        cell.addEventListener('click', handleClick);
        calendarContainer.appendChild(cell);
    });
}

function handleClick(event) {
    const cell = event.currentTarget;
    const maxChecks = window.calendarConfig.adjustDays;

    if (!cell.classList.contains('highlight')) {
        return;
    }

    if (cell.classList.contains('checked')) {
        cell.classList.remove('checked');
        const icon = cell.querySelector('.check-icon');
        if (icon) {
            icon.remove();
        }
        return;
    }

    const checkedCount = document.querySelectorAll('.calendar .checked').length;

    if (checkedCount >= maxChecks) {
        return;
    }

    cell.classList.add('checked');
    const icon = document.createElement('div');
    icon.classList.add('check-icon', 'visible');
    icon.innerHTML = '✔';
    cell.appendChild(icon);
}