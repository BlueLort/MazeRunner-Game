package memento;

public class Memento {
  private   double pos[];

    public Memento(double[] pos) {
        this.pos = pos.clone();
    }

    public double[] getPos() {
        return pos.clone();
    }


}
