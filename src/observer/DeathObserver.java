package observer;

import controllers.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class DeathObserver implements Observer {
    public DeathObserver() {
    }

    private int n;
    Image image=new Image("file:res/ImgData/Warrior.png");
    Controller controller;

    public DeathObserver(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update(Object arg) {
        if (arg instanceof Lives) {
            controller=Controller.getInstance();
             n =  ((Lives) arg).getLives();
            controller.setLives(n);
        }
    }
}
