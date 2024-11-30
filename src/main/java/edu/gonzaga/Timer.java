package edu.gonzaga;
/*
Liam Fitting
11/29/24
Im using system timer, might be easier to use Jtimer, will figure out later...
 */
public class Timer {
    private long startTime;   // Stores the start time in milliseconds
    private long elapsedTime; // Stores the total elapsed time
    private boolean running;  // Tracks if the timer is currently running

    /**
     * Constructor to initialize the Timer.
     */
    public Timer() {
        this.startTime = 0;
        this.elapsedTime = 0;
        this.running = false;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if (!running) {
            this.startTime = System.currentTimeMillis();
            this.running = true;
        }
    }

    /**
     * Stops the timer and calculates elapsed time.
     */
    public void stop() {
        if (running) {
            this.elapsedTime += System.currentTimeMillis() - this.startTime;
            this.running = false;
        }
    }

    /**
     * Resets the timer to its initial state.
     */
    public void reset() {
        this.startTime = 0;
        this.elapsedTime = 0;
        this.running = false;
    }

    /**
     * Returns the elapsed time in seconds.
     * @return Elapsed time in seconds.
     */
    public float getElapsedTime() {
        if (running) {
            return (elapsedTime + (System.currentTimeMillis() - startTime)) / 1000.0f;
        } else {
            return elapsedTime / 1000.0f;
        }
    }
}

