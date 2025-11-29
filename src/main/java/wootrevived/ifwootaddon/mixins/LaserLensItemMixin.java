package wootrevived.ifwootaddon.mixins;

import com.buuz135.industrial.item.LaserLensItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LaserLensItem.class)
public interface LaserLensItemMixin {
    @Accessor(value = "color", remap = false)
    int woot$getColor();
}
