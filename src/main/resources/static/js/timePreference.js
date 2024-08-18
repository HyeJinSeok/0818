const timeOptions = document.querySelectorAll('.time-option');
const preferredTimesInput = document.getElementById('preferenceTime');
let preferredTimes = [];

timeOptions.forEach(option => {
    option.addEventListener('click', () => {
        const time = option.getAttribute('data-time');
        if (preferredTimes.includes(time)) {
            preferredTimes = preferredTimes.filter(t => t !== time);
            option.classList.remove('selected');
        } else {
            preferredTimes.push(time);
            option.classList.add('selected');
        }
        preferredTimesInput.value = preferredTimes.join(',');
    });
});