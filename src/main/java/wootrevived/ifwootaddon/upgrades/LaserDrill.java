package wootrevived.ifwootaddon.upgrades;

import com.buuz135.industrial.item.LaserLensItem;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.LaserDrillFluidRecipe;
import com.hrznstudio.titanium.util.RecipeUtil;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wootrevived.api.WootUpgradeItem;
import wootrevived.api.interfaces.WootDropsProperties;
import wootrevived.api.interfaces.WootUpgradeEnum;
import wootrevived.api.registrations.WootUpgradeItemRegistration;
import wootrevived.ifwootaddon.IFWootAddon;
import wootrevived.ifwootaddon.mixins.LaserLensItemMixin;

import java.util.List;
import java.util.function.IntFunction;

public class LaserDrill extends WootUpgradeItem<LaserDrill.Variant> {
    public final String LENS_STACK_TAG = "LensStack";

    public LaserDrill() {
        super(new Properties(), Variant.DEFAULT);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void modifyDrops(@NotNull WootDropsProperties properties, @NotNull CompoundTag itemTag) {
        Variant variant = getVariant(itemTag);
        ItemStack lens;
        if(variant == Variant.DEFAULT){
            if(!itemTag.contains(LENS_STACK_TAG) || !(itemTag.get(LENS_STACK_TAG) instanceof CompoundTag lensTag))
                return;

            lens = ItemStack.of(lensTag);
            if(lens.isEmpty())
                return;
        } else {
            LaserLensItem item = (LaserLensItem) ModuleCore.LASER_LENS[variant.id()].get();
            lens = item.getDefaultInstance();
        }

        List<FluidStack> fluids = properties.getFluidDrops();

        List<LaserDrillFluidRecipe> fluidRecipes = (List<LaserDrillFluidRecipe>) RecipeUtil.getRecipes(properties.getLevel(), ModuleCore.LASER_DRILL_FLUID_TYPE.get());
        for(LaserDrillFluidRecipe recipe : fluidRecipes){
            if(recipe.catalyst.test(lens) && !recipe.entity.equals(LaserDrillFluidRecipe.EMPTY) &&
                    recipe.entity.equals(ForgeRegistries.ENTITY_TYPES.getKey(properties.getFactoryMob().getEntityType()))){
                fluids.add(FluidStack.loadFluidStackFromNBT(recipe.output));
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull InteractionResult interact(@NotNull CompoundTag itemTag, @NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit){
        ItemStack stack = player.getItemInHand(hand);
        if(stack.isEmpty())
            return InteractionResult.PASS;

        List<LaserDrillFluidRecipe> fluidRecipes = (List<LaserDrillFluidRecipe>) RecipeUtil.getRecipes(level, ModuleCore.LASER_DRILL_FLUID_TYPE.get());

        LaserDrillFluidRecipe recipe = fluidRecipes.stream()
                .filter(r -> r.catalyst.test(stack))
                .findFirst()
                .orElse(null);

        if(recipe == null)
            return InteractionResult.PASS;

        if(stack.getItem() instanceof LaserLensItem item){
            if(itemTag.contains(LENS_STACK_TAG))
                itemTag.remove(LENS_STACK_TAG);
            setVariant(itemTag, Variant.byId(((LaserLensItemMixin) item).woot$getColor()));
        } else {
            itemTag.put(LENS_STACK_TAG, stack.save(new CompoundTag()));
            setVariant(itemTag, Variant.DEFAULT);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        Variant variant = getVariant(stack.getTag());

        if(variant != Variant.DEFAULT) {
            LaserLensItem item = (LaserLensItem) ModuleCore.LASER_LENS[variant.id()].get();
            tooltip.add(item.getName(stack).copy().withStyle(variant.getColorStyle()));
        } else if(tag != null && tag.contains(LENS_STACK_TAG) && tag.get(LENS_STACK_TAG) instanceof CompoundTag lensTag){
            ItemStack lens = ItemStack.of(lensTag);
            if(!lens.isEmpty())
                tooltip.add(lens.getHoverName().copy().withStyle(variant.getColorStyle()));
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Variant variant){
        return IFWootAddon.location("textures/item/" + LASER_DRILL_TAG + "/" + variant.getSerializedName() + ".png");
    }

    public enum Variant implements WootUpgradeEnum<Variant> {
        WHITE(0, "white", DyeColor.WHITE),
        ORANGE(1, "orange", DyeColor.ORANGE),
        MAGENTA(2, "magenta", DyeColor.MAGENTA),
        LIGHT_BLUE(3, "light_blue", DyeColor.LIGHT_BLUE),
        YELLOW(4, "yellow", DyeColor.YELLOW),
        LIME(5, "lime", DyeColor.LIME),
        PINK(6, "pink", DyeColor.PINK),
        GRAY(7, "gray", DyeColor.GRAY),
        LIGHT_GRAY(8, "light_gray", DyeColor.LIGHT_GRAY),
        CYAN(9, "cyan", DyeColor.CYAN),
        PURPLE(10, "purple", DyeColor.PURPLE),
        BLUE(11, "blue", DyeColor.BLUE),
        BROWN(12, "brown", DyeColor.BROWN),
        GREEN(13, "green", DyeColor.GREEN),
        RED(14, "red", DyeColor.RED),
        BLACK(15, "black", DyeColor.BLACK),
        DEFAULT(16, "default", null);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        private static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);

        private final int id;
        private final String name;
        private final @Nullable DyeColor color;

        Variant(int id, String name, @Nullable DyeColor color){
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public int id(){
            return id;
        }

        public Style getColorStyle() {
            return Style.EMPTY.withColor(color != null ? TextColor.fromRgb(color.getTextColor()) : TextColor.fromLegacyFormat(ChatFormatting.GRAY));
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        @Override
        public Codec<Variant> codec() {
            return CODEC;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }
    }

    /* Upgrade Item registration */

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, IFWootAddon.MOD_ID);

    public static void register(WootUpgradeItemRegistration registration){
        ITEMS.register(registration.getWootEventBus());
        registration.register(LASER_DRILL_ITEM, Variant.class);
    }

    public static final String LASER_DRILL_TAG = "laser_drill_upgrade";
    public static final RegistryObject<LaserDrill> LASER_DRILL_ITEM = ITEMS.register(LASER_DRILL_TAG, LaserDrill::new);
}
