import org.jfree.ui.FontChooserDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class ControlFrame extends JFrame {
    ControlFrame(Model model) {
        JLabel widthLabel, heightLabel, xPosLabel, yPosLabel, baseline1Label, baseline2Label, changeDelayLabel, xOffsetLabel, updateDelayLabel;
        JSpinner widthSpinner, heightSpinner, xPosSpinner, yPosSpinner, baseline1Spinner, baseline2Spinner, changeDelaySpinner, xOffsetSpinner, updateDelaySpinner;
        JPanel widthPanel, heightPanel, xPosPanel, yPosPanel, baseline1Panel, baseline2Panel, changeDelayPanel, xOffsetPanel, updateDelayPanel, basicControlPanel, bannerItemControlPanel, rootPanel, addBannerItemPanel;
        JButton fontButton, foregroundColorButton, backgroundColorButton, addBannerItemButton;
        JToggleButton lockButton, guideButton;
        JScrollPane bannerItemControlPane;
        JComboBox<BannerItemFactory> addBannerItemComboBox;

        widthLabel = new JLabel("Width:");
        widthLabel.setHorizontalAlignment(JLabel.RIGHT);
        widthSpinner = new JSpinner(model.width);
        widthPanel = new JPanel();
        widthPanel.setLayout(new GridLayout(1,2));
        widthPanel.add(widthLabel);
        widthPanel.add(widthSpinner);

        heightLabel = new JLabel("Height:");
        heightLabel.setHorizontalAlignment(JLabel.RIGHT);
        heightSpinner = new JSpinner(model.height);
        heightPanel = new JPanel();
        heightPanel.setLayout(new GridLayout(1, 2));
        heightPanel.add(heightLabel);
        heightPanel.add(heightSpinner);

        xPosLabel = new JLabel("Position (x):");
        xPosLabel.setHorizontalAlignment(JLabel.RIGHT);
        xPosSpinner = new JSpinner(model.xPos);
        xPosPanel = new JPanel();
        xPosPanel.setLayout(new GridLayout(1, 2));
        xPosPanel.add(xPosLabel);
        xPosPanel.add(xPosSpinner);

        yPosLabel = new JLabel("Position (y):");
        yPosLabel.setHorizontalAlignment(JLabel.RIGHT);
        yPosSpinner = new JSpinner(model.yPos);
        yPosPanel = new JPanel();
        yPosPanel.setLayout(new GridLayout(1, 2));
        yPosPanel.add(yPosLabel);
        yPosPanel.add(yPosSpinner);

        baseline1Label = new JLabel("Baseline 1:");
        baseline1Label.setHorizontalAlignment(JLabel.RIGHT);
        baseline1Spinner = new JSpinner(model.baseline1);
        baseline1Panel = new JPanel();
        baseline1Panel.setLayout(new GridLayout(1,2));
        baseline1Panel.add(baseline1Label);
        baseline1Panel.add(baseline1Spinner);

        baseline2Label = new JLabel("Baseline 2:");
        baseline2Label.setHorizontalAlignment(JLabel.RIGHT);
        baseline2Spinner = new JSpinner(model.baseline2);
        baseline2Panel = new JPanel();
        baseline2Panel.setLayout(new GridLayout(1, 2));
        baseline2Panel.add(baseline2Label);
        baseline2Panel.add(baseline2Spinner);

        changeDelayLabel = new JLabel("Change delay:");
        changeDelayLabel.setHorizontalAlignment(JLabel.RIGHT);
        changeDelaySpinner = new JSpinner(model.changeDelay);
        changeDelayPanel = new JPanel();
        changeDelayPanel.setLayout(new GridLayout(1,2));
        changeDelayPanel.add(changeDelayLabel);
        changeDelayPanel.add(changeDelaySpinner);

        xOffsetLabel = new JLabel("X offset:");
        xOffsetLabel.setHorizontalAlignment(JLabel.RIGHT);
        xOffsetSpinner = new JSpinner(model.xOffset);
        xOffsetPanel = new JPanel();
        xOffsetPanel.setLayout(new GridLayout(1,2));
        xOffsetPanel.add(xOffsetLabel);
        xOffsetPanel.add(xOffsetSpinner);

        updateDelayLabel = new JLabel("Update delay");
        updateDelayLabel.setHorizontalAlignment(JLabel.RIGHT);
        updateDelaySpinner = new JSpinner(model.updateDelay);
        updateDelayPanel = new JPanel();
        updateDelayPanel.setLayout(new GridLayout(1, 2));
        updateDelayPanel.add(updateDelayLabel);
        updateDelayPanel.add(updateDelaySpinner);

        fontButton = new JButton("Change font");
        fontButton.addActionListener(l -> {
            FontChooserDialog fcd = new FontChooserDialog(
                    this, "Choose Banner Font",
                    true, model.font.getValue());
            fcd.pack();
            fcd.setVisible(true);
            model.font.setValue(fcd.getSelectedFont());
        });

        foregroundColorButton = new JButton("Change fg-color:");
        foregroundColorButton.setToolTipText("Change foreground color");
        foregroundColorButton.addActionListener(l -> {
            Color oldColor = model.foregroundColor.getValue();
            Color newColor = JColorChooser.showDialog(this, "Choose new Foreground Color", oldColor);

            if(newColor != null) {
                model.foregroundColor.setValue(newColor);
            }
        });

        backgroundColorButton = new JButton("Change bg-color:");
        backgroundColorButton.setToolTipText("Change background color");
        backgroundColorButton.addActionListener(l -> {
            Color oldColor = model.backgroundColor.getValue();
            Color newColor = JColorChooser.showDialog(this, "Choose new Background Color", oldColor);

            if(newColor != null) {
                model.backgroundColor.setValue(newColor);
            }
        });

        guideButton = new JToggleButton("Press to hide guides", true);
        guideButton.addActionListener(l -> {
            if(guideButton.isSelected()) {
                guideButton.setText("Press to hide guides");
                model.showGuides.setValue(true);
            } else {
                guideButton.setText("Press to show guides");
                model.showGuides.setValue(false);
            }
        });

        lockButton = new JToggleButton("Press to Lock");
        lockButton.addActionListener(l -> {
            JComponent[] spinners = new JComponent[] {fontButton, widthSpinner, heightSpinner, xPosSpinner, yPosSpinner, baseline1Spinner, changeDelaySpinner};
            if(lockButton.isSelected()) {
                lockButton.setText("Press to Unlock");
                for(JComponent spinner : spinners) {
                    spinner.setEnabled(false);
                }
            } else {
                int selectedOption = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to unlock the controls?",
                        "Confirm unlock",
                        JOptionPane.OK_CANCEL_OPTION);

                if(selectedOption == JOptionPane.OK_OPTION) {   //User changed their mind
                    lockButton.setText("Press to Lock");
                    for(JComponent spinner : spinners) {
                        spinner.setEnabled(true);
                    }
                }
            }
        });

        basicControlPanel = new JPanel();
        basicControlPanel.setBorder(new EmptyBorder(5,5,5,5));
        basicControlPanel.setLayout(new GridLayout(2, 7));

        basicControlPanel.add(xPosPanel);
        basicControlPanel.add(widthPanel);
        basicControlPanel.add(changeDelayPanel);
        basicControlPanel.add(baseline1Panel);
        basicControlPanel.add(xOffsetPanel);
        basicControlPanel.add(foregroundColorButton);
        basicControlPanel.add(fontButton);

        basicControlPanel.add(yPosPanel);
        basicControlPanel.add(heightPanel);
        basicControlPanel.add(updateDelayPanel);
        basicControlPanel.add(baseline2Panel);
        basicControlPanel.add(guideButton);
        basicControlPanel.add(backgroundColorButton);
        basicControlPanel.add(lockButton);

        bannerItemControlPanel = new JPanel();
        bannerItemControlPanel.setLayout(new BoxLayout(bannerItemControlPanel, BoxLayout.Y_AXIS));
        for(BannerItem item : model.items.getElements()) {
            bannerItemControlPanel.add(new RemovableBannerItemPanel(item, model));
        }
        model.items.addChangeListener(l -> {
            bannerItemControlPanel.removeAll();
            for(BannerItem item : model.items.getElements()) {
                bannerItemControlPanel.add(new RemovableBannerItemPanel(item, model));
            }
        });

        bannerItemControlPane = new JScrollPane(bannerItemControlPanel);

        addBannerItemPanel = new JPanel();
        addBannerItemPanel.setLayout(new BoxLayout(addBannerItemPanel, BoxLayout.X_AXIS));
        addBannerItemComboBox = new JComboBox<>(model.bannerItemFactories);
        addBannerItemPanel.add(addBannerItemComboBox);
        addBannerItemButton = new JButton("Add");
        addBannerItemButton.addActionListener(l -> model.items.addElement(((BannerItemFactory) addBannerItemComboBox.getSelectedItem()).getNewItem()));
        addBannerItemPanel.add(addBannerItemButton);

        rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.add(basicControlPanel);
        rootPanel.add(addBannerItemPanel);
        rootPanel.add(bannerItemControlPane);

        this.add(rootPanel);
        this.setTitle("Banner Control Panel");
        this.pack();
        this.setVisible(true);
    }
}
