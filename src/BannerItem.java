import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public abstract class BannerItem extends JPanel {
    private Optional<Font> customFont = Optional.empty();

    public abstract String getFirstLine();
    public abstract String getSecondLine();

    public abstract void setSplitIndex(int index);

    public Optional<Font> getCustomFont() {
        return customFont;
    }

    public void setCustomFont(Font customFont) {
        this.customFont = customFont == null ? Optional.empty() : Optional.of(customFont);
    }
}
