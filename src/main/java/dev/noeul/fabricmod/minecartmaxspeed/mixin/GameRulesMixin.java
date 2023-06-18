package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import dev.noeul.fabricmod.minecartmaxspeed.EntityAttributeAccessor;
import dev.noeul.fabricmod.minecartmaxspeed.MinecartMaxSpeedAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.class)
public class GameRulesMixin {
	@Inject(method = "method_61727", at = @At("TAIL"))
	private static void inject$method_61727(MinecraftServer server, GameRules.IntRule intRule, CallbackInfo callbackInfo) {
		((EntityAttributeAccessor) MinecartMaxSpeedAttribute.maxSpeed.value())
				.setFallback(intRule.get());
		for (EntityType<? extends AbstractMinecartEntity> minecart : MinecartMaxSpeedAttribute.minecarts) {
			EntityAttributeInstance attributeInstance = DefaultAttributeRegistry.get((EntityType) minecart)
					.require(MinecartMaxSpeedAttribute.maxSpeed);
			attributeInstance.setBaseValue(intRule.get());
		}
	}
}
