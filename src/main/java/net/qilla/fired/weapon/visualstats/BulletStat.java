package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.tools.NumUtil;
import org.jetbrains.annotations.NotNull;

public abstract class BulletStat<T extends Number> extends StatDisplay<T> {
    public BulletStat(@NotNull T value, @NotNull String display) {
        super(value, display);
    }

    public static class Damage extends StatDisplay<Float> {

        private static final String ID = "damage";
        private static final String DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold>";

        private final int pelts;

        private Damage(float value, int pelts) {
            super(value, DISPLAY);
            this.pelts = pelts;
        }

        public static @NotNull BulletStat.Damage of(float value, int pelts) {
            return new Damage(value, pelts);
        }

        public static @NotNull BulletStat.Damage of(float value) {
            return new Damage(value, 0);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue(), 1));

            if(this.pelts > 1) {
                str = str.concat(" x (<dark_aqua>☄</dark_aqua> %pelts%)");

                str = str.replace("%pelts%", String.valueOf(pelts));
            }
            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class Range extends StatDisplay<Double> {

        private static final String ID = "range";
        private static final String DISPLAY = "<!italic><white><yellow>☀</yellow> Range: <yellow>%value%</yellow>";

        private Range(double value) {
            super(value, DISPLAY);
        }

        public static @NotNull Range of(double value) {
            return new Range(value);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }
    }

    public static class Bleed extends StatDisplay<Float> {

        private static final String ID = "bleed";
        private static final String DISPLAY = "<!italic><white><red>\uD83C\uDF0A</red> Bleed: <red>%value%</red>";

        private Bleed(float amount) {
            super(amount, DISPLAY);
        }

        public static @NotNull Bleed of(float value) {
            return new Bleed(value);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue(), 2));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class BulletSpread extends StatDisplay<Double> {

        private static final String ID = "bullet_spread";
        private static final String DISPLAY = "<!italic><white><yellow>\uD83C\uDFF9</yellow> Bullet Spread: <yellow>%value%</yellow>";

        private BulletSpread(double value) {
            super(value, DISPLAY);
        }

        public static @NotNull BulletSpread of(double value) {
            return new BulletSpread(value);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue(), 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }
}
