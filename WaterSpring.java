public class WaterSpring{
    public float posX;
    public float posY;
    public int constantY;
    public float speed;

    private final float tensionK = 0.02f;
    private final float dampen = 0.05f;

    /* 
    picture the water made up of column like rectangles. Each rectangle will act like a spring to give us a realistic water motion.
    Speed = (tension/mass) *displacement --> however notice that mass doesnt really matter here, so it simplifies to:
    Speed = Tension * displacement
 */
    public WaterSpring(float x,float y){
        posX = x;
        posY = y;
        constantY = (int) y;
        speed = 0;
    }
    public void update(){
        //y is our displacement, it shows calculates how far the new Y point has shifted from the original
        float y = constantY - posY;

        //Spring stretching --> speed = tension * displacement
        y =  tensionK * y;
    
        //apply force opposite of direction it is going so it doesnt continuously spring back and forth
        if (speed<0){ 
            y += dampen;
        }
        else{
            y-= dampen;
        }
        speed+=y;
        posY += speed;
    }


	
}