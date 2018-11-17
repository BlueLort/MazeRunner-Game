package observer;


import controllers.Controller;

import javafx.scene.control.Label;
import main.Main;

public class HealthObserver implements Observer {
    private int Health;
    Controller controller;

    public HealthObserver() {
    }

    public HealthObserver(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update(Object arg) {
        if (arg instanceof Health & arg!=null) {
           this.controller= Controller.getInstance();
            Double d =  ((Health) arg).getHealth();
            Health = ((int) Math.round(d));
            controller.setHealth(Health);
        }
    }
}