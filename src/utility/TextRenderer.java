package utility;

import controllers.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;




public class TextRenderer {
    private double RenderTime=0.0;
    private static TextRenderer TR=null;
    private String RenderText="";

    //SINGLETON
    public static TextRenderer getInstance() {
        if (TR == null) {
            TR=new TextRenderer();
            return TR;
        } else {
            return TR;
        }
    }
    public TextRenderer(){
        Controller.getInstance().get_canvas().getGraphicsContext2D().setFont(Font.loadFont("SDS_6x6.ttf",20));
     }

    public void render(GraphicsContext canvas){

        if(RenderTime>0) {
            canvas.setStroke(Color.WHITE);
            canvas.strokeText(RenderText, Main.SCR_WIDTH-200,28);


        }
        }

    public void update( double deltaTime){
    if(RenderTime>0){
        RenderTime-=deltaTime;
    }

    }

    public void setRenderText(String renderText) {
        this.RenderText = renderText;
        Utility.getInstance().playSound("res/Audio/Effects/gift.wav");
        RenderTime=3.0;//3 Seconds and disappear
    }
}
