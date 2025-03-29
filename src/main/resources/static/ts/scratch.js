"use strict";
class ScratchLottery {
    constructor() {
        this.isDrawing = false;
        this.lastX = 0;
        this.lastY = 0;
        this.canvas = document.getElementById('scratchCanvas');
        this.ctx = this.canvas.getContext('2d');
        this.resetButton = document.getElementById('resetButton');
        this.initializeEventListeners();
        this.resizeCanvas();
        window.addEventListener('resize', () => this.resizeCanvas());
    }
    resizeCanvas() {
        const scratchArea = this.canvas.parentElement;
        this.canvas.width = scratchArea.offsetWidth;
        this.canvas.height = scratchArea.offsetHeight;
        this.initScratchArea();
    }
    initScratchArea() {
        this.ctx.fillStyle = '#e0e0e0';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        // Add some texture
        this.ctx.fillStyle = '#d0d0d0';
        for (let i = 0; i < 100; i++) {
            const x = Math.random() * this.canvas.width;
            const y = Math.random() * this.canvas.height;
            this.ctx.beginPath();
            this.ctx.arc(x, y, 1, 0, Math.PI * 2);
            this.ctx.fill();
        }
    }
    getCoordinates(e) {
        const rect = this.canvas.getBoundingClientRect();
        return {
            x: e.clientX - rect.left,
            y: e.clientY - rect.top
        };
    }
    startDrawing(e) {
        this.isDrawing = true;
        const coords = this.getCoordinates(e);
        this.lastX = coords.x;
        this.lastY = coords.y;
    }
    draw(e) {
        if (!this.isDrawing)
            return;
        const coords = this.getCoordinates(e);
        this.ctx.beginPath();
        this.ctx.moveTo(this.lastX, this.lastY);
        this.ctx.lineTo(coords.x, coords.y);
        this.ctx.strokeStyle = '#ffffff';
        this.ctx.lineWidth = 20;
        this.ctx.lineCap = 'round';
        this.ctx.lineJoin = 'round';
        this.ctx.stroke();
        this.lastX = coords.x;
        this.lastY = coords.y;
    }
    stopDrawing() {
        this.isDrawing = false;
    }
    async handleReset() {
        try {
            const response = await fetch('/new_prize');
            const newPrize = await response.json();
            // Update the prize content
            const messageElement = document.querySelector('.prize-content h2');
            const amountElement = document.querySelector('.prize');
            if (messageElement && amountElement) {
                messageElement.textContent = newPrize.message;
                amountElement.textContent = newPrize.amount;
            }
            // Reset the scratch area
            this.initScratchArea();
        }
        catch (error) {
            console.error('Error fetching new prize:', error);
        }
    }
    initializeEventListeners() {
        // Mouse events
        this.canvas.addEventListener('mousedown', (e) => this.startDrawing(e));
        this.canvas.addEventListener('mousemove', (e) => this.draw(e));
        this.canvas.addEventListener('mouseup', () => this.stopDrawing());
        this.canvas.addEventListener('mouseout', () => this.stopDrawing());
        // Touch events
        this.canvas.addEventListener('touchstart', (e) => {
            e.preventDefault();
            this.startDrawing(e.touches[0]);
        });
        this.canvas.addEventListener('touchmove', (e) => {
            e.preventDefault();
            this.draw(e.touches[0]);
        });
        this.canvas.addEventListener('touchend', () => this.stopDrawing());
        // Reset button
        this.resetButton.addEventListener('click', () => this.handleReset());
    }
}
// Initialize the scratch lottery when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ScratchLottery();
});
//# sourceMappingURL=scratch.js.map