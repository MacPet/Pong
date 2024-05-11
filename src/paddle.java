import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class paddle {
    private int x;
    private int y;
    private int middleY;
    private int lowerY;
    private final int width;
    private final int height;



    public int getY(){ return y; }
    public int getMiddleY(){
        middleY = y + (height / 2);
        return middleY;
    }
    public int getLowerY(){ return lowerY; }

    public int getX(){
        return x;
    }


    //postanowilem zrobic update lowerY w setY, bo gdy y jest zmieniane zawsze zmieniane jest tez
    //lower Y
    public void setY(int newY){

        this.y = newY;
        this.lowerY = newY + height;
        this.middleY = y + (height / 2);
    }


    private final Color color = Color.WHITE;
    public paddle(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lowerY = y + height;
    }

    public void drawPaddle(Graphics graphics){
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }
    public void moveAI(Ball ball){
        int movementSpeed = 5+Variables.gameDifficulty+Variables.leftScore;
        boolean ballInRange = ball.getX() > x - 100 - (25*Variables.gameDifficulty);
        if(ballInRange){
            if(y > ball.getY()){
                setY(y-movementSpeed);
            }
            else{
                setY(y+movementSpeed);
            }
        }

    }
    public void movePlayer(ListenerKey keyListener, int UP, int DOWN){

        if(keyListener.isKeyPressed(UP)) {
            if(Variables.sidePadding < y - 10 ){
                setY(y - 10);
            }
        }

        else if(keyListener.isKeyPressed(DOWN)) {
            if(lowerY + Variables.sidePadding < Variables.screenHeight){
            setY(y + 10);
            }
        }
    }

}
