
package net.mcreator.enhancedvanilla.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.enhancedvanilla.EnhancedvanillaModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@EnhancedvanillaModElements.ModElement.Tag
public class PenguinentityEntity extends EnhancedvanillaModElements.ModElement {
	public static EntityType entity = null;
	public PenguinentityEntity(EnhancedvanillaModElements instance) {
		super(instance, 8);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.4f, 0.3f)).build("penguinentity")
						.setRegistryName("penguinentity");
		elements.entities.add(() -> entity);
		elements.items
				.add(() -> new SpawnEggItem(entity, -16777216, -26368, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("penguinentity"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			boolean biomeCriteria = false;
			if (ForgeRegistries.BIOMES.getKey(biome).equals(new ResourceLocation("snowy_tundra")))
				biomeCriteria = true;
			if (ForgeRegistries.BIOMES.getKey(biome).equals(new ResourceLocation("frozen_ocean")))
				biomeCriteria = true;
			if (!biomeCriteria)
				continue;
			biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(entity, 14, 4, 4));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				AnimalEntity::canAnimalSpawn);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new Modelpenguinmcmodel(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("enhancedvanilla:textures/penguinmcmodel.png");
				}
			};
		});
	}
	public static class CustomEntity extends CreatureEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 1;
			setNoAI(false);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 1));
			this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 2, 40));
			this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, CodEntity.class, true, true));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, SalmonEntity.class, true, true));
			this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, TropicalFishEntity.class, true, true));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("enhancedvanilla:penguinidle"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("enhancedvanilla:penguinhurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("enhancedvanilla:penguindeath"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3);
		}
	}

	// Made with Blockbench 3.5.4
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelpenguinmcmodel extends EntityModel<Entity> {
		private final ModelRenderer leftfoot;
		private final ModelRenderer rightfoot;
		private final ModelRenderer body;
		private final ModelRenderer leftarm;
		private final ModelRenderer rightarm2;
		private final ModelRenderer head;
		public Modelpenguinmcmodel() {
			textureWidth = 57;
			textureHeight = 42;
			leftfoot = new ModelRenderer(this);
			leftfoot.setRotationPoint(-4.0F, 24.0F, -2.5F);
			leftfoot.setTextureOffset(29, 0).addBox(6.0F, -1.0F, 1.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			leftfoot.setTextureOffset(16, 26).addBox(8.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			leftfoot.setTextureOffset(16, 28).addBox(6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			leftfoot.setTextureOffset(28, 30).addBox(6.0F, -3.0F, 2.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
			rightfoot = new ModelRenderer(this);
			rightfoot.setRotationPoint(0.0F, 24.0F, 0.0F);
			rightfoot.setTextureOffset(16, 30).addBox(-5.0F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			rightfoot.setTextureOffset(32, 15).addBox(-5.0F, -3.0F, -0.5F, 3.0F, 2.0F, 2.0F, 0.0F, false);
			rightfoot.setTextureOffset(13, 25).addBox(-3.0F, -1.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			rightfoot.setTextureOffset(22, 17).addBox(-5.0F, -1.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 0).addBox(-6.0F, -15.0F, -1.5F, 12.0F, 12.0F, 5.0F, 0.0F, false);
			body.setTextureOffset(20, 20).addBox(-4.0F, -14.0F, -2.5F, 8.0F, 9.0F, 1.0F, 0.0F, false);
			leftarm = new ModelRenderer(this);
			leftarm.setRotationPoint(-6.5F, 14.6667F, 1.5F);
			leftarm.setTextureOffset(8, 25).addBox(-0.5F, -5.6667F, -2.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
			leftarm.setTextureOffset(5, 25).addBox(-0.5F, 0.3333F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			leftarm.setTextureOffset(0, 19).addBox(-0.5F, 1.3333F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			rightarm2 = new ModelRenderer(this);
			rightarm2.setRotationPoint(6.5F, 14.6667F, 1.5F);
			rightarm2.setTextureOffset(0, 25).addBox(-0.5F, -5.6667F, -2.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
			rightarm2.setTextureOffset(24, 17).addBox(-0.5F, 0.3333F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			rightarm2.setTextureOffset(0, 17).addBox(-0.5F, 1.3333F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 24.0F, 0.0F);
			head.setTextureOffset(0, 17).addBox(-3.0F, -19.0F, -1.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
			head.setTextureOffset(16, 17).addBox(-1.0F, -17.0F, -3.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			head.setTextureOffset(0, 0).addBox(-2.0F, -18.5F, -1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			head.setTextureOffset(0, 2).addBox(1.0F, -18.5F, -1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			leftfoot.render(matrixStack, buffer, packedLight, packedOverlay);
			rightfoot.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			leftarm.render(matrixStack, buffer, packedLight, packedOverlay);
			rightarm2.render(matrixStack, buffer, packedLight, packedOverlay);
			head.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.leftfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.rightfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.rightarm2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
