package observer;

import controllers.Controller;

public class ScoreObserver implements Observer {
    public ScoreObserver() {
    }

    private int score;
    private Controller controller;
    public ScoreObserver(Controller controller)
    {
        this.controller=controller;
    }

    @Override
    public void update(Object arg) {
        if(arg instanceof Score)
        {
            controller=Controller.getInstance();
            score=((Score) arg).getScore();
            controller.setScore(score);
        }
    }
}
