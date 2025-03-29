interface Prize {
    amount: string;
    message: string;
}

interface Coordinates {
    x: number;
    y: number;
}

class ScratchLottery {
    private canvas: HTMLCanvasElement;
    private ctx: CanvasRenderingContext2D;
    private resetButton: HTMLButtonElement;
    private isDrawing: boolean = false;
    private lastX: number = 0;
    private lastY: number = 0;

    constructor() {
        this.canvas = document.getElementById('scratchCanvas') as HTMLCanvasElement;
        this.ctx = this.canvas.getContext('2d', { alpha: true })!;
        this.resetButton = document.getElementById('resetButton') as HTMLButtonElement;
        
        this.initializeEventListeners();
        this.resizeCanvas();
        window.addEventListener('resize', () => this.resizeCanvas());
    }

    private resizeCanvas(): void {
        const scratchArea = this.canvas.parentElement!;
        this.canvas.width = scratchArea.offsetWidth;
        this.canvas.height = scratchArea.offsetHeight;
        this.initScratchArea();
    }

    private initScratchArea(): void {
        // Clear the canvas first
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        
        // Fill with gray background
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

    private getCoordinates(e: MouseEvent | Touch): Coordinates {
        const rect = this.canvas.getBoundingClientRect();
        const scaleX = this.canvas.width / rect.width;
        const scaleY = this.canvas.height / rect.height;
        return {
            x: (e.clientX - rect.left) * scaleX,
            y: (e.clientY - rect.top) * scaleY
        };
    }

    private startDrawing(e: MouseEvent | Touch): void {
        this.isDrawing = true;
        const coords = this.getCoordinates(e);
        this.lastX = coords.x;
        this.lastY = coords.y;
        this.draw(e); // Draw a dot when starting
    }

    private draw(e: MouseEvent | Touch): void {
        if (!this.isDrawing) return;
        
        const coords = this.getCoordinates(e);
        
        // Draw a circle at the current position
        this.ctx.save();
        this.ctx.beginPath();
        this.ctx.arc(coords.x, coords.y, 40, 0, Math.PI * 2);
        this.ctx.globalCompositeOperation = 'destination-out';
        this.ctx.fillStyle = 'rgba(0, 0, 0, 1)';
        this.ctx.fill();
        this.ctx.restore();
        
        // Draw a line between points for smoothness
        this.ctx.save();
        this.ctx.beginPath();
        this.ctx.moveTo(this.lastX, this.lastY);
        this.ctx.lineTo(coords.x, coords.y);
        this.ctx.lineWidth = 80;
        this.ctx.lineCap = 'round';
        this.ctx.lineJoin = 'round';
        this.ctx.globalCompositeOperation = 'destination-out';
        this.ctx.stroke();
        this.ctx.restore();
        
        this.lastX = coords.x;
        this.lastY = coords.y;
    }

    private stopDrawing(): void {
        this.isDrawing = false;
    }

    private async handleReset(): Promise<void> {
        try {
            const response = await fetch('/new_prize');
            const newPrize: Prize = await response.json();
            
            // Update the prize content
            const messageElement = document.querySelector('.prize-content h2') as HTMLElement;
            const amountElement = document.querySelector('.prize') as HTMLElement;
            
            if (messageElement && amountElement) {
                messageElement.textContent = newPrize.message;
                amountElement.textContent = newPrize.amount;
            }
            
            // Reset the scratch area
            this.initScratchArea();
        } catch (error) {
            console.error('Error fetching new prize:', error);
        }
    }

    private initializeEventListeners(): void {
        // Mouse events
        this.canvas.addEventListener('mousedown', (e: MouseEvent) => this.startDrawing(e));
        this.canvas.addEventListener('mousemove', (e: MouseEvent) => this.draw(e));
        this.canvas.addEventListener('mouseup', () => this.stopDrawing());
        this.canvas.addEventListener('mouseout', () => this.stopDrawing());
        
        // Touch events
        this.canvas.addEventListener('touchstart', (e: TouchEvent) => {
            e.preventDefault();
            this.startDrawing(e.touches[0]);
        });
        
        this.canvas.addEventListener('touchmove', (e: TouchEvent) => {
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