package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import dev.noeul.fabricmod.minecartmaxspeed.AttributeCommandHelper;
import dev.noeul.fabricmod.minecartmaxspeed.MinecartMaxSpeedAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.AttributeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AttributeCommand.class)
public abstract class AttributeCommandMixin {
	@Shadow
	@Final
	private static Dynamic3CommandExceptionType NO_MODIFIER_EXCEPTION;

	@Shadow
	@Final
	private static Dynamic3CommandExceptionType MODIFIER_ALREADY_PRESENT_EXCEPTION;

	@Shadow
	private static Text getName(RegistryEntry<EntityAttribute> attribute) {
		throw new AssertionError();
	}

	@Inject(method = "executeValueGet", at = @At("HEAD"), cancellable = true)
	private static void inject$executeValueGet(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			double multiplier,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		Entity livingEntity = AttributeCommandHelper.getLivingEntityWithAttribute(target, attribute);
		double d = AttributeCommandHelper.getAttributeValue(livingEntity, attribute);
		source.sendFeedback(() -> Text.translatable("commands.attribute.value.get.success", getName(attribute), target.getName(), d), false);
		callbackInfo.setReturnValue((int) (d * multiplier));
	}

	@Inject(method = "executeBaseValueGet", at = @At("HEAD"), cancellable = true)
	private static void inject$executeBaseValueGet(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			double multiplier,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		Entity livingEntity = AttributeCommandHelper.getLivingEntityWithAttribute(target, attribute);
		double d = AttributeCommandHelper.getAttributeBaseValue(livingEntity, attribute);
		source.sendFeedback(() -> Text.translatable("commands.attribute.base_value.get.success", getName(attribute), target.getName(), d), false);
		callbackInfo.setReturnValue((int) (d * multiplier));
	}

	@Inject(method = "executeModifierValueGet", at = @At("HEAD"), cancellable = true)
	private static void inject$executeModifierValueGet(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			Identifier id,
			double multiplier,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		Entity livingEntity = AttributeCommandHelper.getLivingEntityWithAttribute(target, attribute);
		AttributeContainer attributeContainer = AttributeCommandHelper.getAttributes(livingEntity);
		if (!attributeContainer.hasModifierForAttribute(attribute, id)) {
			throw NO_MODIFIER_EXCEPTION.create(target.getName(), getName(attribute), id);
		} else {
			double d = attributeContainer.getModifierValue(attribute, id);
			source.sendFeedback(() -> Text.translatable("commands.attribute.modifier.value.get.success", Text.of(id), getName(attribute), target.getName(), d), false);
			callbackInfo.setReturnValue((int) (d * multiplier));
		}
	}

	@Inject(method = "executeBaseValueSet", at = @At("HEAD"), cancellable = true)
	private static void inject$executeBaseValueSet(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			double value,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		AttributeCommandHelper.getAttributeInstance(target, attribute).setBaseValue(value);
		source.sendFeedback(() -> Text.translatable("commands.attribute.base_value.set.success", getName(attribute), target.getName(), value), false);
		callbackInfo.setReturnValue(1);
	}

	@Inject(method = "executeModifierAdd", at = @At("HEAD"), cancellable = true)
	private static void inject$executeModifierAdd(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			Identifier id,
			double value,
			EntityAttributeModifier.Operation operation,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		EntityAttributeInstance entityAttributeInstance = AttributeCommandHelper.getAttributeInstance(target, attribute);
		EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(id, value, operation);
		if (entityAttributeInstance.hasModifier(id)) {
			throw MODIFIER_ALREADY_PRESENT_EXCEPTION.create(target.getName(), getName(attribute), id);
		} else {
			entityAttributeInstance.addPersistentModifier(entityAttributeModifier);
			source.sendFeedback(() -> Text.translatable("commands.attribute.modifier.add.success", Text.of(id), getName(attribute), target.getName()), false);
			callbackInfo.setReturnValue(1);
		}
	}

	@Inject(method = "executeModifierRemove", at = @At("HEAD"), cancellable = true)
	private static void inject$executeModifierRemove(
			ServerCommandSource source,
			Entity target,
			RegistryEntry<EntityAttribute> attribute,
			Identifier id,
			CallbackInfoReturnable<Integer> callbackInfo
	) throws CommandSyntaxException {
		if (!MinecartMaxSpeedAttribute.areMinecartImprovementsEnabled(source.getServer())) return;
		EntityAttributeInstance entityAttributeInstance = AttributeCommandHelper.getAttributeInstance(target, attribute);
		if (entityAttributeInstance.removeModifier(id)) {
			source.sendFeedback(() -> Text.translatable("commands.attribute.modifier.remove.success", Text.of(id), getName(attribute), target.getName()), false);
			callbackInfo.setReturnValue(1);
		} else {
			throw NO_MODIFIER_EXCEPTION.create(target.getName(), getName(attribute), id);
		}
	}

	@Redirect(method = "getName", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"
	))
	private static MutableText redirect$getName(String key) {
		return Text.translatable(
				key.equalsIgnoreCase(MinecartMaxSpeedAttribute.maxSpeed.value().getTranslationKey())
						? GameRules.MINECART_MAX_SPEED.getTranslationKey()
						: key
		);
	}
}
