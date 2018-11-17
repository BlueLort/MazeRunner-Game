package observer;

import controllers.Controller;

public class KeyObserver implements Observer {
    private int key;
    private Controller controller;

    public KeyObserver() {
    }

    public KeyObserver(Controller controller)
    {
        this.controller=controller;
    }

    @Override
    public void update(Object arg) {
        if(arg instanceof Keys)
        {
            controller=Controller.getInstance();
            key=((Keys) arg).getKeys();
            controller.setKeys(key);
        }
    }
}
