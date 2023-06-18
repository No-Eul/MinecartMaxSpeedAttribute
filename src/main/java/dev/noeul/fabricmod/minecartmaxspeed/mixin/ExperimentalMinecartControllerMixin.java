package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.noeul.fabricmod.minecartmaxspeed.IAbstractMinecartEntity;
import dev.noeul.fabricmod.minecartmaxspeed.MinecartMaxSpeedAttribute;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperimentalMinecartController.class)
public abstract class ExperimentalMinecartControllerMixin extends MinecartController {
	protected ExperimentalMinecartControllerMixin(AbstractMinecartEntity minecart) {
		super(minecart);
	}

	@ModifyReturnValue(method = "getMaxSpeed", at = @At("RETURN"))
	private double inject$getMaxSpeed(double original) {
		return ((IAbstractMinecartEntity) this.minecart).getAttributeValue(MinecartMaxSpeedAttribute.maxSpeed)
				* (this.minecart.isTouchingWater() ? 0.5 : 1.0) / 20.0;
	}
}
