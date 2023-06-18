package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultAttributeContainer.Builder.class)
public class DefaultAttributeContainer$BuilderMixin {
	@Inject(method = "method_26869", at = @At("HEAD"), cancellable = true)
	private void inject$method_26869(RegistryEntry<EntityAttribute> registryEntry, EntityAttributeInstance attributex, CallbackInfo callbackInfo) {
		callbackInfo.cancel();
	}
}
