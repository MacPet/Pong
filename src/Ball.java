import java.awt.*;
import java.util.Random;

public class Ball {

    private int  bounceTick, roofbounceTick;
    private double ballSpeed;

    private final int size;
    private double middleY, lowerY, x, y;


    Random rand = new Random();

    private double vectorY = generateRandomVY();

    private double generateRandomVY() {
        double randomYGen = rand.nextDouble(-1, 2);
        return randomYGen == 0 ? 0.5 : randomYGen;
    }


    public double getY(){ return y; }
    public double getMiddleY(){
        return middleY;
    }
    public double getLowerY(){ return lowerY; }

    public double getX(){
        return x;
    }



    public void setY(double newY){

        this.y = newY;
        this.lowerY = newY + size;
        this.middleY = this.y + (this.size / 2.0);

    }

    public void setX(double newX)
    {
        this.x = newX;
    }

    private final Color color = Color.WHITE;
    public Ball(int size){
        this.x = Variables.defaultBallX;
        this.y = Variables.defaultBallY;
        this.size = size;
        this.lowerY = y + size;
        this.middleY = this.y + (this.size / 2.0);
        this.ballSpeed = 5;


    }

    public void calcBounce(paddle paddle){
        double ydiff = paddle.getMiddleY() - getMiddleY();
        double balldiff = ydiff/ (Variables.paddleHeight /2.0);
        double bounce = Math.toRadians(balldiff * Variables.maxBounceAngle);
        vectorY = -Math.sin(bounce) * Math.abs(ballSpeed);

    }
    public void move(paddle leftPaddle, paddle rightPaddle) {
        //roofbounce jest od powstrzymania kuli od utkniecia w granicy jesli jakos przeskoczylaby poza linie
        //bounce jest od powstrzymania kuli od utkniecia w srodku paletki lub za nią
        roofbounceTick--;
        bounceTick--;
        boolean roofBounced = roofbounceTick<=0;
        boolean bounced = bounceTick<=0;

            //sprawdza czy pilka odbija sie od lewej rakietki
        int bounceTickDefault = 10;
        if (roofBounced && bounced && x <= leftPaddle.getX() + Variables.paddleWidth  && x >= leftPaddle.getX()   - (Math.abs(ballSpeed) - 5) && y + size >= leftPaddle.getY() && y <= leftPaddle.getY() + Variables.paddleHeight) {
                calcBounce(leftPaddle);
                ballSpeed-=0.2;
                ballSpeed *= -1;

                bounceTick = bounceTickDefault;

                System.out.println("LB");

            }
            //sprawdza czy pilka odbija sie od prawej rakietki
            else if (roofBounced && bounced &&  x >= rightPaddle.getX() - (Math.abs(ballSpeed) - 5)  && x <= rightPaddle.getX() + Variables.paddleWidth && rightPaddle.getY() <= y && y <= rightPaddle.getLowerY()) {
                calcBounce(rightPaddle);
                ballSpeed+=0.2;
                ballSpeed *= -1;
                bounceTick = bounceTickDefault;
                System.out.println("RB");
            }

            //sprawdza czy pilka wyszla za lewa rakietke
            else if (x < 0) {
                ballSpeed = 5;
                vectorY = generateRandomVY();
                x = Variables.defaultBallX;
                Variables.rightScore++;
             Variables.scoreDisplayUpdate = false;

                System.out.println("Left");
            }
            //sprawdza czy pilka wyszla za prawa rakietke
            else if (Variables.screenWidth < x) {
                ballSpeed = -5;
                vectorY = generateRandomVY();
                x = Variables.defaultBallX;
                Variables.leftScore++;
                Variables.scoreDisplayUpdate = false;

            System.out.println("R");
            }
            else{


                if (roofBounced && (y < Variables.sidePadding || y > Variables.screenHeight - Variables.sidePadding)) {

                    vectorY *= -1;
                    roofbounceTick = 3;

                    System.out.println("ROOF");



                }
            }
        x+= ballSpeed;
        setY(y + vectorY);
        }




    public void drawBall(Graphics graphics){
        graphics.setColor(color);
        graphics.fillRect((int)x, (int)y, size, size);
    }




}
