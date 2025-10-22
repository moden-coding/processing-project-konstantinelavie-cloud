import processing.core.*;

public class App extends PApplet {
    float x = 1500;
    float y = 500;
    float size = 64;
    float baseY = y + size;
    float baseX = x + size;
    // spike position and size
    float spikeX = 900;
    float spikeWidth = 80;
    float spikeHeight = 64;
    boolean stopped = false; // becomes true when the cube hits the spike
    float speed = 5; // how fast the cube moves left
    // jumping stuff...
    float ySpeed = 0; // vertical speed
    boolean jumping = false; // keeps track if cube is in the air

    


    public boolean spikecollision() { // returns true if the square is touching (or overlapping) the spike 
        float rightBaseX = spikeX + spikeWidth;
        float apexX = rightBaseX / 2;
    

        // overlap between triangle and square from top to bottom (vertical)
        float overlapTop = max(y, y); // (500, 500)
        float overlapBottom = min(baseY, baseY); // (564, 564)

        if (overlapTop > overlapBottom) {
            // no vertical overlap -> no collision
            return false;
        }

        // some code to find left and right triangle edge "x" at a given vertical
       
        float tTop = (overlapTop - y) / (baseY - y); // (max(500, 500) - 64) / (564 - 64) == 1.0
        float leftAtTop = apexX + (spikeX - apexX) * tTop;
        float rightAtTop = apexX + (rightBaseX - apexX) * tTop;

        float tBottom = (overlapBottom - y) / (baseY - y);
        float leftAtBottom = apexX + (spikeX - apexX) * tBottom;
        float rightAtBottom = apexX + (rightBaseX - apexX) * tBottom;

        // the triangle horizontal extent over the overlapping vertical span:
        float triLeftMax = min(leftAtTop, leftAtBottom); // left-most
        float triRightMax = max(rightAtTop, rightAtBottom); // right-most (this matters for first contact)

        // If square's left edge has reached the triangle's right edge (and vertical
        // overlap exists),
        // that's a contact (cube moving left).
        if (x <= triRightMax && baseX >= triLeftMax) {
            // Align the square so its left edge sits flush with the triangle's rightmost x
            // (this ensures the cube stops exactly where it first touches)
            x = triRightMax;
            return true;
        }

        return false;
    }

    // This method sets the size of the window
    public void settings() {
        size(1500, 700);

      

    }

    // This method runs once when the program starts
    public void draw() {
  background(10, 10, 120);  // dark blue background

  // base
  

  // spike (red triangle)
  strokeWeight(2);
  stroke(255);
  fill(0);
  float baseY = y + size; // top of base
  triangle(spikeX, baseY, 
           spikeX + spikeWidth, baseY, 
           spikeX + spikeWidth / 2, baseY - spikeHeight);

  // cube
 

  // --- cube movement ---
  x = x - 5;
  if (x < -size) {
    x = 1500; // reset cube when off screen
  }

  // --- gravity and jumping ---
  if (jumping) {
    y = y + ySpeed;
    ySpeed = ySpeed + 1; // gravity pulls down

    // stop at the ground
    if (y > 500) {
      y = 500;
      ySpeed = 0;
      jumping = false;
    }
  }

  // --- collision detection with spike ---
  float cubeRight = x + size;
  float cubeBottom = y + size;
  float spikeTop = 500 + size - spikeHeight;
  
  if (cubeRight > spikeX && x < spikeX + spikeWidth && cubeBottom > spikeTop) {
    noLoop(); // stop the sketch when collision happens
  }
 // cube
   stroke(0);
  strokeWeight(3);
  fill(255);
  rect(x, y, size, size);
}

public void keyPressed() {
  // If spacebar is pressed and cube is NOT already jumping
  if (!jumping && key == ' ') {
    jumping = true; 
    ySpeed = -20; // jump upward
  }
}
    // This is the entry point of your Java program
    public static void main(String[] args) {
        PApplet.main("App");
    }
} 
