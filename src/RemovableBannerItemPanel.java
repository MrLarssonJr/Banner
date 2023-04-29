import org.jfree.ui.FontChooserDialog;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class RemovableBannerItemPanel extends JPanel {

    private final JLabel currentCustomFont;
    private BannerItem item;

    public RemovableBannerItemPanel(BannerItem item, Model model) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.item = item;

        this.add(item);
        this.add(new JSeparator(JSeparator.VERTICAL));

        JButton remove = new JButton("Remove");
        remove.addActionListener(l -> {
            int selectedOption = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this banner item?",
                    "Confirm removal",
                    JOptionPane.YES_NO_OPTION);
            if(selectedOption == JOptionPane.YES_OPTION) {
                model.items.removeElement(item);
            }
        });

        JPanel customFontPanel = new JPanel();
        customFontPanel.setLayout(new BoxLayout(customFontPanel, BoxLayout.Y_AXIS));
        currentCustomFont = new JLabel();
        updateCurrentCustomFontText();
        JButton setCustomFont = new JButton("Set font");
        setCustomFont.addActionListener(l -> {
            FontChooserDialog fcd = new FontChooserDialog(
                    (Frame) null, "Choose Custom Font",
                    true, item.getCustomFont().orElse(model.font.getValue()));
            fcd.pack();
            fcd.setVisible(true);

            item.setCustomFont(fcd.getSelectedFont());
            updateCurrentCustomFontText();
        });
        JButton clearCustomFont = new JButton("Clear font");
        clearCustomFont.addActionListener(l -> {
            item.setCustomFont(null);
            updateCurrentCustomFontText();
        });
        customFontPanel.add(currentCustomFont);
        customFontPanel.add(setCustomFont);
        customFontPanel.add(clearCustomFont);

        this.add(remove);
        this.add(customFontPanel);

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    private void updateCurrentCustomFontText() {
        currentCustomFont.setText(item.getCustomFont().map(Font::getName).orElse("N/A"));
    }
}
