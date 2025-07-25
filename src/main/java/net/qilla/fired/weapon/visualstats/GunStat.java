package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.tools.NumUtil;
import org.jetbrains.annotations.NotNull;

public abstract class GunStat<T extends Number> extends StatDisplay<T> {

    public GunStat(@NotNull T value, @NotNull String display) {
        super(value, display);
    }

    public static class DamageMod extends StatDisplay<Float> {

        private static final String ID = "damagemod";
        private static final String DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold>";

        private final double modifier;

        private DamageMod(float value, double modifier) {
            super(value, DISPLAY);
            this.modifier = modifier;
        }

        public static @NotNull GunStat.DamageMod of(float value, double modifier) {
            return new DamageMod(value, modifier);
        }

        public static @NotNull GunStat.DamageMod of(float value) {
            return new DamageMod(value, 0);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue() * this.modifier, 1));

            if(this.modifier != 1.0) {
                int percent = (int) (this.modifier * 100) - 100;
                str = percent < 0 ? str.concat(" (<red>%modifier%%</red>)") : str.concat(" (<green>+%modifier%%</green>)");

                str = str.replace("%modifier%", String.valueOf(percent));
            }
            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class FireRate extends StatDisplay<Integer> {

        private static final String ID = "firerate";
        private static final String DISPLAY_SINGLE = "<!italic><white><aqua>☀</aqua> Fire Rate: <aqua>%value%</aqua>";

        private final int bullets;

        private FireRate(int cooldown, int bullets) {
            super(cooldown, DISPLAY_SINGLE);
            this.bullets = bullets;
        }

        public static @NotNull FireRate of(int cooldown) {
            return new FireRate(cooldown, 1);
        }

        public static @NotNull FireRate of(int cooldown, int bullets) {
            return new FireRate(cooldown, bullets);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(1000f / (float) super.getValue(), 1));

            if(this.bullets != 1) {
                str = str.concat(" x (<dark_green>❄</dark_green> %amount%)");
                str = str.replace("%amount%", String.valueOf(this.bullets));
            }
            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class Accuracy extends StatDisplay<Double> {

        private static final String ID = "accuracy";
        private static final String DISPLAY = "<!italic><white><yellow>\uD83C\uDFF9</yellow> Accuracy: <yellow>%value%</yellow>";

        private final double modifier;

        private Accuracy(double value, double multiplier) {
            super(value, DISPLAY);
            this.modifier = multiplier;
        }

        public static @NotNull GunStat.Accuracy of(double bulletSpread, double multiplier) {
            return new Accuracy(bulletSpread, multiplier);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue() * this.modifier, 1));

            if(this.modifier != 1.0) {
                int percent = (int) (this.modifier * 100) - 100;
                str = percent < 0 ? str.concat(" (<green>%modifier%%</green>)") : str.concat(" (<red>+%modifier%%</red>)");

                str = str.replace("%modifier%", String.valueOf(percent));
            }
            return MiniMessage.miniMessage().deserialize(str);
        }
    }
}
