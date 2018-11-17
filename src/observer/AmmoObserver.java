package observer;

import controllers.Controller;

public class AmmoObserver implements Observer {
    private int ammo;

    public AmmoObserver() {
    }

    private Controller controller;
    public AmmoObserver(Controller controller)
    {this.controller=controller;}

    @Override
    public void update(Object arg) {
        if(arg instanceof Ammo)
        {
            controller=Controller.getInstance();
            controller.setAmmo(((Ammo) arg).getAmmo());
        }
    }
}
