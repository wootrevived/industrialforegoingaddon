package wootrevived.ifwootaddon.upgrades;

import com.buuz135.industrial.module.ModuleCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import wootrevived.api.WootUpgradeItem;
import wootrevived.api.enums.UpgradeNoVariant;
import wootrevived.api.interfaces.WootDropsProperties;
import wootrevived.api.registrations.WootUpgradeItemRegistration;
import wootrevived.ifwootaddon.IFWootAddon;

import java.util.List;

public class MobSlaughterFactory extends WootUpgradeItem<UpgradeNoVariant> {
    public MobSlaughterFactory() {
        super(new Properties(), UpgradeNoVariant.NONE);
    }

    @Override
    public void modifyDrops(@NotNull WootDropsProperties properties, @NotNull CompoundTag itemTag) {
        List<FluidStack> fluids = properties.getFluidDrops();

        LivingEntity entity = properties.getEntity();
        if(entity == null)
            return;

        int health = (int)entity.getHealth();
        fluids.add(new FluidStack(ModuleCore.MEAT.getSourceFluid().get(), entity instanceof Animal ? health : health * 20));
        fluids.add(new FluidStack(ModuleCore.PINK_SLIME.getSourceFluid().get(), entity instanceof Animal ? health * 20 : health));
    }

    /* Upgrade Item registration */

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, IFWootAddon.MOD_ID);

    public static void register(WootUpgradeItemRegistration registration){
        ITEMS.register(registration.getWootEventBus());
        registration.register(MOB_SLAUGHTER_FACTORY_ITEM);
    }

    public static final String MOB_SLAUGHTER_FACTORY_TAG = "mob_slaughter_factory_upgrade";
    public static final RegistryObject<MobSlaughterFactory> MOB_SLAUGHTER_FACTORY_ITEM = ITEMS.register(MOB_SLAUGHTER_FACTORY_TAG, MobSlaughterFactory::new);
}
