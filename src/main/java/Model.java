import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;


class Model {
    final NumberModel xOffset, baseline1, baseline2, width, height, xPos, yPos, updateDelay, changeDelay;
    final CircularQueue<BannerItem> items;
    final ModelItem<Font> font;
    final ModelItem<Color> foregroundColor, backgroundColor;
    final BannerItemFactory[] bannerItemFactories;
    final ModelItem<Boolean> showGuides;

    Model() {
        showGuides = new ModelItem<>(false);

        {//Initialise xOffset
            this.xOffset = new NumberModel(5, 1);
        }

        {//Initialise baseline1
            this.baseline1 = new NumberModel(36, 1);
        }

        {//Initialise baseline2
            this.baseline2 = new NumberModel(72, 1);
        }

        {//Initialise width
            this.width = new NumberModel(385, 1);
            this.width.setMin(0);
        }

        {//Initialise height
            this.height = new NumberModel(81, 1);
            this.height.setMin(0);
        }

        {//Initialise pos
            this.xPos = new NumberModel(0, 1);
            this.yPos = new NumberModel(650, 1);
        }

        this.updateDelay = new NumberModel(10, 1);
        this.updateDelay.setMin(0);

        this.changeDelay = new NumberModel(10000, 1);
        this.updateDelay.setMin(0);

        this.items = new CircularQueue<>();
        BannerItem i = new ConstantBannerItem("HEJ SQUVALP!");
        i.setSplitIndex(4);
        this.items.addElement(i);
        i = new CountDownBannerItem("BYGGSTART", "TID TILL BYGG: ", "s!", secondsUntil(LocalDateTime.of(2018, 5, 11, 14, 8)), 1000);
        this.items.addElement(i);

        Font f = new JLabel().getFont();
        f = f.deriveFont(36.0f);
        this.font = new ModelItem<>(f);
        this.foregroundColor = new ModelItem<>(Color.CYAN);
        this.backgroundColor = new ModelItem<>(Color.BLACK);

        bannerItemFactories = new BannerItemFactory[]{
                new BannerItemFactory(() -> new ConstantBannerItem(""), "Constant"),
                new BannerItemFactory(() -> new CounterBannerItem("", "", 0, 1000), "Counter"),
                new BannerItemFactory(() -> new CountDownBannerItem("DONE", "", "", 100, 1000), "Count down")
        };
    }

    private int secondsUntil(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        long diffInSeconds = Duration.between(now, time).getSeconds();

        if(diffInSeconds > Integer.MAX_VALUE) {
            System.err.println("Will cast to big long to int when calculating duration between dates");
        }

        return (int) diffInSeconds;
    }
}
