package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import dev.noeul.fabricmod.minecartmaxspeed.IAbstractMinecartEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends VehicleEntity implements IAbstractMinecartEntity {
	@Mutable
	private @Final AttributeContainer attributes;

	@Shadow
	public static boolean areMinecartImprovementsEnabled(World world) {
		throw new AssertionError();
	}

	private AbstractMinecartEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"))
	private void inject$_init_(EntityType<?> entityType, World world, CallbackInfo callbackInfo) {
		if (!areMinecartImprovementsEnabled(this.getWorld())) return;
		this.attributes = new AttributeContainer(DefaultAttributeRegistry.get((EntityType) entityType));
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void inject$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
		if (!areMinecartImprovementsEnabled(this.getWorld())) return;
		nbt.put("attributes", this.getAttributes().toNbt());
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void inject$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
		if (!areMinecartImprovementsEnabled(this.getWorld())) return;
		if (nbt.contains("attributes", NbtElement.LIST_TYPE) && this.getWorld() != null && !this.getWorld().isClient)
			this.getAttributes().readNbt(nbt.getList("attributes", NbtElement.COMPOUND_TYPE));
	}

	@Override
	public AttributeContainer getAttributes() {
		return areMinecartImprovementsEnabled(this.getWorld()) ? this.attributes : null;
	}
}
