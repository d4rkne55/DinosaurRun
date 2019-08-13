package dinosaurGame.helper;

public class Animation
{
    public static final int EASING_LINEAR = 1;
    public static final int EASING_SINE = 2;
    
    private int easing;
    private double finalState;
    private double currentState;
    private int duration;
    private boolean inverted = false;
    
    private long firstUpdate = 0;
    private long lastUpdate;
    
    public Animation(double toAnimate, int duration, int easing) {
        this.easing = easing;
        this.finalState = this.currentState = toAnimate;
        this.duration = duration;
    }
    
    private void calcNextLinear() {
        int timePassed = (int) (this.lastUpdate - this.firstUpdate);
        // animation state in percent
        double progress = Math.min((double) timePassed / this.duration, 1);
        
        if (!this.inverted) {
            this.currentState = this.finalState * progress;
        } else {
            this.currentState = this.finalState - this.finalState * progress;
        }
    }
    
    private void calcNextSine() {
        int timePassed = (int) (this.lastUpdate - this.firstUpdate);
        // animation state in percent
        double progress = Math.min((double) timePassed / this.duration, 1);
        
        if (!this.inverted) {
            this.currentState = this.finalState * Math.sin(progress * Math.PI / 2);
        } else {
            this.currentState = this.finalState * Math.cos(progress * Math.PI / 2);
        }
    }
    
    public void update() {
        if (this.isFinished()) {
            this.firstUpdate = 0;
        }
        
        if (this.firstUpdate == 0) {
            this.firstUpdate = System.currentTimeMillis();
        }
        
        this.lastUpdate = System.currentTimeMillis();
        
        switch (this.easing) {
            case EASING_LINEAR:
                this.calcNextLinear();
                break;
            case EASING_SINE:
                this.calcNextSine();
                break;
        }
    }
    
    public double getState() {
        return this.currentState;
    }
    
    public boolean isFinished() {
        return (this.lastUpdate - this.firstUpdate) >= this.duration;
    }
    
    public boolean isInverted() {
        return inverted;
    }
    
    public void invert() {
        this.invert(true);
    }
    
    public void invert(boolean inverted) {
        this.inverted = inverted;
    }
}
