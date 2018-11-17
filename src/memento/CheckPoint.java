package memento;

import IOFile.GameData;
import IOFile.SaverAndLoader;
import controllers.Controller;
import entities.Cell;
import entities.Entity;
import entities.player.Player;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class CheckPoint {
    CareTaker careTaker;
    Originator originator;

    public CheckPoint() {
      careTaker=new CareTaker();
      originator=new Originator();
    }

    public void saveNewCheckPoint(double posx,double posy)  {
        careTaker=new CareTaker();
        originator=new Originator();
        double pos[]=new double[2];
         pos[0]=posx;
         pos[1]=posy;
        originator.setGameData(pos);
        try {
            careTaker.setMemento(originator.save());
        } catch (CloneNotSupportedException e) {}
        Controller.getInstance().setCheckPoint(careTaker);
    }
    public void loadCheckPoint()  {
        try {
            originator.restore(Controller.getInstance().getCheckPoint().getMemento());
           double posx= originator.getPos().clone()[0];
           double posy=originator.getPos().clone()[1];
            Controller.getInstance().getPlayer().setPosX(posx);
            Controller.getInstance().getPlayer().setPosY(posy-Entity.SPRITE_HEIGHT);
        } catch (CloneNotSupportedException e) { }
    }

    public CareTaker getCareTaker() {
        return careTaker;
    }

    public void setCareTaker(CareTaker careTaker) {
        this.careTaker = careTaker;
    }

    public Originator getOriginator() {
        return originator;
    }

    public void setOriginator(Originator originator) {
        this.originator = originator;
    }
}
