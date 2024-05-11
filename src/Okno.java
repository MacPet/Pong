import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Okno extends JFrame implements Runnable {

    Graphics2D graphics2D;

    Timer updateTimer = new Timer(1000 / Variables.fpsCap, e -> update());
    ListenerKey keyListener = new ListenerKey();
    paddle leftPaddle, rightPaddle;
    Ball ball;
    int difficultyCounter = 0;
    int actionTicker  = 5;
    public int startingState = 0;
    int countdown = Variables.countdownDefault;
    int fpsCounter = Variables.fpsCap;
    int endingScreenCounter = 0;







    private Graphics getOffScreenGraphics(Image offscreen) {
        Graphics offScreenGraphics = offscreen.getGraphics();

        offScreenGraphics.setColor(Color.BLACK);
        offScreenGraphics.fillRect(0,0,Variables.screenWidth,Variables.screenHeight);
        offScreenGraphics.setColor(Color.WHITE);
        offScreenGraphics.setFont(Variables.stringFont);
        return offScreenGraphics;
    }

    public void drawStartingScreen(Graphics g){
        Image offscreen = createImage(Variables.screenWidth, Variables.screenHeight);
        Graphics offScreenGraphics = getOffScreenGraphics(offscreen);
        offScreenGraphics.drawString(Variables.title, Variables.screenWidth/2 - offScreenGraphics.getFontMetrics().stringWidth(Variables.title ) /2, Variables.screenHeight/3);
        if(actionTicker<=0){
        if(keyListener.isKeyPressed( KeyEvent.VK_D)){
                difficultyCounter = (difficultyCounter == 1 + startingState) ? 0 : difficultyCounter+1;
            actionTicker = Variables.fpsCap / 4;
        }
        else if(keyListener.isKeyPressed(KeyEvent.VK_A)){
            difficultyCounter = (difficultyCounter == 0) ? 1 + startingState : difficultyCounter-1;
            actionTicker = Variables.fpsCap / 4;
        }
            if(keyListener.isKeyPressed(KeyEvent.VK_ENTER)){
                if(startingState == 0){
                    if(difficultyCounter != 0){
                        startingState = 1;
                        Variables.gameMode = 1;
                        Variables.b.x = Variables.buttonX2;
                        Variables.a.caption = "EASY";
                        Variables.b.caption  =  "NORMAL";
                    }
                    else{
                        Variables.gameState = 1;
                    }
                }
                else{
                    Variables.gameDifficulty = difficultyCounter;
                    Variables.gameState = 1;

                }
                actionTicker = Variables.fpsCap / 4;


            }
        }
         Variables.a.show(difficultyCounter, offScreenGraphics);
        Variables.b.show(difficultyCounter, offScreenGraphics);
         if(startingState == 1){
             Variables.c.show(difficultyCounter, offScreenGraphics);
         }




        actionTicker--;
        g.drawImage(offscreen,0,0,null);
    }

    public void waitForGame(Graphics g){
        Image offscreen = createImage(Variables.screenWidth, Variables.screenHeight);
        Graphics offScreenGraphics = getOffScreenGraphics(offscreen);



        fpsCounter--;
        if(fpsCounter == 0){

            countdown--;
            String temp= "" + countdown;
            offScreenGraphics.fillRect(leftPaddle.getX(), leftPaddle.getY(), Variables.paddleWidth,Variables.paddleHeight);
            offScreenGraphics.fillRect(rightPaddle.getX(), rightPaddle.getY(), Variables.paddleWidth,Variables.paddleHeight);
            offScreenGraphics.drawString(Integer.toString(countdown), Variables.screenWidth / 2 - (g.getFontMetrics().stringWidth(temp)/2), Variables.screenHeight / 2);
            if(countdown == 0){
                Variables.gameState++;
            }

            fpsCounter = Variables.fpsCap;
            g.drawImage(offscreen, 0,0,null);
        }



    }
    public void drawGame(Graphics g) {
     Image offscreen = createImage(Variables.screenWidth, Variables.screenHeight);
     Graphics offScreenGraphics = getOffScreenGraphics(offscreen);

        leftPaddle.drawPaddle(offScreenGraphics);
        rightPaddle.drawPaddle(offScreenGraphics);
        ball.drawBall(offScreenGraphics);

if(!Variables.scoreDisplayUpdate){

    offScreenGraphics.drawString(Integer.toString(Variables.leftScore), Variables.sidePadding, Variables.sidePadding);
    offScreenGraphics.drawString(Integer.toString(Variables.rightScore), Variables.screenWidth - Variables.sidePadding - offScreenGraphics.getFontMetrics().stringWidth(Integer.toString(Variables.rightScore)), Variables.sidePadding);

}
     g.drawImage(offscreen,0,0,null);

    }

    private void drawEndingScreen(Graphics g) {
        boolean leftSideWon = Variables.leftScore >= Variables.maxScore;
        Image offscreen = createImage(Variables.screenWidth, Variables.screenHeight);
        Graphics offScreenGraphics = getOffScreenGraphics(offscreen);


        Variables.a.show(endingScreenCounter, offScreenGraphics);
        Variables.b.show(endingScreenCounter, offScreenGraphics);
        Variables.c.show(endingScreenCounter, offScreenGraphics);
        if(actionTicker<=0){
            if(keyListener.isKeyPressed( KeyEvent.VK_D)){
                endingScreenCounter = (endingScreenCounter == 2) ? 0 : endingScreenCounter+1;
                actionTicker = Variables.fpsCap / 4;
            }
            else if(keyListener.isKeyPressed(KeyEvent.VK_A)){
                endingScreenCounter = (endingScreenCounter == 0) ? 2 : endingScreenCounter-1;
                actionTicker = Variables.fpsCap / 4;
            }
            if(keyListener.isKeyPressed(KeyEvent.VK_ENTER)) {
                countdown = Variables.countdownDefault;
                Variables.leftScore = 0;
                Variables.rightScore = 0;
                leftPaddle.setY(Variables.defaultPaddleY);
                rightPaddle.setY(Variables.defaultPaddleY);
                ball.setX(Variables.defaultBallX);
                ball.setY(Variables.defaultBallY);
                switch (endingScreenCounter) {
                    case 0 -> Variables.gameState = 1;
                    case 1 -> {Variables.gameState = 0;
                        difficultyCounter = 0;
                        startingState = 0;
                        Variables.gameMode = 0;
                        Variables.a.caption = "PVP";
                        Variables.b.caption = "PVE";
                        Variables.b.x = Variables.buttonX3;
                        Variables.c.caption = "HARD";
                        Variables.gameDifficulty = 1;}
                    case 2 -> System.exit(0);
                }
                actionTicker = Variables.fpsCap / 4;
            }
        }


        offScreenGraphics.setFont(Variables.stringFont);
        String endGameText = Variables.gameMode == 0 ? (leftSideWon ? "Player 1 Won" : "Player 2 Won") : (leftSideWon ? "You won" : "You lost");

        offScreenGraphics.drawString(endGameText, Variables.screenWidth/2 - offScreenGraphics.getFontMetrics().stringWidth(endGameText) /2, Variables.screenHeight/3);



        g.drawImage(offscreen,0,0,null);
        actionTicker--;

    }




    public Okno(){
        this.setSize(Variables.screenWidth, Variables.screenHeight);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle(Variables.title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphics2D = (Graphics2D)this.getGraphics();
        this.addKeyListener(keyListener);
       leftPaddle = new paddle(Variables.sidePadding,Variables.defaultPaddleY, Variables.paddleWidth, Variables.paddleHeight);
       rightPaddle = new paddle(Variables.screenWidth - Variables.sidePadding - Variables.paddleWidth , Variables.defaultPaddleY, Variables.paddleWidth,  Variables.paddleHeight);
        ball = new Ball(Variables.ballSize);


    }






public void update(){

    switch (Variables.gameState) {
        case 0 -> drawStartingScreen(graphics2D);
        case 1 ->waitForGame(graphics2D);
        case 2 -> {
            leftPaddle.movePlayer(keyListener, KeyEvent.VK_W, KeyEvent.VK_S);
            if (Variables.gameMode == 0) {
                rightPaddle.movePlayer(keyListener, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
            }
            else {
                rightPaddle.moveAI(ball);
            }
            ball.move(leftPaddle, rightPaddle);
            drawGame(graphics2D);



            if(Variables.rightScore >= Variables.maxScore || Variables.leftScore >= Variables.maxScore){
                graphics2D.setFont(Variables.stringFont);
                Variables.gameState++;
                Variables.a.caption = "Restart";
                Variables.b.caption = "Main Menu";
                Variables.c.caption = "Quit";
                Variables.b.x = Variables.buttonX2;

            }

        }
        case 3 -> drawEndingScreen(graphics2D);
    }







}




    @Override
    public void run() {
while(true){
    
    updateTimer.start();
}


    }
}
