package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.BarycenterAlgorithm;
import model.FruchtermanSpringAlgorithm;
import model.RandomAlgorithm;
import model.SpringAlgorithm;

public class AlgorithmOptionsPanel extends JPanel {

    protected DrawingPanel drawingPanel;

    public AlgorithmOptionsPanel(DrawingPanel drawingPanel) {
        super(true);
        this.drawingPanel = drawingPanel;
        this.setLayout(new CardLayout());
        this.add(new RandomAlgoPanel(), "Random");
        this.add(new BarycenterAlgoPanel(), "Tutte's Barycenter");
        this.add(new SpringAlgoPanel(), "Spring Embedder");
        this.add(new FruchtermanSpringAlgorithmPanel(), "Fruchterman-Reingold");
    }

    public JPanel getPanelByName(String panelName) {
        switch(panelName) {
            case "Random":
                return (RandomAlgoPanel) this.getComponent(0);
            case "Tutte's Barycenter":
                return (BarycenterAlgoPanel) this.getComponent(1);
            case "Spring Embedder":
                return (SpringAlgoPanel) this.getComponent(2);
            case "Fruchterman-Reingold":
                return (FruchtermanSpringAlgorithmPanel) this.getComponent(3);
            default:
                return null;
        }
    }

    public abstract class AbstractAlgoPanel extends JPanel {

        public AbstractAlgoPanel(String title) {
            super(true);
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        public abstract void runAlgorithm();

    }

    private class RandomAlgoPanel extends AbstractAlgoPanel {

        public RandomAlgoPanel() {
            super("Random Algorithm Options");
        }

        @Override
        public void runAlgorithm() {
            drawingPanel.setVisualizationAlgorithm(new RandomAlgorithm());
        }
    }

    private class BarycenterAlgoPanel extends AbstractAlgoPanel {

        protected JSpinner numOfFixedNodesSpinner;

        public BarycenterAlgoPanel() {
            super("Barycenter Algorithm Options");
            this.add(numOfFixedNodesPanel());
        }

        private JPanel numOfFixedNodesPanel() {
            JPanel numOfFixedNodesPanel = new JPanel(true);
            //numOfFixedNodesPanel.setBackground(Color.YELLOW);
            numOfFixedNodesPanel.setLayout(new BoxLayout(numOfFixedNodesPanel, BoxLayout.X_AXIS));
            numOfFixedNodesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel numOfFixedNodesLabel = new JLabel("# fixed nodes: ");
            numOfFixedNodesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            numOfFixedNodesLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            numOfFixedNodesPanel.add(numOfFixedNodesLabel);

            numOfFixedNodesSpinner = new JSpinner();
            //Min value = 0, Max value = 50, Initial value = 3
            numOfFixedNodesSpinner.setModel(new SpinnerNumberModel(3, 0, 50, 1));
            numOfFixedNodesSpinner.setValue(3);
            numOfFixedNodesSpinner.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            numOfFixedNodesSpinner.setMaximumSize(new Dimension(100, 20));
            numOfFixedNodesPanel.add(numOfFixedNodesSpinner);

            return numOfFixedNodesPanel;
        }

        @Override
        public void runAlgorithm() {
            int numOfFixedNodes = (int) numOfFixedNodesSpinner.getValue();
            drawingPanel.setVisualizationAlgorithm(new BarycenterAlgorithm(numOfFixedNodes));
        }
    }

    private class SpringAlgoPanel extends AbstractAlgoPanel {

        protected JSlider attractionConstantSlider;
        protected JSlider repulsionConstantSlider;

        public SpringAlgoPanel() {
            super("Spring Algorithm Options");
            this.add(attractionConstantPanel());
            this.add(repulsionConstantPanel());
        }

        private JPanel attractionConstantPanel() {
            JPanel attractionConstantPanel = new JPanel(true);
            //attractionConstantPanel.setBackground(Color.YELLOW);
            attractionConstantPanel.setLayout(new BoxLayout(attractionConstantPanel, BoxLayout.X_AXIS));
            attractionConstantPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel attractionConstantLabel = new JLabel("Attraction: ");
            attractionConstantLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            attractionConstantLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            attractionConstantPanel.add(attractionConstantLabel);

            attractionConstantSlider = new JSlider(100, 1000, 600);
            attractionConstantSlider.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            attractionConstantSlider.setMaximumSize(new Dimension(96, 40));
            //attractionConstantSlider.setPaintLabels(true);
            attractionConstantSlider.setPaintTicks(true);
            attractionConstantSlider.setMajorTickSpacing(450);
            attractionConstantSlider.setMinorTickSpacing(50);
            attractionConstantPanel.add(attractionConstantSlider);

            return attractionConstantPanel;
        }

        private JPanel repulsionConstantPanel() {
            JPanel repulsionConstantPanel = new JPanel(true);
            //repulsionConstantPanel.setBackground(Color.YELLOW);
            repulsionConstantPanel.setLayout(new BoxLayout(repulsionConstantPanel, BoxLayout.X_AXIS));
            repulsionConstantPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

            JLabel repulsionConstantLabel = new JLabel("Repulsion: ");
            repulsionConstantLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            repulsionConstantLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            repulsionConstantPanel.add(repulsionConstantLabel);

            repulsionConstantSlider = new JSlider(100, 1000, 600);
            repulsionConstantSlider.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            repulsionConstantSlider.setMaximumSize(new Dimension(120, 50));
            repulsionConstantSlider.setPaintLabels(true);
            repulsionConstantSlider.setPaintTicks(true);
            repulsionConstantSlider.setMajorTickSpacing(450);
            repulsionConstantSlider.setMinorTickSpacing(50);
            repulsionConstantPanel.add(repulsionConstantSlider);

            return repulsionConstantPanel;
        }

        @Override
        public void runAlgorithm() {
            double attractionConstant = attractionConstantSlider.getValue();
            double repulsionConstant = repulsionConstantSlider.getValue();

            drawingPanel.setVisualizationAlgorithm(new SpringAlgorithm(attractionConstant, repulsionConstant));
        }
    }

    private class FruchtermanSpringAlgorithmPanel extends AbstractAlgoPanel {

        protected JSlider temperatureSlider;

        public FruchtermanSpringAlgorithmPanel() {
            super("Fruchterman-Reingold Algorithm Options");
            this.add(temperaturePanel());
        }

        private JPanel temperaturePanel() {
            JPanel temperaturePanel = new JPanel(true);
            //temperaturePanel.setBackground(Color.YELLOW);
            temperaturePanel.setLayout(new BoxLayout(temperaturePanel, BoxLayout.X_AXIS));
            temperaturePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel temperatureLabel = new JLabel("Temperature: ");
            temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            temperatureLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            temperaturePanel.add(temperatureLabel);

            temperatureSlider = new JSlider(100, 300, 200);
            temperatureSlider.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            temperatureSlider.setMaximumSize(new Dimension(120, 50));
            temperatureSlider.setPaintLabels(true);
            temperatureSlider.setPaintTicks(true);
            temperatureSlider.setMajorTickSpacing(100);
            temperatureSlider.setMinorTickSpacing(50);
            temperaturePanel.add(temperatureSlider);

            return temperaturePanel;
        }

        @Override
        public void runAlgorithm() {
            double temperature = temperatureSlider.getValue();
            drawingPanel.setVisualizationAlgorithm(new FruchtermanSpringAlgorithm(temperature));
        }

    }



}
