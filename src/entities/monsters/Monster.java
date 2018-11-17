package entities.monsters;

import controllers.Controller;
import entities.player.Arrow;
import entities.Cell;
import entities.ENUMS.TileDecoration;
import entities.ENUMS.tileType;
import entities.Entity;
import entities.monsters.monsterMoveState.MonsterMoveState;
import entities.player.Player;
import entities.monsters.monsterMoveState.*;
import entities.tiles.TilesFactory;
import entities.walls.DarkWall;
import entities.walls.Wall;
import utility.Collider;
import utility.Utility;

public abstract class Monster extends Entity {
    protected MovingLeft ml = new MovingLeft(this);
    protected MovingRight mr = new MovingRight(this);
    protected MovingUp mu = new MovingUp(this);
    protected MovingDown md = new MovingDown(this);
    protected NotMoving nm = new NotMoving();
    protected MonsterMoveState monster_M_State = nm;
    protected double health;
    protected double defense;
    protected double speed;
    protected double attackPower;
    protected final int TRANSITION_PARTS = 2;
    public static final double COLLIDER_SHIFT_HEIGHT = 5;
    public static final double COLLIDER_SHIFT_WIDTH = 8;
    protected final double INVULNERABLE_TIME = 0.7;
    protected double invulnerableTime = 0.0;
    protected Collider AICollider;
    private float spriteTransition = 0;
    private float transitionFactor = 4000;

    public Monster() {
    }

    public Monster(double posX, double posY) {
        super(posX, posY);
        this.c.setStartX(this.posX + COLLIDER_SHIFT_WIDTH / 2);
        this.c.setStartY(this.posY + COLLIDER_SHIFT_HEIGHT);
        this.c.setHeight(SPRITE_HEIGHT - COLLIDER_SHIFT_HEIGHT);
        this.c.setWidth(SPRITE_WIDTH - COLLIDER_SHIFT_WIDTH);
        this.AICollider=new Collider();
        this.AICollider.setStartX(this.posX-80);
        this.AICollider.setStartY(this.posY-80);
        this.AICollider.setWidth(200);
        this.AICollider.setHeight(200);

    }

    public void changeSpriteTransition() {
        spriteTransition++;
        if (spriteTransition >= transitionFactor / speed) {
            spritePlaceX++;
            spriteTransition = 0;
        }
        spritePlaceX %= TRANSITION_PARTS;
    }

    @Override
    public void update(double deltaTime) {

        if(deltaTime==0)return;
        int rnd = (int) (Math.random() * 35);//the bigger the number the more it stays in place
        double colliderPlaceX=this.posX;
        double colliderPlaceY=this.posY;
        double posx = Controller.getInstance().getPlayer().getPosX();
        double posy = Controller.getInstance().getPlayer().getPosY();
        if(AICollider.isColliding(Controller.getInstance().getPlayer().getCollider())){

            boolean Searching=true;
            while(Math.abs(colliderPlaceX-this.posX)<90&&Math.abs(colliderPlaceY-this.posY)<90&&Searching) {

                if (posx < this.posX) {
                    spritePlaceY=0;
                colliderPlaceX-=Entity.SPRITE_WIDTH;
                } else {
                    spritePlaceY=1;
                    colliderPlaceX+=Entity.SPRITE_WIDTH;
                }
                if (posy < this.posY) {
                    colliderPlaceY-=SPRITE_HEIGHT;
                } else {
                    colliderPlaceY+=SPRITE_HEIGHT;
                }
                Collider tmp=new Collider(colliderPlaceX+COLLIDER_SHIFT_WIDTH,colliderPlaceY+COLLIDER_SHIFT_HEIGHT,SPRITE_WIDTH-COLLIDER_SHIFT_WIDTH,SPRITE_HEIGHT-COLLIDER_SHIFT_HEIGHT);
                for(Cell c:Controller.getInstance().getGameObjects()){
                    if(c instanceof Wall || c instanceof DarkWall){
                        Searching=false;
                        break;
                    }
                }
                if(tmp.isColliding(Controller.getInstance().getPlayer().getCollider())){

                   rnd=999;
                    this.monster_M_State = nm;
                   Searching=false;
                   break;
                }
            }

        }

            switch (rnd) {
                case 999://CAN NOT BE ACCESSED BY RANDOM ENGINE
                    this.posX+=(speed*(posX>posx?-deltaTime:deltaTime))/6;
                    this.posY+=(speed*(posY>posy?-deltaTime:deltaTime))/6;

                    break;
                case 2:
                    this.monster_M_State = mu;
                    this.spritePlaceY = 0;
                    break;
                case 3:
                    this.monster_M_State = md;
                    this.spritePlaceY = 1;
                    break;
                case 4:
                    this.monster_M_State = ml;
                    this.spritePlaceY = 0;
                    break;
                case 5:
                    this.monster_M_State = mr;
                    this.spritePlaceY = 1;
                    break;
                default:
                    this.monster_M_State = nm;
                    break;
            }

        monster_M_State.move(deltaTime);
        changeSpriteTransition();
        if (this.invulnerableTime > 0) {
            invulnerableTime -= deltaTime;
        }
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        this.c.setStartX(this.posX + COLLIDER_SHIFT_WIDTH / 2);
        this.c.setStartY(this.posY + COLLIDER_SHIFT_HEIGHT);
        this.AICollider.setStartX(this.posX-80);
        this.AICollider.setStartY(this.posY-80);


        if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getCollider())) {
            this.skill(Controller.getInstance().getPlayer());
            Controller.getInstance().getPlayer().takeDamage(this.attackPower);
        }
        if(Controller.getInstance().getPlayer().getSwordsAnimationTime()>0&&!Controller.getInstance().getPlayer().getSwords().isDAMAGE_DONE()) {
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getSwords().getCollider())) {
                this.takeDamage(Controller.getInstance().getPlayer().getAttackPower());
               Controller.getInstance().getPlayer().getSwords().setDAMAGE_DONE(true);
            }
        }
        for (Cell c : Controller.getInstance().getGameObjects()) {
            if (c instanceof Arrow) {
                if (this.getCollider().isColliding(c.getCollider())) {
                    this.takeDamage(Controller.getInstance().getPlayer().getAttackPower());
                    ((Arrow) c).destroy();
                }
            }
        }
    }

    public void takeDamage(double damage) {
        if (this.invulnerableTime <= 0) {
            this.invulnerableTime = this.INVULNERABLE_TIME;
            this.health -= damage / this.defense;
            Utility.getInstance().playSound("res/Audio/Effects/scream-6.wav");
            if (this.health <= 0) {

                this.onDeath();
            }
        }

    }

    private void onDeath() {
        Controller.getInstance().getToBeDeletedObjects().add(this);
        Controller.getInstance().getToBeAddedObjects().add(TilesFactory.getTile(this.posX, this.posY, TileDecoration.SKULL
                , false,/*WON'T BE RENDERED ANYWAY*/ tileType.MINI_CENTER));
        int rnd=(int)(Math.random()*24);
        //GIVE PLAYER A GIFT (RARE TO HAPPEN) MORE LIKELY TO GET ARMOR/AMMO
        switch (rnd){
            case 1:Controller.getInstance().getPlayer().OnC_ChestCollision();
            break;
            case 11: case 9: case 5:Controller.getInstance().getPlayer().OnS_ChestCollision();
                break;


        }
        if(this instanceof GhostMonster)
             Controller.getInstance().getPlayer().addScore(400);
        else
            Controller.getInstance().getPlayer().addScore(800);

    }
    public abstract void skill(Player p);
    public double getHealth() {
        return health;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(double attackPower) {
        this.attackPower = attackPower;
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

    public MonsterMoveState getMonster_M_State() {
        return monster_M_State;
    }

    public void setMonster_M_State(MonsterMoveState monster_M_State) {
        this.monster_M_State = monster_M_State;
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

    public double getINVULNERABLE_TIME() {
        return INVULNERABLE_TIME;
    }

    public double getInvulnerableTime() {
        return invulnerableTime;
    }

    public void setInvulnerableTime(double invulnerableTime) {
        this.invulnerableTime = invulnerableTime;
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

    public Collider getAICollider() {
        return AICollider;
    }

    public void setAICollider(Collider AICollider) {
        this.AICollider = AICollider;
    }
}
