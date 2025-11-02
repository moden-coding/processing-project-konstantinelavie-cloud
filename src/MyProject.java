import processing.core.*;

public class MyProject extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MyProject");
    }

    // window
    int windowWidth = 1500;
    int windowHeight = 700;

    // white rect
    float rectX = 1500;
    float rectY = 500;
    float rectSize = 64;
    float rectXSpeed = 5;

    // rect jumping
    float rectYSpeed = 0;
    boolean jumping = false;

    // black rect enemies & size
    float blackRectY = 500;
    float blackRectSize = 64;
    float blackRect1, blackRect2, blackRect3, blackRect4, blackRect5;
    int blackRectCount = 3; // how many black rect enemies are currently active (will randomize). it will
                            // start at 3.

    // scenes
    int scene = 1;

    // keeps track of the score when you reach the other side
    int score = 0;
    int highScore = 0; // keeps track of the all time highscore

    // game control (stop/continue)
    boolean stopped = false;

    public void settings() {
        size(windowWidth, windowHeight); // sets the size of the window to be 1500 * 700
        randomizeObstacles();
    }

    public void draw() {
        background(10, 10, 100);

        // enter game
        if (scene == 1) {
            textAlign(CENTER, CENTER);
            fill(255);
            textSize(70);
            text("Endless Geometry Dash", width / 2, height / 2 - 100);
            textSize(40);
            text("Press ENTER to start", width / 2, height / 2 + 20);
            textSize(30);
            text("Press SPACE to jump", width / 2, height / 2 + 70);

            strokeWeight(2);
            stroke(255);
            fill(0);
            rect(0, 500 + rectSize, width, 200);

            strokeWeight(2);
            stroke(0);
            fill(255);
            rect(rectX, rectY, rectSize, rectSize);

            rectX -= rectXSpeed;
            if (rectX < -rectSize) {
                rectX = windowWidth;
            }

            if (jumping) {
                rectY += rectYSpeed;
                rectYSpeed += 1;
                if (rectY > 500) {
                    rectY = 500;
                    rectYSpeed = 0;
                    jumping = false;
                }
            }
        }
        
        if (score > highScore) {
            highScore = score;
        }
        // gameplay
        if (scene == 2) {
           fill(255);
           textSize(30);
           text("Score: " + score, 60, 15);
           text("Current Speed: " + rectXSpeed, 125, 50);
            
            // draw base
            strokeWeight(2);
            stroke(255);
            fill(0);
            rect(0, 500 + rectSize, width, 200);

            // move white rectangle
            rectX -= rectXSpeed;
            if (rectX < -rectSize) {
                rectX = windowWidth;
                score++;
                randomizeObstacles();
                if (rectXSpeed <= 10) {
                    rectXSpeed++;
                }
            }

            // jumping + gravity
            if (jumping) {
                rectY += rectYSpeed;
                rectYSpeed += 1;
                if (rectY > 500) {
                    rectY = 500;
                    rectYSpeed = 0;
                    jumping = false;
                }
            }

            // check collisions
            if (collisionDetection()) {
                scene = 3;
            }

            // draw white player
            strokeWeight(2);
            stroke(0);
            fill(255);
            rect(rectX, rectY, rectSize, rectSize);

            // enemies
            fill(0);
            stroke(255);
            if (blackRectCount >= 1) {
                rect(blackRect1, blackRectY, blackRectSize, blackRectSize);
            }
            if (blackRectCount >= 2) {
                rect(blackRect2, blackRectY, blackRectSize, blackRectSize);
            }
            if (blackRectCount >= 3) {
                rect(blackRect3, blackRectY, blackRectSize, blackRectSize);
            }
            if (blackRectCount >= 4) {
                rect(blackRect4, blackRectY, blackRectSize, blackRectSize);
            }
            if (blackRectCount >= 5) {
                rect(blackRect5, blackRectY, blackRectSize, blackRectSize);
            }


        }

        
        // game over
        if (scene == 3) {
            background(80, 0, 0);
            textAlign(CENTER, CENTER);
            fill(255);
            textSize(80);
            text("GAME OVER", width / 2, height / 2 - 150);
            textSize(40);
            text("HIGHSCORE: " + highScore, width / 2, height / 2 - 50);
            text("Score: " + score + "    " + " Final Speed: " + rectXSpeed, width / 2, height / 2 + 50);
            text("Press R to Restart Game", width / 2, height / 2 + 150);
            textSize(30);
            text("Or Press M to Return to Menu", width / 2, height / 2 + 250);
        }
    }

    // collision detection
    public boolean collisionDetection() {
        float whiteLeft = rectX;
        float whiteRight = rectX + rectSize;
        float whiteTop = rectY;
        float whiteBottom = rectY + rectSize;

        if (blackRectCount >= 1
                && rectOverlap(whiteLeft, whiteRight, whiteTop, whiteBottom, blackRect1, blackRectY, blackRectSize)) {
            return true;
        }
        if (blackRectCount >= 2
                && rectOverlap(whiteLeft, whiteRight, whiteTop, whiteBottom, blackRect2, blackRectY, blackRectSize)) {
            return true;
        }
        if (blackRectCount >= 3
                && rectOverlap(whiteLeft, whiteRight, whiteTop, whiteBottom, blackRect3, blackRectY, blackRectSize)) {
            return true;
        }
        if (blackRectCount >= 4
                && rectOverlap(whiteLeft, whiteRight, whiteTop, whiteBottom, blackRect4, blackRectY, blackRectSize)) {
            return true;
        }
        if (blackRectCount >= 5
                && rectOverlap(whiteLeft, whiteRight, whiteTop, whiteBottom, blackRect5, blackRectY, blackRectSize)) {
            return true;
        }

        return false; //code is the same for all black rectangles to detect whether they intersect at any point, or not.
    }

    // helper method: checks overlap between white rect and ONE black rect
    public boolean rectOverlap(float whiteLeft, float whiteRight, float whiteTop, float whiteBottom,
            float blackX, float blackY, float size) { // this is something i got feedback from chat gpt as it detects
                                                      // every square individually for when you run the game, and it is
                                                      // alot more efficient then the return command below, as written:
        float blackLeft = blackX;
        float blackRight = blackX + size;
        float blackTop = blackY;
        float blackBottom = blackY + size;

        return whiteRight > blackLeft &&
                whiteLeft < blackRight &&
                whiteBottom > blackTop &&
                whiteTop < blackBottom; // beforehand, i used the return method to detect all sides and it looked very
                                        // clumped and confusing to me because there was alot of code that i couldnt
                                        // understand. i asked chatgpt for feedback on whether i was making my code too
                                        // complicated and it told me to create a new method that detects each
                                        // individual side for the white and black rectangles (or squares) for when they
                                        // intersect. this is a version of what it reccomended, and it seemed to work well enough
                                        // to the point where it detects an accurate collision.

    }

    public void randomizeObstacles() {
        // Always at least 2 cubes, up to 5 (including both 2 and 5)
        blackRectCount = (int) random(2, 6); // random(2, 6) gives 2–5 inclusive

        // Start a bit more to the right (not near the left edge)
        float start = random(600, 900); // start within the visible screen

        // Spacing between cubes
        float spacingMin = 280;
        float spacingMax = 420;

        // Place each cube
        blackRect1 = start;
        blackRect2 = blackRect1 + random(spacingMin, spacingMax);
        blackRect3 = blackRect2 + random(spacingMin, spacingMax);
        blackRect4 = blackRect3 + random(spacingMin, spacingMax);
        blackRect5 = blackRect4 + random(spacingMin, spacingMax);

        // If the last cube is offscreen, shift them all left a bit
        float right = 0;
        if (blackRectCount == 2)
            right = blackRect2;
        else if (blackRectCount == 3)
            right = blackRect3;
        else if (blackRectCount == 4)
            right = blackRect4;
        else if (blackRectCount == 5)
            right = blackRect5;

        if (right > windowWidth - 275) {
            float shift = right - (windowWidth - 275);
            blackRect1 -= shift;
            blackRect2 -= shift;
            blackRect3 -= shift;
            blackRect4 -= shift;
            blackRect5 -= shift;
        }

        if (blackRectCount >= 3) {
            rectXSpeed = 6;
        } else {
            rectXSpeed++;
        }
    }

    // key control
    public void keyPressed() {
        if (!jumping && key == ' ') {
            jumping = true;
            rectYSpeed = -20; // jump upward
        }
        if (key == ENTER && scene == 1) {
            scene = 2;
            rectX = windowWidth + 100;
            randomizeObstacles();
        }

        // if (collisionDetection() && scene == 2) {
        // scene = 3;
        // }

        if (scene == 3 && key == 'r') {
            scene = 2;
            rectX = windowWidth;
            rectXSpeed = 5;
            score = 0;
            randomizeObstacles();
        }
        if (scene == 3 && key == 'm') {
            scene = 1;
            rectXSpeed = 5;
            rectX = windowWidth;
            rectY = 500;
            score = 0;
            randomizeObstacles();
        }

    }
}
