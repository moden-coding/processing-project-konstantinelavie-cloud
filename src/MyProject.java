import processing.core.*;

public class MyProject extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MyProject");
    }

    float x = 1500;
    float y = 500;
    float size = 64;
    float heroX = 1500;
    float heroY = 500;
    float ySpeed = 0;
    boolean jumping = false;
    boolean stopped = false; // true when collision
    float speed = 5;
    int scene = 1;
    public void settings() {
        size(1500, 700);
    }

    public void draw() {
        background(10, 10, 100); // dark blue background

        stroke(50);
        fill(0);
        rect(0, 500 + size, width, 200);

        stroke(0);
        strokeWeight(3);
        fill(255);
        rect(heroX, heroY, size, size);
        heroX = heroX - 5;
        if (heroX < -size) {
            heroX = 1500; // reset cube when off screen
        }
       

        if (jumping) {
            heroY = heroY + ySpeed;
            ySpeed = ySpeed + 1; // gravity pulls down

            // stop at the ground
            if (heroY > 500) {
                heroY = 500;
                ySpeed = 0;
                jumping = false;
            }
        }

        strokeWeight(2);
        stroke(255);
        fill(0);
        rect(900, 300, size, size);

    }

   // public boolean collisionDetection() {
      
        
    

    public void keyPressed() {
        // If spacebar is pressed and cube is NOT already jumping
        if (!jumping && key == ' ') {
            jumping = true;
            ySpeed = -20; // jump upward
        }
    }
}
