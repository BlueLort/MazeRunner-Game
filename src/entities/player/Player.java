package entities.player;

import controllers.Controller;
import entities.Cell;
import entities.ENUMS.PlayerDirection;
import entities.Entity;
import entities.player.playerArmoredState.Armoured;
import entities.player.playerArmoredState.Disarmoured;
import entities.player.playerArmoredState.PlayerArmouredState;
import entities.player.playerMoveState.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import memento.CheckPoint;
import observer.*;
import playerState.PlayerState;
import utility.TextRenderer;
import utility.Utility;

import java.util.ArrayList;

public class Player extends Entity implements Cell,Cloneable {
    private double mapX,mapY;//FOR CHECK POINTS WORLD POSTION
    private PlayerDirection direction = PlayerDirection.NONE;
    private MovingLeft ml=new MovingLeft(this);
    private MovingRight mr=new MovingRight(this);
    private MovingUp mu=new MovingUp(this);
    private MovingDown md=new MovingDown(this);
    private NotMoving nm=new NotMoving();
    private PlayerMoveState player_M_State=nm;
    private Armoured pa=new Armoured(this);
    private Disarmoured pda=new Disarmoured(this);
    private PlayerArmouredState player_A_State=pda;
    private int lives;
    private double health;
    public double score;
    private double defense;
    private double attackPower;
    private double speed;
    private int availableKeys;
    private int arrows;
    private PlayerState playerState;
    private ArrayList<Observer> observers=new ArrayList<>();
    protected final int TRANSITION_PARTS = 4;
    private final double MAX_SPEED=160.0;
    public static final double COLLIDER_SHIFT_HEIGHT = 0.5*Entity.SPRITE_HEIGHT;
    public static final double COLLIDER_SHIFT_WIDTH = 0.5*Entity.SPRITE_WIDTH;


    private final double DEAD_TIME=3.0;
    private  double deadTime=0.0;
    private boolean respawn=false;
    private float spriteTransition = 0;
    private float transitionFactor = 200;


    private PlayerSwordDecorator Swords = new PlayerSwordDecorator(this);
    private final double SWORD_TIME = 0.6;
    private double swordsAnimationTime = 0;

    private PlayerBowDecorator Bow = new PlayerBowDecorator(this);
    private final double BOW_TIME = 0.35;
    private final double FIRE_DELAY = 1.1;//FIRE RATE = 1/1.1 =0.909 arrow(orSword)/s
    private double bowAnimationTime = 0;
    private double fireBowTime = 0.0;
    private double fireSwordTime = 0.0;
    private final double INVULNERABLE_TIME=1.2;
    private  double invulnerableTime=0.0;



    /**
     * THE SPRITE SHEET IS AS FOLLOWS
     * <p>
     * FRONT TRANSITION
     * LEFT TRANSITION
     * RIGHT TRANSITION
     * BACK TRANSITION
     **/
    @Override
    public void render(GraphicsContext canvas) {
        if(deadTime<=0) {
            player_A_State.render(canvas);
        }else{
            canvas.drawImage(DEAD_SPRITE, 0, 0, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }

    }

    public Player() {
    }

    public void update(double deltaTime) {
        switch (direction) {

            case FRONT:
                this.player_M_State=md;
                this.spritePlaceY = 0;
                break;
            case LEFT:
                this.player_M_State=ml;
                this.spritePlaceY = 1;
                break;
            case RIGHT:
                this.player_M_State=mr;
                this.spritePlaceY = 2;
                break;
            case BACK:
                this.player_M_State=mu;
                this.spritePlaceY = 3;
                break;
            case ATTACK_SWORD:
                if(!respawn&&fireSwordTime<=0&&swordsAnimationTime<=0) {
                    Utility.getInstance().playSound("res/Audio/Effects/woosh-2.wav");
                    swordsAnimationTime = SWORD_TIME;
                    fireSwordTime=FIRE_DELAY;
                    Swords.prepare();
                }
                break;
            case FIRE_ARROW:
                if(!respawn&&fireBowTime<=0&&arrows>0&&bowAnimationTime<=0) {
                    bowAnimationTime = BOW_TIME;
                    fireBowTime = FIRE_DELAY;
                    Bow.prepare();
                    OnShooting();
                }
                break;
            case NONE:
                player_M_State=nm;
                spritePlaceX=0;
                break;
            default:
                break;
        }
        this.posX+=-Entity.getCameraX();
        this.posY+=-Entity.getCameraY();
        Swords.setSpritePlaceY(this.spritePlaceY);
        Bow.setSpritePlaceY(this.spritePlaceY);
        if(fireBowTime>=0) {
            fireBowTime -= deltaTime;
        }
        if(fireSwordTime>=0) {
            fireSwordTime -= deltaTime;
        }
        if(deadTime>0){
            deadTime-=deltaTime;
        }else{
            if(respawn){
                respawn=false;
                playerRespawn();
            }
        }
        if(invulnerableTime>=0){
            invulnerableTime-=deltaTime;
        }
           if(!respawn) player_M_State.move(deltaTime);
            this.direction = PlayerDirection.NONE;

        this.c.setStartX(this.posX + COLLIDER_SHIFT_WIDTH / 2);
        this.c.setStartY(this.posY + COLLIDER_SHIFT_HEIGHT);
        //DRAW COLLIDER
      //  Controller.getInstance().get_canvas().getGraphicsContext2D().strokeRect(this.c.getStartX(),this.c.getStartY(),this.c.getWidth(),this.c.getHeight());
        updateDecors(deltaTime);
        //player_A_State=pa;//immortal


    }
    public void updateDecors(double deltaTime){
        Bow.setPosX(this.posX);
        Bow.setPosY(this.posY);
        Swords.setPosX(this.posX);
        Swords.setPosY(this.posY);
        switch (this.spritePlaceY){
            case 0:

                Bow.setPosY(this.posY+Entity.SPRITE_HEIGHT/2);
                Swords.setPosY(this.posY+Entity.SPRITE_HEIGHT/2);
                break;
            case 1:
                Bow.setPosX(this.posX-Entity.SPRITE_WIDTH/2);
                Swords.setPosX(this.posX-Entity.SPRITE_WIDTH/2);
                break;
            case 2:
                Bow.setPosX(this.posX+Entity.SPRITE_WIDTH/2);
                Swords.setPosX(this.posX+Entity.SPRITE_WIDTH/2);
                break;
            case 3:
                Bow.setPosY(this.posY-Entity.SPRITE_HEIGHT/2);
                Swords.setPosY(this.posY-Entity.SPRITE_HEIGHT/2);
                break;
        }
        if (swordsAnimationTime > 0) {
            Swords.update(deltaTime);
            swordsAnimationTime -= deltaTime;
        }
        if (bowAnimationTime > 0) {
            Bow.update(deltaTime);
            bowAnimationTime -= deltaTime;
        }
    }
    public  void updateMove(double deltaTime)
    {
        playerState.updateMove(this,deltaTime);
    }
    public void pauseUnPause()
    {
        playerState.pauseUnPause(this);
    }

    public void changeSpriteTransition(){
        spriteTransition++;
        if (spriteTransition >= transitionFactor / speed) {
            spritePlaceX++;
            spriteTransition = 0;
        }
        spritePlaceX %= TRANSITION_PARTS ;
    }
    public void OnC_ChestCollision()
    {
        int random=(int)(Math.random()*3);
        switch (random){
            case 0://RARE <- ARMOR
                player_A_State=pa;
                TextRenderer.getInstance().setRenderText("Armour ");
                break;
            default:
                //AMMO
                TextRenderer.getInstance().setRenderText("Arrows +4");
                arrows+=4;
                break;
        }

        refreshInfo();
    }
    public void OnS_ChestCollision()
    {
        int random=(int)(Math.random()*6);
        switch (random){
            case 0:
               int rand =20+(int)(Math.random()*20);
                health+=rand;
                TextRenderer.getInstance().setRenderText("Health +"+rand);
                if(health>=100)health=100;
                break;
            case 1:
                speed+=4;
                TextRenderer.getInstance().setRenderText("Speed +4");
                if(speed>=MAX_SPEED)speed=MAX_SPEED;
                break;
            case 2:
                defense +=15;
                TextRenderer.getInstance().setRenderText("Def +15");
                if(defense>=150)defense=150;
                break;
            case 3:
                lives++;
                TextRenderer.getInstance().setRenderText("Lives +1");
                if(lives>=3)lives=3;
                break;
            case 4:
                attackPower+=35;
                TextRenderer.getInstance().setRenderText("Attack Power +35");
                if(attackPower>=500)attackPower=500;
                break;
            case 5:
                score+=600;
                TextRenderer.getInstance().setRenderText("Score +600");
                break;
        }
        refreshInfo();
    }
    public void OnShooting()
    {
        Utility.getInstance().playSound("res/Audio/Effects/woosh-1.wav");
        arrows--;
        notifyObservers(new Ammo(arrows));
    }

    public Player(double posX, double posY) {
        super(posX, posY);
        this.mapX=posX;
        this.mapY=posY;
        this.c.setStartX(this.posX + COLLIDER_SHIFT_WIDTH / 2);
        this.c.setStartY(this.posY + COLLIDER_SHIFT_HEIGHT );
        this.c.setHeight(SPRITE_HEIGHT - COLLIDER_SHIFT_HEIGHT);
        this.c.setWidth(SPRITE_WIDTH - COLLIDER_SHIFT_WIDTH);
        this.health = 100;
        this.defense =1000;
        this.attackPower = 150;
        this.speed = 95.0;
        this.availableKeys =0;
        this.lives=3;
        this.arrows=6;
    }
    public void refreshInfo()
    {
        notifyObservers(new Health(health));
        notifyObservers(new Lives(lives));
        notifyObservers(new Ammo(arrows));
        notifyObservers(new Keys(availableKeys));
        notifyObservers(new Score((int)score));
    }
    public void takeDamage(double damage){
       player_A_State.takeDamage(damage);
       notifyObservers(new Health(health));
    }
   public void onDeath(){
       deadTime=DEAD_TIME;
        if(this.lives>0){
            this.lives--;
            notifyObservers(new Lives(lives));
            respawn=true;

        }else{
            Controller.getInstance().get_canvas_container().getChildren().add(Controller.getInstance().getLoosewindow());
            GraphicsContext g=Controller.getInstance().getLossingCanvas1().getGraphicsContext2D();
            Canvas canvas=Controller.getInstance().getLossingCanvas1();
            Color color = Color.web("#000000", 0.7);
            Color color1 = Color.web("#551a8b", 1);
            g.setFill(color);
            g.fillRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1, canvas.getHeight()-1, 50, 50);
            g.setStroke(color1);
            g.strokeRoundRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth()-1, canvas.getHeight()-1, 50, 50);
            Controller.getInstance().getGameState().pauseUnPause();
            Controller.getInstance().get_main_layout().getChildren().remove(Controller.getInstance().getPauseMenuView());
            Controller.getInstance().getFinalScore1().setText(Controller.getInstance().getFinalScore1().getText()+Controller.getInstance().getPlayer().getScore());

        }


    }
    public void playerRespawn(){
        this.health=100.0;
        notifyObservers(new Health(health));
        CheckPoint checkPoint=new CheckPoint();
        checkPoint.loadCheckPoint();



    }

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
    }


    public PlayerDirection getDirection() {
        return direction;
    }


    public PlayerSwordDecorator getSwords() {
        return Swords;
    }

    public double getSwordsAnimationTime() {
        return swordsAnimationTime;
    }

    public PlayerBowDecorator getBow() {
        return Bow;
    }

    public double getBowAnimationTime() {
        return bowAnimationTime;
    }


    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHealth() {
        return health;
    }
    public void notifyObservers(Object object)
    {
        for (Observer observer:observers) {
            observer.update(object);
        }
    }
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }
    public void deleteObserver(Observer observer)
    {
        observers.remove(observer);
    }
    public void deleteAllObservers(){observers=new ArrayList<>();}
    public double getScore() {return this.score; }
    public void setScore(double score) {this.score= score; }
    public void takeKey(){
        availableKeys++;
        notifyObservers(new Keys(availableKeys));
    }
    public void useKey(){
        availableKeys--;
        notifyObservers(new Keys(availableKeys));
    }


    public void addScore(double score)
    {
        this.score+=score;
        if(this.score<0){
            this.score=0;
        }
        //like leveling up so player could feel progress and risky if he is reckless
        this.attackPower+=score/50;
        this.defense+=score/100;
        notifyObservers(new Score((int)this.score));
    }
    public void removeArmour(){
        this.player_A_State=pda;
    }


    public MovingLeft getMl() {
        return ml;
    }

    public void setMl(MovingLeft ml) {
        this.ml = ml;
    }

    public MovingRight getMr() {
        return mr;
    }

    public void setMr(MovingRight mr) {
        this.mr = mr;
    }

    public MovingUp getMu() {
        return mu;
    }

    public void setMu(MovingUp mu) {
        this.mu = mu;
    }

    public MovingDown getMd() {
        return md;
    }

    public void setMd(MovingDown md) {
        this.md = md;
    }

    public NotMoving getNm() {
        return nm;
    }

    public void setNm(NotMoving nm) {
        this.nm = nm;
    }

    public PlayerMoveState getPlayer_M_State() {
        return player_M_State;
    }

    public void setPlayer_M_State(PlayerMoveState player_M_State) {
        this.player_M_State = player_M_State;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(double attackPower) {
        this.attackPower = attackPower;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getAvailableKeys() {
        return availableKeys;
    }

    public void setAvailableKeys(int availableKeys) {
        this.availableKeys = availableKeys;
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public int getTRANSITION_PARTS() {
        return TRANSITION_PARTS;
    }

    public static double getColliderShiftHeight() {
        return COLLIDER_SHIFT_HEIGHT;
    }

    public static double getColliderShiftWidth() {
        return COLLIDER_SHIFT_WIDTH;
    }

    public float getSpriteTransition() {
        return spriteTransition;
    }

    public void setSpriteTransition(float spriteTransition) {
        this.spriteTransition = spriteTransition;
    }

    public float getTransitionFactor() {
        return transitionFactor;
    }

    public void setTransitionFactor(float transitionFactor) {
        this.transitionFactor = transitionFactor;
    }

    public void setSwords(PlayerSwordDecorator swords) {
        Swords = swords;
    }

    public double getSWORD_TIME() {
        return SWORD_TIME;
    }

    public void setSwordsAnimationTime(double swordsAnimationTime) {
        this.swordsAnimationTime = swordsAnimationTime;
    }

    public void setBow(PlayerBowDecorator bow) {
        Bow = bow;
    }

    public double getBOW_TIME() {
        return BOW_TIME;
    }

    public void setBowAnimationTime(double bowAnimationTime) {
        this.bowAnimationTime = bowAnimationTime;
    }

    public Armoured getPa() {
        return pa;
    }

    public void setPa(Armoured pa) {
        this.pa = pa;
    }

    public Disarmoured getPda() {
        return pda;
    }

    public void setPda(Disarmoured pda) {
        this.pda = pda;
    }

    public PlayerArmouredState getPlayer_A_State() {
        return player_A_State;
    }

    public void setPlayer_A_State(PlayerArmouredState player_A_State) {
        this.player_A_State = player_A_State;
    }

    public double getFireBowTime() {
        return fireBowTime;
    }

    public void setFireBowTime(double fireBowTime) {
        this.fireBowTime = fireBowTime;
    }

    public double getFireSwordTime() {
        return fireSwordTime;
    }

    public void setFireSwordTime(double fireSwordTime) {
        this.fireSwordTime = fireSwordTime;
    }

    public double getInvulnerableTime() {
        return invulnerableTime;
    }

    public void setInvulnerableTime(double invulnerableTime) {
        this.invulnerableTime = invulnerableTime;
    }

    public double getMAX_SPEED() {
        return MAX_SPEED;
    }

    public double getFIRE_DELAY() {
        return FIRE_DELAY;
    }

    public double getINVULNERABLE_TIME() {
        return INVULNERABLE_TIME;
    }

    public double getDEAD_TIME() {
        return DEAD_TIME;
    }

    public double getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(double deadTime) {
        this.deadTime = deadTime;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public double getMapX() {
        return mapX;
    }

    public void setMapX(double mapX) {
        this.mapX = mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public void setMapY(double mapY) {
        this.mapY = mapY;
    }

    @Override
public Object clone() throws CloneNotSupportedException {
    return super.clone();
}
}
