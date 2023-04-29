import java.util.function.Supplier;

public class BannerItemFactory {
    private Supplier<BannerItem> supplier;
    private String name;

    public BannerItemFactory(Supplier<BannerItem> supplier, String name) {
        this.supplier = supplier;
        this.name = name;
    }

    public BannerItem getNewItem() {
        return supplier.get();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
