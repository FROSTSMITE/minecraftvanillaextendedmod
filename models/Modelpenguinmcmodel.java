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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.leftfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.rightfoot.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		this.rightarm2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
	}
}