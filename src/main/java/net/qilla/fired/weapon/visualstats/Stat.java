package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.tools.NumUtil;
import org.jetbrains.annotations.NotNull;

public abstract class Stat<T extends Number> {

    private final T value;
    private final String display;

    public Stat(@NotNull T value, @NotNull String display) {
        this.value = value;
        this.display = display;
    }
    public abstract @NotNull String getID();

    public @NotNull T getValue() {
        return value;
    }

    public @NotNull String getDisplay() {
        return this.display;
    }

    public @NotNull Component getFormatted() {
        String str = this.display;

        str = str.replace("%value%", String.valueOf(this.value));
        return MiniMessage.miniMessage().deserialize(str);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Stat<?> stat) {
            return this.getID().equals(stat.getID());
        } else return false;
    }

    public static class Damage extends Stat<Float> {

        private static final String ID = "damage";
        private static final String DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold>";
        private static final String BONUS_DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Damage: <gold>%value%</gold> <green>(+%bonus%)</green>";

        private final float bonus;

        private Damage(float value, float bonus) {
            super(value, bonus == 0 ? DISPLAY : BONUS_DISPLAY);
            this.bonus = bonus;
        }

        public static @NotNull Stat.Damage of(float value, float bonus) {
            return new Damage(value, bonus);
        }

        public static @NotNull Stat.Damage of(float value) {
            return new Damage(value, 0);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            if(this.bonus == 0) return MiniMessage.miniMessage().deserialize(super.display.replace("%value%", NumUtil.decimalTruncation(super.value, 1)));

            String str = super.display;

            str = str.replace("%value%", NumUtil.decimalTruncation(super.value + this.bonus, 1));
            str = str.replace("%bonus%", NumUtil.decimalTruncation(this.bonus, 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class BonusDamage extends Stat<Float> {

        private static final String ID = "damage";
        private static final String DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Bonus Damage: <gold>%value%</gold>";
        private static final String PELT_DISPLAY = "<!italic><white><gold>\uD83D\uDD25</gold> Bonus Damage: <gold>%value%</gold> x (<dark_aqua>☄</dark_aqua> %peltCount%)";

        private final int peltCount;

        private BonusDamage(float value, int peltCount) {
            super(value, peltCount == 0 ? DISPLAY : PELT_DISPLAY);
            this.peltCount = peltCount;
        }

        public static @NotNull BonusDamage of(float value, int peltCount) {
            return new BonusDamage(value, peltCount);
        }

        public static @NotNull BonusDamage of(float value) {
            return new BonusDamage(value, 0);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }

        @Override
        public @NotNull Component getFormatted() {
            if(this.peltCount == 0) return MiniMessage.miniMessage().deserialize(super.display.replace("%value%", NumUtil.decimalTruncation(super.value, 1)));

            String str = super.display;

            str = str.replace("%value%", NumUtil.decimalTruncation(super.value, 1));
            str = str.replace("%peltCount%", String.valueOf(this.peltCount));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class FireRate extends Stat<Integer> {

        private static final String ID = "firerate";
        private static final String DISPLAY_SINGLE = "<!italic><white><aqua>⏳</aqua> Fire Rate: <aqua>%value%</aqua>";
        private static final String DISPLAY_MULTI = "<!italic><white><aqua>⏳</aqua> Fire Rate: <aqua>%value%</aqua> x (<dark_green>❄</dark_green> %amount%)";

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
            if(this.bullets == 1 ) return MiniMessage.miniMessage().deserialize(super.display.replace("%value%", NumUtil.decimalTruncation(1000f / (float) super.value, 1)));

            String str = super.display;

            str = str.replace("%value%", NumUtil.decimalTruncation(1000f / (float) super.value, 1));
            str = str.replace("%amount%", NumUtil.decimalTruncation(this.bullets, 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class BulletSpread extends Stat<Double> {

        private static final String ID = "bulletspread";
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
            String str = super.display;

            str = str.replace("%value%", NumUtil.decimalTruncation(super.value, 1));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }

    public static class Range extends Stat<Integer> {

        private static final String ID = "range";
        private static final String DISPLAY = "<!italic><white><yellow>☀</yellow> Range: <yellow>%value%</yellow>";

        private Range(int value) {
            super(value, DISPLAY);
        }

        public static @NotNull Range of(int value) {
            return new Range(value);
        }

        @Override
        public @NotNull String getID() {
            return ID;
        }
    }

    public static class Bleed extends Stat<Float> {

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
            String str = super.display;

            str = str.replace("%value%", NumUtil.decimalTruncation(super.value, 2));

            return MiniMessage.miniMessage().deserialize(str);
        }
    }
}
