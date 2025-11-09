package wootrevived.ifwootaddon;

import wootrevived.api.IWootPlugin;
import wootrevived.api.WootPlugin;
import wootrevived.api.registrations.WootUpgradeItemRegistration;
import wootrevived.ifwootaddon.upgrades.LaserDrill;
import wootrevived.ifwootaddon.upgrades.MobCrusher;
import wootrevived.ifwootaddon.upgrades.MobSlaughterFactory;

@WootPlugin
public class IFWootPlugin implements IWootPlugin {
    @Override
    public void registerUpgradeItems(WootUpgradeItemRegistration registration){
        LaserDrill.register(registration);
        MobCrusher.register(registration);
        MobSlaughterFactory.register(registration);
    }
}
