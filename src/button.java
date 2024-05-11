import java.awt.*;

public class button {

   public int size, sizeActivated, sizeDiff, displaySize, x, y, displayX, displayY, id;
   public String caption;







    public button(int x, int y, int size, int id, String caption){
this.size = size;
this.x = x;
this.y = y;
this.displaySize = size;
this.id = id;
this.caption = caption == null ? "" : caption;
    }




    public void show(int counter, Graphics graphics){
        sizeActivated = (int)(size*1.2);
        sizeDiff = (sizeActivated - size)/2;
        if(counter == id){

            displayX = x - sizeDiff;
            displayY = y - sizeDiff;
            displaySize = sizeActivated;
            Font previousFont = graphics.getFont();
            graphics.setFont(Variables.buttonFont);
            graphics.drawString(caption, displayX  + (displaySize / 2 )- (graphics.getFontMetrics().stringWidth(caption) /2), displayY - displaySize);
            graphics.setFont(previousFont);
        }
        else{
            displayX = x;
            displayY = y;
            displaySize = size;
        }
        graphics.fillRect(displayX,displayY,displaySize, displaySize);


    }

}
