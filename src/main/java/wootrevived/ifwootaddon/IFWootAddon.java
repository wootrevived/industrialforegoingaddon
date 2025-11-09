package wootrevived.ifwootaddon;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import wootrevived.ifwootaddon.registries.IFItemsRegistry;

import java.util.Objects;

@Mod(IFWootAddon.MOD_ID)
public class IFWootAddon
{
    public static final String MOD_ID = "ifwootaddon";

    public IFWootAddon()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IFItemsRegistry.register(bus);
    }

    public static @NotNull ResourceLocation location(String path) {
        return Objects.requireNonNull(ResourceLocation.tryBuild(IFWootAddon.MOD_ID, path));
    }
}
