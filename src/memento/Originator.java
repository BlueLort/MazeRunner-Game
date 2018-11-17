package memento;

import IOFile.GameData;
import entities.player.Player;

public class Originator {
    private double pos[];
    public Originator()
    {
        pos=new double[2];
    }
    public void setGameData(double[] pos)
    {
        this.pos=pos.clone();
    }
    public Memento save() throws CloneNotSupportedException {
            return new Memento( this.pos.clone());

    }
    public void restore(Memento memento) throws CloneNotSupportedException {
        this.pos=  memento.getPos().clone();
    }
    public double[] getPos()
    {
        return this.pos;
    }

}
