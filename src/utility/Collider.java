package utility;


import javafx.geometry.Rectangle2D;

public class Collider {
    private double startX;
    private double startY;
    private double width;
    private double height;



/**
 *
 * c:  (startX,startY)
 *              <---Width--->
 *              c-----------+ ^
 *              |           | |
 *              |           | |
 *              |           | Height
 *              |           | |
 *              |           | |
 *              +-----------+ #
 *
 */
    //TODO INTERSECT FUNCTION
    public boolean isColliding(Collider c){
      /* return! (this.startX + this.width/2  <= c.getStartX()  -  c.getWidth()/2   ||
                this.startX - this.width/2  >= c.getStartX()  +  c.getWidth()/2   ||
                this.startY + this.height/2 <= c.getStartY()  -  c.getHeight()/2  ||
                this.startY - this.height/2 >= c.getStartY()  +  c.getHeight()/2
        );*/
        Rectangle2D collider=new Rectangle2D(this.startX,this.startY,width,height);
      return collider.intersects(c.getCollider());

    }

    public Collider(double startX, double startY,double width, double height ) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;

    }

    public Collider() {
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setWidth(double width) {
     //   startX+=(width-this.width)/2;
        this.width = width;
    }

    public Rectangle2D getCollider() {
        return new Rectangle2D(startX,startY,width,height);
    }

    public void setHeight(double height) {
       // startY+=(height-this.height)/2;
        this.height = height;
    }

    public void setStartX(double startX) {
        this.startX = startX;
        //this.startX+=width/2;
    }

    public void setStartY(double startY) {
        this.startY =startY;
        //this.startY+=height/2;
    }
}
