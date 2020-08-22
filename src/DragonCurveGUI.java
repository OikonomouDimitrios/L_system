import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class DragonCurveGUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private JPanel mainDragonPanel;
    private JLabel iterations;
    private JLabel pixels;
    private JSlider iterationSlider;
    private JSlider pixelSlider;
    private JLabel iterationValue;
    private JLabel pixelValue;
    private JButton startButton;
    private JLabel ImgLabel;
    private static JFrame dragonFrame;
    private static BufferedImagePanel dc;

    public DragonCurveGUI() {
        super("DragonCurveGUI");

        mainDragonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        iterations = new JLabel("iterations");
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainDragonPanel.add(iterations, constraints);

        iterationSlider = new JSlider();
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        iterationSlider.setMinimum(1);
        iterationSlider.setMaximum(25);
        mainDragonPanel.add(iterationSlider, constraints);


        iterationValue = new JLabel();
        constraints.gridx = 3;
        mainDragonPanel.add(iterationValue, constraints);


        pixels = new JLabel("pixels");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainDragonPanel.add(pixels, constraints);

        pixelSlider = new JSlider();
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        pixelSlider.setMinimum(1);
        pixelSlider.setMaximum(10);
        mainDragonPanel.add(pixelSlider, constraints);


        pixelValue = new JLabel();
        constraints.gridx = 3;
        mainDragonPanel.add(pixelValue, constraints);

        startButton = new JButton("start");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        mainDragonPanel.add(startButton, constraints);


        ImgLabel = new JLabel("dragon curve will appear here");
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        var dim = new Dimension(900, 700);
        ImgLabel.setMinimumSize(dim);
        ImgLabel.setPreferredSize(dim);
        ImgLabel.setMaximumSize(dim);
        ImgLabel.setBorder(border);

        constraints.anchor = GridBagConstraints.PAGE_END;
        mainDragonPanel.add(ImgLabel, constraints);

        // set border for the panel
        mainDragonPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Dragon Curve"));

        // add the panel to this frame
        add(mainDragonPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iterationSlider.setValue(5);
        pixelSlider.setValue(1);
        String pixelSliderValue = String.valueOf(pixelSlider.getValue());
        pixelValue.setText(pixelSliderValue);

        String iterationSliderValue = String.valueOf(iterationSlider.getValue());
        iterationValue.setText(iterationSliderValue);
        pixelSlider.addChangeListener(changeEvent -> {
            String pixelSliderValue1 = String.valueOf(pixelSlider.getValue());
            pixelValue.setText(pixelSliderValue1);
        });
        iterationSlider.addChangeListener(changeEvent -> {
            String iterationSliderValue1 = String.valueOf(iterationSlider.getValue());
            iterationValue.setText(iterationSliderValue1);
        });
        startButton.addActionListener(actionEvent -> {
            iterationSlider.setEnabled(false);
            pixelSlider.setEnabled(false);
            startButton.setEnabled(false);
            startButton.setText("Running...");

            try {
                CompletableFuture.supplyAsync(() -> calculateGeneration()).thenAccept(s -> createDragonCurvePanel(s))
                    .thenRun(() -> enableControls());
                ;
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        setLocationRelativeTo(null);
    }

    public void enableControls() {
        iterationSlider.setEnabled(true);
        pixelSlider.setEnabled(true);
        startButton.setEnabled(true);
        startButton.setText("Start");
    }


    public static void main(String[] args) {

        dragonFrame = new DragonCurveGUI();
        dragonFrame.setLayout(new GridLayout());
        dragonFrame.setVisible(true);
        dragonFrame.setSize(WIDTH, HEIGHT);


    }

    public String calculateGeneration() {
        String startingSequence = "FX";
        String nextSequence = "";
        String[] tableofRules = {"X+YF+", "-FX-Y"};
        for (int j = 0; j < iterationSlider.getValue(); j++) {
            for (int i = 0; i < startingSequence.length(); i++) {
                char currentSequence = startingSequence.charAt(i);
                if (currentSequence == 'X') {
                    nextSequence += tableofRules[0];
                } else if (currentSequence == 'Y') {
                    nextSequence += tableofRules[1];
                } else {
                    nextSequence += currentSequence;
                }

            }
            startingSequence = nextSequence;
        }

        return startingSequence;
    }

    public void createDragonCurvePanel(String finalSequence) {
        dc = new BufferedImagePanel();
        dc.playGame(pixelSlider.getValue(), finalSequence);
        var ico = new ImageIcon(dc.getImage());
        ImgLabel.setIcon(ico);
        dragonFrame.revalidate();
        dragonFrame.repaint();
    }

}
