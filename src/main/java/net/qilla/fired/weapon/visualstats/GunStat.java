package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.tools.NumUtil;
import org.jetbrains.annotations.NotNull;

public abstract class GunStat<T extends Number> extends StatDisplay<T> {

    public GunStat(@NotNull T value, @NotNull String display) {
        super(value, display);
    }

    public static class Damage extends StatDisplay<Float> {

        private static final String ID = "damage";
        private static final String DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold>";
        private static final String BONUS_DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold> <green>(+%bonus%)</green>";

        private final float bonus;

        private Damage(float value, float bonus) {
            super(value, bonus == 0 ? DISPLAY : BONUS_DISPLAY);
            this.bonus = bonus;
        }

        public static @NotNull Damage of(float value, float bonus) {
            return new Damage(value, bonus);
        }

        public static @NotNull Damage of(float value) {
            return new Damage(value, 0);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            if(this.bonus == 0) return MiniMessage.miniMessage().deserialize(super.getDisplay().replace("%value%", NumUtil.decimalTruncation(super.getValue(), 1)));

            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue() + this.bonus, 1));
            str = str.replace("%bonus%", NumUtil.decimalTruncation(this.bonus, 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class FireRate extends StatDisplay<Integer> {

        private static final String ID = "firerate";
        private static final String DISPLAY_SINGLE = "<!italic><white><aqua>☀</aqua> Fire Rate: <aqua>%value%</aqua>";
        private static final String DISPLAY_MULTI = "<!italic><white><aqua>☀</aqua> Fire Rate: <aqua>%value%</aqua> x (<dark_green>❄</dark_green> %amount%)";

        private final int bullets;

        private FireRate(int cooldown, int bullets) {
            super(cooldown, bullets == 1 ? DISPLAY_SINGLE : DISPLAY_MULTI);
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
            if(this.bullets == 1 ) return MiniMessage.miniMessage().deserialize(super.getDisplay().replace("%value%", NumUtil.decimalTruncation(1000f / (float) super.getValue(), 1)));

            String str = super.getDisplay();

            str = str.replace("%value%", NumUtil.decimalTruncation(1000f / (float) super.getValue(), 1));
            str = str.replace("%amount%", NumUtil.decimalTruncation(this.bullets, 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class Accuracy extends StatDisplay<Double> {

        private static final String ID = "accuracy";
        private static final String DISPLAY = "<!italic><white><yellow>\uD83C\uDFF9</yellow> Accuracy: <yellow>%value%</yellow>";

        private final double multiplier;

        private Accuracy(double value, double multiplier) {
            super(value, DISPLAY);
            this.multiplier = multiplier;
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

            str = str.replace("%value%", NumUtil.decimalTruncation(super.getValue() * this.multiplier, 1));

            if(this.multiplier != 1.0) {
                int percent = (int) (this.multiplier * 100) - 100;
                str = percent < 0 ? str.concat(" (<green>%multiplier%%</green>)") : str.concat(" (<red>+%multiplier%%</red>)");

                str = str.replace("%multiplier%", String.valueOf(percent));
            }
            return MiniMessage.miniMessage().deserialize(str);
        }
    }
}
