import java.awt.*;

public class Variables {
    public static String title = "PONG";
    public static int sidePadding = 80;
    public static final int screenWidth = 800;
    public static final int screenHeight = 800;

    public static final double maxBounceAngle = 75;
    public static final int ballSize = 10;
    public static final int defaultBallX = (screenWidth - ballSize) / 2;
    public static final int defaultBallY = (screenHeight - ballSize) / 2;

    public static final int paddleHeight = 100;
    public static final int paddleWidth = 10;
    public static Font buttonFont = new Font("SansSerif", Font.BOLD, 30);
    public static Font stringFont = new Font( "SansSerif", Font.BOLD, 50);

    public static final int defaultPaddleY = (Variables.screenHeight / 2)  - paddleHeight;


    public static final int fpsCap = 100;

    public static int gameDifficulty = 0;

    // 0 = choosing gameMode, 1 = game, 2 = ending screen
    public static  int gameState = 0;
    //0 = pvp, 1 = pve
    public static int gameMode = 0;



    public static int buttonSize = 50;
    public static int buttonCorrection = buttonSize /2;
    public static int buttonY = (int)(screenHeight * 0.75) -  buttonCorrection;
    public static int buttonX1 = (int)(screenWidth * 0.25) -  buttonCorrection;
    public static int buttonX2 = (int)(screenWidth * 0.5) -  buttonCorrection;
    public static int buttonX3 = (int)(screenWidth * 0.75) -  buttonCorrection;

   static button a = new button(Variables.buttonX1, Variables.buttonY, Variables.buttonSize, 0,  "PVP" );
   static button b = new button(Variables.buttonX3, Variables.buttonY, Variables.buttonSize, 1, "PVE");
   static button c = new button(Variables.buttonX3, Variables.buttonY, Variables.buttonSize, 2,"HARD");



    public static int leftScore = 0;
    public static int rightScore = 0;
    public static int maxScore = 5;
     public static boolean scoreDisplayUpdate = false;

     public static final int countdownDefault = 5;



}
