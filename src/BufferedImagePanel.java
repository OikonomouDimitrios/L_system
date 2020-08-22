
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePanel extends JPanel {
    private BufferedImage image;
    private Graphics2D g2;
    private Graphics2D g;
    private String letterSequence;
//    Here, F means "draw forward", − means "turn left 90°", and + means "turn right 90°". X and Y do not correspond to any drawing action and are only used to control the evolution of the curve.

    private final char Forward = 'F';
    private final char LeftTurn = '-';
    private final char RightTurn = '+';
    private int ForwardPixels ;

    public BufferedImage getImage() {
        return image;
    }

    public void playGame(int forwardPixels, String letterSequence) {
        this.letterSequence = letterSequence;
        draw(forwardPixels);
    }

    public BufferedImagePanel() {
        image = new BufferedImage(DragonCurveGUI.WIDTH, DragonCurveGUI.HEIGHT, BufferedImage.TYPE_INT_BGR);
        g2 = (Graphics2D) image.getGraphics();
    }

    public void draw(int forwardPixels) {
        g2.setColor(Color.white);
        g2.fillRect(0, 0, DragonCurveGUI.WIDTH, DragonCurveGUI.HEIGHT);
        DirectionVector startingVector = new DirectionVector(0, DragonCurveGUI.HEIGHT/2, Direction.NORTH);
        DirectionVector finalVector;
        var seq = this.letterSequence;
        g2.setColor(Color.BLACK);
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case Forward:
                    finalVector = goForward(startingVector, forwardPixels);
                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
                    startingVector = finalVector;
                    break;
                case LeftTurn:
                    startingVector.direction = turnLeft(startingVector.direction);
//                    finalVector = goForward(startingVector, forwardPixels);
//                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
//                    startingVector.xCoordinate = finalVector.xCoordinate;
//                    startingVector.yCoordinate = finalVector.yCoordinate;
//                    startingVector.direction = finalVector.direction;
                    break;
                case RightTurn:
                    startingVector.direction = turnRight(startingVector.direction);
//                    finalVector = goForward(startingVector, forwardPixels);
//                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
//                    startingVector.xCoordinate = finalVector.xCoordinate;
//                    startingVector.yCoordinate = finalVector.yCoordinate;
//                    startingVector.direction = finalVector.direction;
                    break;
                default:
                    break;
            }
        }

        g2.setColor(Color.BLACK);

    }

    private DirectionVector goForward(DirectionVector startingVector, int forwardPixels) {

        if (startingVector.direction == Direction.NORTH) {
            return new DirectionVector(startingVector.xCoordinate, startingVector.yCoordinate - forwardPixels, startingVector.direction);
        }
        if (startingVector.direction == Direction.SOUTH) {
            return new DirectionVector(startingVector.xCoordinate, startingVector.yCoordinate + forwardPixels, startingVector.direction);
        }
        if (startingVector.direction == Direction.EAST) {
            return new DirectionVector(startingVector.xCoordinate + forwardPixels, startingVector.yCoordinate, startingVector.direction);
        }
        if (startingVector.direction == Direction.WEST) {
            return new DirectionVector(startingVector.xCoordinate - forwardPixels, startingVector.yCoordinate, startingVector.direction);
        }
        return startingVector;

    }

    private Direction turnRight(Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Direction.EAST;
            case SOUTH:
                return Direction.WEST;
            case EAST:
                return Direction.SOUTH;
            case WEST:
                return Direction.NORTH;
            default:
                return initialDirection;
        }
    }

    private Direction turnLeft(Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Direction.WEST;
            case WEST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.EAST;
            case EAST:
                return Direction.NORTH;
            default:
                return initialDirection;
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }


}
