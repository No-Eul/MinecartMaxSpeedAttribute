package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import dev.noeul.fabricmod.minecartmaxspeed.EntityAttributeAccessor;
import net.minecraft.entity.attribute.EntityAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAttribute.class)
public class EntityAttributeMixin implements EntityAttributeAccessor {
	@Mutable
	@Shadow
	@Final
	private double fallback;

	@Override
	public void setFallback(double value) {
		this.fallback = value;
	}
}
