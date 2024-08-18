// Script for handling stress level visualization
document.addEventListener('DOMContentLoaded', function() {
    const stressSlider = document.getElementById('stressSlider');
    const stressCanvas = document.getElementById('stressCanvas');
    const stressValue = document.getElementById('stressValue');
    const ctx = stressCanvas.getContext('2d'); // 이 부분에서 'ctx'를 한 번만 선언

    const img = new Image();
    img.src = '/image/person.png'; // 이미지 경로는 실제 경로로 변경해야 합니다.

    stressSlider.addEventListener('input', () => {
        const level = parseInt(stressSlider.value, 10); // 문자열을 숫자로 변환
        stressValue.textContent = `스트레스 수준: ${level}`;
        drawStressLevel(level);
    });

    img.onload = () => {
        drawStressLevel(parseInt(stressSlider.value, 10)); // 문자열을 숫자로 변환
    };

    function drawStressLevel(level) {
        ctx.clearRect(0, 0, stressCanvas.width, stressCanvas.height);
        ctx.drawImage(img, 0, 0, stressCanvas.width, stressCanvas.height);

        const height = (level / 10) * stressCanvas.height;
        ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
        ctx.fillRect(0, stressCanvas.height - height, stressCanvas.width, height);
    }
});