package wootrevived.ifwootaddon.datagen.languages;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import wootrevived.ifwootaddon.IFWootAddon;
import wootrevived.ifwootaddon.registries.IFItemsRegistry;
import wootrevived.ifwootaddon.upgrades.LaserDrill;
import wootrevived.ifwootaddon.upgrades.MobCrusher;
import wootrevived.ifwootaddon.upgrades.MobSlaughterFactory;

public class Japanese extends LanguageProvider {
    public Japanese(PackOutput output){
        super(output, IFWootAddon.MOD_ID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        add(MobCrusher.MOB_CRUSHER_ITEM.get(), "Mob粉砕機アップグレード");
        add(MobSlaughterFactory.MOB_SLAUGHTER_FACTORY_ITEM.get(), "Mob屠殺機アップグレード");
        add(LaserDrill.LASER_DRILL_ITEM.get(), "レーザードリルアップグレード");
        add(IFItemsRegistry.STYGIAN_LASER_LENS_ITEM.get(), "スティジアンのレーザーレンズ");
    }
}