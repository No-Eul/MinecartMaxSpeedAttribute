package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import dev.noeul.fabricmod.minecartmaxspeed.MinecartMaxSpeedAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void inject$_clinit_(CallbackInfo callbackInfo) {
		MinecartMaxSpeedAttribute.maxSpeed = Registry.registerReference(
				Registries.ATTRIBUTE,
				Identifier.of(MinecartMaxSpeedAttribute.ID, "max_speed"),
				new ClampedEntityAttribute("attribute.name.max_speed", 8.0, 1.0, 1000.0).setTracked(true)
		);
	}
}
