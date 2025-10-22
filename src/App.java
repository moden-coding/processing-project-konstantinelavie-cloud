import processing.core.*;

public class App extends PApplet {
  float heroX = 1500;
  float heroY = 500;
  float size = 64;
  float baseY = 500 + size;
  float baseX = 1500 + size;
  float spikeX = 900;
  float spikeY = heroY;
  float spikeWidth = 80;
  float spikeHeight = 64;
  boolean stopped = false; // becomes true when the cube hits the spike
  float speed = 5; // how fast the cube moves left
  // jumping stuff...
  float ySpeed = 0; // vertical speed
  boolean jumping = false; // keeps track if cube is in the air

  public boolean spikecollisionandalign() { // returns true if the square is touching (or overlapping) the spike
    float rightBaseX = spikeX + spikeWidth;
    float apexX = rightBaseX / 2;

    // overlap between triangle and square from top to bottom (vertical)
    float overlapTop = max(heroY, heroY); // (500, 500)
    float overlapBottom = min(baseY, baseY); // (564, 564)

    if (overlapTop > overlapBottom) {
      // no vertical overlap -> no collision
      return false; 
    }

    // some code to find left and right triangle edge "x" at a given vertical

    float tTop = (overlapTop - heroY) / (baseY - heroY); // (max(500, 500) - 64) / (564 - 64) == 1.0
    float leftAtTop = apexX + (spikeX - apexX) * tTop;
    float rightAtTop = apexX + (rightBaseX - apexX) * tTop;

    float tBottom = (overlapBottom - heroY) / (baseY - heroY);
    float leftAtBottom = apexX + (spikeX - apexX) * tBottom;
    float rightAtBottom = apexX + (rightBaseX - apexX) * tBottom;

    // the triangle horizontal extent over the overlapping vertical span:
    float triLeftMax = min(leftAtTop, leftAtBottom); // left-most
    float triRightMax = max(rightAtTop, rightAtBottom); // right-most (this matters for first contact)

    // If square's left edge has reached the triangle's right edge (and vertical
    // overlap exists),
    // that's a contact (cube moving left).
    if (1500 <= triRightMax && baseX >= triLeftMax) {
      heroX = triRightMax;
      return true;
    }
    if (heroX <= tTop && baseX >= tBottom) {
      heroX = 32;
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
    background(10, 10, 120); // dark blue background

    // base
    stroke(50);
    fill(0);
    rect(0, 500 + size, width, 200);

    // spike (red triangle)
    strokeWeight(2);
    stroke(255);
    fill(0);
    float baseY = 500 + size; // top of base
    triangle(spikeX, baseY, spikeX + spikeWidth, baseY, spikeX + spikeWidth / 2, baseY - spikeHeight);

    // cube

    // --- cube movement ---
    heroX = heroX - 5;
    if (heroX < -size) {
      heroX = 1500; // reset cube when off screen
    }

    // --- gravity and jumping ---
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

    // --- collision detection with spike ---
    
    float spikeTop = 500 + size - spikeHeight;

    if (baseX > spikeX && 1500 < spikeX + spikeWidth && baseY > spikeTop) {
      noLoop(); // stop the sketch when collision happens
    }
    // cube
    stroke(0);
    strokeWeight(3);
    fill(255);
    rect(heroX, heroY, size, size);
    
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
