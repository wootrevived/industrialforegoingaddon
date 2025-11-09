package wootrevived.ifwootaddon.datagen.languages;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import wootrevived.ifwootaddon.IFWootAddon;
import wootrevived.ifwootaddon.registries.IFItemsRegistry;
import wootrevived.ifwootaddon.upgrades.LaserDrill;
import wootrevived.ifwootaddon.upgrades.MobCrusher;
import wootrevived.ifwootaddon.upgrades.MobSlaughterFactory;

public class SimplifiedChinese extends LanguageProvider {
    public SimplifiedChinese(PackOutput output){
        super(output, IFWootAddon.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(MobCrusher.MOB_CRUSHER_ITEM.get(), "生物粉碎机升级");
        add(MobSlaughterFactory.MOB_SLAUGHTER_FACTORY_ITEM.get(), "生物屠宰厂升级");
        add(LaserDrill.LASER_DRILL_ITEM.get(), "镭射钻升级");
        add(IFItemsRegistry.STYGIAN_LASER_LENS_ITEM.get(), "幽冥镭射聚焦透镜");
    }
}