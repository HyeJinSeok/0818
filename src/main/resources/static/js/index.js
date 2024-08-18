// Priority Canvas Drawing Script
const canvas = document.getElementById('priorityCanvas');
const ctx = canvas.getContext('2d');
let dragging = false;
let point = { x: 20, y: 380 };

function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = 'black';
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    // Draw axes
    ctx.strokeStyle = 'white';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(20, 0);
    ctx.lineTo(20, 400);
    ctx.moveTo(0, 380);
    ctx.lineTo(400, 380);
    ctx.stroke();

    // Draw arrows
    ctx.beginPath();
    ctx.moveTo(20, 0);
    ctx.lineTo(15, 10);
    ctx.moveTo(20, 0);
    ctx.lineTo(25, 10);
    ctx.moveTo(400, 380);
    ctx.lineTo(390, 375);
    ctx.moveTo(400, 380);
    ctx.lineTo(390, 385);
    ctx.stroke();

    // Draw labels
    ctx.fillStyle = 'white';
    ctx.font = '16px Arial';
    ctx.fillText('긴급도', 25, 15);
    ctx.fillText('중요도', 350, 375);

    // Draw point
    ctx.beginPath();
    ctx.arc(point.x, point.y, 10, 0, Math.PI * 2);
    ctx.fillStyle = 'red';
    ctx.fill();
}

canvas.addEventListener('mousedown', (e) => {
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    if (Math.sqrt((x - point.x) ** 2 + (y - point.y) ** 2) < 10) {
        dragging = true;
    }
});

canvas.addEventListener('mousemove', (e) => {
    if (dragging) {
        const rect = canvas.getBoundingClientRect();
        point.x = e.clientX - rect.left;
        point.y = e.clientY - rect.top;
        point.x = Math.max(20, Math.min(point.x, 380)); // Restrict point within canvas
        point.y = Math.max(20, Math.min(point.y, 380)); // Restrict point within canvas
        draw();
    }
});

canvas.addEventListener('mouseup', () => {
    dragging = false;
    // Calculate priority values (1 to 10)
    const importance = Math.round((point.x - 20) / 36) + 1;
    const urgency = Math.round((380 - point.y) / 36) + 1;
    // Set the hidden input value to the calculated importance and urgency
    document.getElementById('importance').value = importance;
    document.getElementById('urgency').value = urgency;

});

draw();