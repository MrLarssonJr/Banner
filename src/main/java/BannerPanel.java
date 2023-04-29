import javax.swing.*;
import java.awt.*;

public class BannerPanel extends JPanel {
    private static class BannerLabel {
        private String text;
        private double x;

        BannerLabel(String text, int x) {
            this.text = text;
            this.x = x;
        }

        String getText() {
            return text;
        }

        double getX() {
            return (int) x;
        }

        void setX(double x) {this.x = x;}

        void move(double dx) {
            x += dx;
        }
    }

    private Model model;
    private BannerItem currentBannerItem;

    BannerPanel(Model model) {
        JFrame frame = new JFrame();

        this.model = model;

        changeItem();

        model.width.addChangeListener(l -> {
            Dimension size = frame.getSize();
            size.width = (int) (double) model.width.getValue();
            frame.setSize(size);
        });
        model.height.addChangeListener(l -> {
            Dimension size = frame.getSize();
            size.height = (int) (double) model.height.getValue();
            frame.setSize(size);
        });
        model.xPos.addChangeListener(l -> {
            Point loc = frame.getLocation();
            loc.x = (int) (double) model.xPos.getValue();
            frame.setLocation(loc);
        });
        model.yPos.addChangeListener(l -> {
            Point loc = frame.getLocation();
            loc.y = (int) (double) model.yPos.getValue();
            frame.setLocation(loc);
        });

        Timer timer = new Timer((int) (double) model.updateDelay.getValue(), e -> repaint());
        model.updateDelay.addChangeListener(l -> {
            int newDelay = (int) (double) model.updateDelay.getValue();
            timer.setInitialDelay(newDelay);
            timer.setDelay(newDelay);
        });
        timer.start();

        Timer changeItemTimer = new Timer((int) (double) model.changeDelay.getValue(), l -> changeItem());
        model.changeDelay.addChangeListener(l -> {
            int newDelay = (int) (double) model.changeDelay.getValue();
            changeItemTimer.setInitialDelay(newDelay);
            changeItemTimer.setDelay(newDelay);
        });
        changeItemTimer.start();

        this.setPreferredSize(new Dimension((int) (double) model.width.getValue(), (int) (double) model.height.getValue()));
        frame.setLocation((int) (double) model.xPos.getValue(), (int) (double) model.yPos.getValue());

        frame.add(this);

        frame.setUndecorated(true);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private void changeItem() {
        model.items.next().ifPresent(item -> currentBannerItem = item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(currentBannerItem.getCustomFont().orElse(model.font.getValue()));

        //Paint background
        g.setColor(model.backgroundColor.getValue());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //Paint current label
        g.setColor(model.foregroundColor.getValue());
        g.drawString(currentBannerItem.getFirstLine(), (int) (double) model.xOffset.getValue(), (int) (double) model.baseline1.getValue());
        g.drawString(currentBannerItem.getSecondLine(), (int) (double) model.xOffset.getValue(), (int) (double) model.baseline2.getValue());

        //Paint guides
        if(model.showGuides.getValue()) {
            g.setColor(Color.WHITE);
            g.drawRect(0,0, this.getWidth() - 1, this.getHeight() - 1);
        }
    }

    private static int stringWidths(Graphics g, BannerLabel label) {
        return g.getFontMetrics().stringWidth(label.getText());
    }
}
