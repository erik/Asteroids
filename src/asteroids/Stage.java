package asteroids;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * TODO: make getters and setters, not public everything, damn it!
 */
public class Stage extends JPanel implements ActionListener {

    private double fps = System.currentTimeMillis(), newFPS = System.currentTimeMillis();
    private int lives;
    private Asteroid[] roids;
    private boolean debug;
    private Image background, explosion[];
    private Point origin;
    private boolean gameOn;
    private boolean keyUp = false, keyDown = false, keyLeft = false,
            keyRight = false, keySpace = false, keyShift = false, keyStart = false, replay = false;
    private Ship craft;
    private boolean gameOver, exploding;
    private boolean invin;
    private final int moveAmt = 1, radians = 3;
    private int width, height, score, asteroidCount;
    private double explosionStage;
    private Clock time;
    /*massive accessor/mutator block*/

    //getters
    public double getFPS() {
        return fps;
    }

    public int getLives() {
        return lives;
    }

    public Asteroid[] getAsteroids() {
        return roids;
    }

    public boolean getDebug() {
        return debug;
    }

    public Image[] getExplosion() {
        return explosion;
    }

    public Point getOrigin() {
        return origin;
    }

    public boolean gameOn() {
        return gameOn;
    }

    public boolean getInvin() {
        return invin;
    }
    //setters

    public void setKeyUp(boolean val) {
        keyUp = val;
    }

    public void setKeyDown(boolean val) {
        keyDown = val;
    }

    public void setKeyLeft(boolean val) {
        keyLeft = val;
    }

    public void setKeyRight(boolean val) {
        keyRight = val;
    }

    public void setKeySpace(boolean val) {
        keySpace = val;
    }

    public void setKeyShift(boolean val) {
        keyShift = val;
    }

    public void setKeyStart(boolean val) {
        keyStart = val;
    }

    public void setGameOn() {
        gameOn = true;
    }

    public void setGameOff() {
        gameOn = false;
    }

    public void setDebugOn() {
        debug = true;
    }

    public void setDebugOff() {
        debug = false;
    }

    public void setInvinOn() {
        invin = true;
    }

    public void setInvinOff() {
        invin = false;
    }

    public Stage(int w, int x, String backImg) throws Exception {
        this.setBackground(Color.BLACK);
        this.setBackground(this.loadImage(backImg));
        super.setSize(w, x);

        origin = new Point(this.getWidth() / 2, this.getHeight() / 2);
        craft = new Ship(origin.x, origin.y, this.loadImage("ship.png"));

        gameOn = true;
        gameOver = false;
        debug = false;

        width = this.getWidth();
        height = this.getHeight();

        Asteroid.large = this.loadImage("asteroidLarge.png");
        Asteroid.medium = this.loadImage("asteroidMedium.png");
        Asteroid.small = this.loadImage("asteroidSmall.png");
        Asteroid.driftSpeed = 5;

        asteroidCount = 5;

        lives = 3;
        score = 0;

        explosion = new Image[12];
        this.loadExplosions();

        loadSounds();

        Timer clock = new Timer(25, this);
        clock.start();

        time = new Clock();
    }

    public void actionPerformed(ActionEvent e) {
        fps = 1000 / (System.currentTimeMillis() - newFPS);
        if (roids == null || roids.length == 0) {
            try {
                spawn.play();
                asteroidCount += 3;
                roids = new Asteroid[asteroidCount];
                instantiateAsteroids();
            } catch (Exception ex) {
            }
        }
        repaint();
        newFPS = System.currentTimeMillis();

    }

    @Override
    public void paintComponent(Graphics g) {
        if (gameOn) {
            super.paintComponent(g);
            //this must be called first!
            drawBackground(g);
            parseInput();
            craft.drift();
            moveAsteroids();
            moveBullets();
            boundsCheck();



            if (!gameOver) {
                checkCollisions();
                if (invin) {
                    drawInvinNotice(g);
                }
                drawBullets(g);
                drawTimer(g);
                if (exploding) {
                    craft = new Ship(origin.x, origin.y, this.loadImage("ship.png"));
                    drawExplosions(g);
                } else {
                    drawCraft(g);
                }
            } else {
                if (exploding) {
                    drawExplosions(g);
                }
            }
            drawAsteroids(g);
            drawLives(g);
            drawScore(g);
            drawFPS(g, this.getHeight());
            drawAmmunition(g);
            if (gameOver) {
                drawGameOver(g);
            }

        } else {
            if (!gameOver) {
                drawPauseScreen(g);
            }
        }
    }

    public void parseInput() {
        if (keyRight) {
            craft.rotate(radians);
        }
        if (keyLeft) {
            craft.rotate(-radians);
        }
        if (keyUp) {
            craft.move(new Point(moveAmt, -moveAmt));
            keyUp = false;
        }
        if (keyDown) {
            int tempX = craft.pull.x;
            int tempY = craft.pull.y;

            tempX = tempX == 0 ? 0 : tempX > 0 ? tempX - 1 : tempX + 1;
            tempY = tempY == 0 ? 0 : tempY > 0 ? tempY - 1 : tempY + 1;
            craft.pull = new Point(tempX, tempY);
        }
        if (keySpace) {
            if (!gameOver) {
                addBullet(craft.fire());
                keySpace = false;
            }
        }
        if (keyShift) {
            craft.setXPos((int) (Math.random() * this.getWidth()));
            craft.setYPos((int) (Math.random() * this.getHeight()));
            teleport.play();
            keyShift = false;
        }
        if (keyStart) {
            keyStart = false;
            if (gameOver || !gameOn) {
                spawn.play();
                replay = true;
                score = 0;
                craft = new Ship(origin.x, origin.y, this.loadImage("ship.png"));
                asteroidCount = 5;
                roids = new Asteroid[asteroidCount];
                try {
                    instantiateAsteroids();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }

                lives = 3;
                score = 0;
                gameOver = false;
                gameOn = true;

            }
        }
    }

    public void setBackground(Image img) {
        background = img;
    }

    public Image loadImage(String imageName) {
        Image image = null;
        URL fileLocation = getClass().getResource("images/" + imageName);
        image = Toolkit.getDefaultToolkit().getImage(fileLocation);
        this.prepareImage(image, null);
        while (image.getWidth(null) == -1) {
        }//wait for image to be ready
        return image;
    }

    public void loadExplosions() {
        for (int i = 1; i < explosion.length + 1; i++) {
            String fetchName = String.format("Explosion%d.png", i);
            explosion[i - 1] = this.loadImage(fetchName);
        }
    }

    public void boundsCheck() {
        if (!this.getBounds().intersects(craft.hitBox)) {
            if (craft.hitBox.x < 0) {
                craft.setXPos(this.getWidth());
            } else if (craft.hitBox.x > this.getWidth()) {
                craft.setXPos(0);
            }
            if (craft.hitBox.y < 0) {
                craft.setYPos(this.getHeight());
            } else if (craft.hitBox.y > this.getHeight()) {
                craft.setYPos(0);
            }
        }
        asteroidBoundsCheck();
        bulletsBoundsCheck();
    }

    public void asteroidBoundsCheck() {
        for (int i = 0; i < roids.length; i++) {
            if (!this.getBounds().intersects(roids[i].hitBox)) {
                if (roids[i].hitBox.x < 0) {
                    roids[i].setXPos(this.getWidth());
                } else if (roids[i].hitBox.x > this.getWidth()) {
                    roids[i].setXPos(0 - roids[i].hitBox.width);
                }
                if (roids[i].hitBox.y < 0) {
                    roids[i].setYPos(this.getHeight());
                } else if (roids[i].hitBox.y > this.getHeight()) {
                    roids[i].setYPos(0 - roids[i].hitBox.height);
                }
            }
        }
    }

    public void bulletsBoundsCheck() {
        ArrayList<Bullet> tempList = new ArrayList<Bullet>();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet temp = bullets.get(i);
            if (!this.getBounds().intersects(temp.hitBox)) {
                if (temp.hitBox.x < 0) {
                    temp.setXPos(this.getWidth());
                } else if (temp.hitBox.x > this.getWidth()) {
                    temp.setXPos(0);
                }
                if (temp.hitBox.y < 0) {
                    temp.setYPos(this.getHeight());
                } else if (temp.hitBox.y > this.getHeight()) {
                    temp.setYPos(0);
                }
                bullets.remove(i);
                tempList.add(temp);
            }
        }
        bullets.addAll(tempList);
    }
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public Point[] explosionLoc = new Point[1];
    public int[] explosionNum = new int[1];
    public Point explosionLocation;
    public Sound spawn, laser, explosionShip, explosionAsteroid, teleport;
    public int maxDist = 200;
    public int maxBullets = 5;

    public void moveAsteroids() {
        for (int i = 0; i < roids.length; i++) {
            roids[i].drift();
        }
    }

    public void instantiateAsteroids() throws Exception {
        for (int i = 0; i < roids.length; i++) {
            roids[i] = new Asteroid((int) (Math.random() * 3) + 1);
            roids[i].setXPos((int) (Math.random() * width));
            roids[i].setYPos((int) (Math.random() * height));
            while (roids[i].collidesWith(craft.hitBox) || Math.abs(roids[i].xPos - craft.xPos) < maxDist) {
                roids[i] = new Asteroid((int) (Math.random() * 3) + 1);
                roids[i].setXPos((int) (Math.random() * width));
                roids[i].setYPos((int) (Math.random() * height));

            }
        }
    }

    public void checkCollisions() {
        checkBulletCollisions();
        if (!invin && !gameOver) {
            for (int i = 0; i < roids.length; i++) {
                if (craft.collidesWith(roids[i].hitBox)) {
                    explosionShip.play();
                    exploding = true;
                    explosionStage = 0;
                    explosionLocation = new Point(roids[i].xPos, roids[i].yPos);
                    craft.setXPos(origin.x);
                    craft.setYPos(origin.y);
                    craft.pull = new Point(0, 0);
                    lives--;
                    removeAsteroid(i);
                    if (lives == 0) {
                        gameOver = true;
                    }
                }
            }
        }
    }

    public void removeAsteroid(int index) {
        Asteroid[] temp = new Asteroid[roids.length - 1];
        for (int i = 0, j = 0; i < roids.length; i++) {
            if (!(i == index)) {
                temp[j] = roids[i];
                j++;
            } else {
                score += 25 * roids[i].scale;
            }
        }
        roids = temp;
    }

    public void addAsteroid(Asteroid a) {
        Asteroid temp[] = new Asteroid[roids.length + 1];
        temp[0] = a;
        System.arraycopy(roids, 0, temp, 1, roids.length);
        roids = temp;
    }

    public void addBullet(Bullet fire) {
        if (bullets.size() < maxBullets) {
            laser.play();
            bullets.add(fire);
        }
    }

    private void checkBulletCollisions() {
        if (!(bullets.size() == 0)) {
            for (int i = 0; i < roids.length && roids.length != 0; i++) {
                for (int j = 0; j < bullets.size(); j++) {
                    if (bullets.size() > 0) {
                        Bullet temp = bullets.get(j);
                        if (temp.collidesWith(roids[i].hitBox)) {
                            explosionAsteroid.play();
                            score += 25 * roids[i].scale;
                            addExplosion(roids[i].xPos, roids[i].yPos);
                            if (roids[i].scale == 1) {
                                removeAsteroid(i);
                                i = 0;
                            } else {
                                try {
                                    roids[i] = new Asteroid(roids[i].scale - 1, roids[i].xPos, roids[i].yPos);
                                    addAsteroid(new Asteroid(roids[i].scale, roids[i].xPos, roids[i].yPos));
                                } catch (Exception ex) {
                                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
    }

    public void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet temp = bullets.get(i);
            temp.move();
            bullets.remove(i);
            if (!temp.die) {
                bullets.add(temp);
            }
        }
    }

    public void addExplosion(int x, int y) {
        if (explosionLoc != null) {
            int[] tempNum = explosionNum;
            Point[] tempLoc = explosionLoc;
            explosionLoc = new Point[tempLoc.length + 1];
            explosionNum = new int[tempNum.length + 1];
            explosionNum[tempNum.length] = 1;
            explosionLoc[tempLoc.length] = new Point(x, y);
            for (int i = 0; i < tempNum.length - 1; i++) {
                explosionLoc[i] = tempLoc[i];
                explosionNum[i] = tempNum[i];
            }
        } else {
            explosionNum = new int[1];
            explosionNum[0] = 1;
            explosionLoc = new Point[1];
            explosionLoc[0] = new Point(x, y);
        }
    }

    public void loadSounds() {
        spawn = new Sound("spawn.wav");
        laser = new Sound("fire2.wav");
        explosionAsteroid = new Sound("explosion2.wav");
        explosionShip = new Sound("explosion.wav");
        teleport = new Sound("fire.wav");
    }

    public void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }

    public void drawFPS(Graphics g, int h) {
        g.setColor(Color.DARK_GRAY);
        g.drawString(String.format("\tFPS: %.2f", fps), 0, h - 20);
    }

    public void drawCraft(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform orig = g2d.getTransform();
        AffineTransform at = (AffineTransform) orig.clone();
        at.translate(craft.xPos, craft.yPos);
        at.translate(craft.img.getWidth(null) / 2, craft.img.getHeight(null) / 2);
        at.rotate(Math.toRadians(craft.degrees));
        //
        at.translate(-craft.xPos, -craft.yPos);
        at.translate(-craft.img.getWidth(null) / 2, -craft.img.getHeight(null) / 2);
        g2d.setTransform(at);
        g2d.drawImage(craft.img, at.getTranslateInstance(craft.xPos, craft.yPos), null);
        g2d.setTransform(orig);
        if (debug) {
            g2d.setColor(Color.WHITE);
            g2d.drawRect(craft.hitBox.x, craft.hitBox.y, craft.img.getWidth(null), craft.img.getHeight(null));
        }
    }

    public void drawPauseScreen(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 36));
        g.drawString("PAUSED", ((int) (width
                - g.getFontMetrics().getStringBounds("PAUSED", g).getWidth())
                / 2), height / 2);
    }

    public void drawAsteroids(Graphics g) {
        for (Asteroid a : roids) {
            g.drawImage(a.img, a.xPos, a.yPos, null);
            if (debug) {
                g.drawRect(a.hitBox.x, a.hitBox.y, a.img.getWidth(null), a.img.getHeight(null));
            }
        }
    }

    public void drawLives(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g.drawString("Lives remaining: " + lives, 20, 40);
    }

    public void drawGameOver(Graphics g) {
        g.setColor(new Color(0x00, 0x72, 0xFF));
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 100));
        g.drawString("GAME OVER", ((int) (width
                - g.getFontMetrics().getStringBounds("GAME OVER", g).getWidth())
                / 2), height / 3);
        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
        String message = "s to start";
        g.drawString(message, ((int) (width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), height / 2);
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g.drawString("Score: " + score, width - 100, 40);
    }

    public void drawBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet temp = bullets.get(i);
            g.setColor(Color.RED);
            g.fillOval(temp.xPos, temp.yPos, temp.width, temp.height);
            if (debug) {
                g.setColor(Color.WHITE);
                g.drawRect(temp.hitBox.x, temp.hitBox.y, temp.width, temp.height);
            }
        }
    }

    public void drawExplosions(Graphics g) {
        int x = explosionLocation.x;
        int y = explosionLocation.y;
        if (explosionStage < explosion.length - 1) {
            explosionStage += .3;
            g.drawImage(explosion[(int) explosionStage], x, y, null);
        } else {
            exploding = false;
        }
    }

    public void drawAmmunition(Graphics g) {
        int startX = width - maxBullets * 10;
        int startY = height - 40;
        int endX = (maxBullets - bullets.size()) * 10;
        int endY = height - 20;
        g.setColor(Color.RED);
        g.drawRect(startX, startY, endX, endY);
        g.setColor(Color.orange);
        g.fillRect(startX + 1, startY + 1, endX - 1, endY - 1);
    }

    public void drawInvinNotice(Graphics g) {
        String message = "You are now invincible";
        g.setColor(Color.GREEN);
        g.drawString(message, ((int) (width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), 40);
    }

    public void drawTimer(Graphics g) {
        g.setColor(Color.WHITE);
        String message = time.toString();
        g.drawString(message, ((int) (width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), 20);
    }
}



