package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.util.Variation;
import mokiyoki.enhancedanimals.entity.util.VariationKey;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

public final class TextureBuilder {

    private final TextureCacheKeyWriter key;
    private final Variation variation;
    private final int[] gene;
    private final BooleanSupplier female;
    private final IntSupplier sexVariation;
    private final BooleanSupplier baby;
    private final DoubleSupplier growthAmount;
    private final Consumer<TextureGrouping> output;
    private final TextureGroup root;

    public TextureBuilder(TextureCacheKeyWriter key, Variation variation, int[] gene, BooleanSupplier female, IntSupplier sexVariation, BooleanSupplier baby, DoubleSupplier growthAmount, Consumer<TextureGrouping> output) {
        this.key = key;
        this.variation = variation;
        this.gene = gene;
        this.female = female;
        this.sexVariation = sexVariation;
        this.baby = baby;
        this.growthAmount = growthAmount;
        this.output = output;
        this.root = new TextureGroup(this, TextureGrouping.merge());
    }

    public static TextureBuilder of(EnhancedAnimalAbstract animal, int[] gene) {
        return new TextureBuilder(animal, animal.getVariation(), gene, animal::getOrSetIsFemale, animal::getSexVariation, animal::isBaby, animal::growthAmount, animal::setTextureGrouping);
    }

    public TextureGroup root() {
        return root;
    }

    public void finishTexture() {
        this.key.writeKeyField(root.grouping().cacheKey());
        this.output.accept(root.grouping());
    }

    public int gene(int index) {
        return gene[index];
    }

    public int sum(int index) {
        return gene[index] + gene[index + 1];
    }

    public int max(int index) {
        return Math.max(gene[index], gene[index + 1]);
    }

    public boolean eitherAllelesHave(int index, int allele) {
        return gene[index] == allele || gene[index + 1] == allele;
    }

    public boolean isHomoFor(int index, int allele) {
        return gene[index] == allele && gene[index + 1] == allele;
    }

    public boolean isHetFor(int index, int allele) {
        return gene[index] == allele ^ gene[index + 1] == allele;
    }

    public boolean bothAllelesHaveAnyOf(int index, int... alleles) {
        return isOneOf(gene[index], alleles) && isOneOf(gene[index + 1], alleles);
    }

    public boolean eitherAllelesHaveAnyOf(int index, int... alleles) {
        return isOneOf(gene[index], alleles) || isOneOf(gene[index + 1], alleles);
    }

    private static boolean isOneOf(int value, int... alleles) {
        for (int allele : alleles) {
            if (value == allele) return true;
        }
        return false;
    }

    public boolean ifIsFemale() {
        return female.getAsBoolean();
    }

    public boolean ifIsBaby() {
        return baby.getAsBoolean();
    }

    public float growthAmount() {
        return (float) growthAmount.getAsDouble();
    }

    public int sexVariation() {
        return sexVariation.getAsInt();
    }

    public Variation variation() {
        return variation;
    }

    public int randomHex(VariationKey key) {
        return variation.hex(key);
    }

    public char randomHexChar(VariationKey key) {
        return variation.hexChar(key);
    }

    public int randomBoundedInt(VariationKey key, int bound) {
        return variation.get(key, bound);
    }

    public boolean randomChance(VariationKey key, int percentageSuccess) {
        if (percentageSuccess < 1 || percentageSuccess > 99) {
            throw new IllegalArgumentException("percentageSuccess must be 1 to 99, was " + percentageSuccess);
        }
        int divisor = gcd(percentageSuccess, 100);
        return variation.get(key, 100 / divisor) < percentageSuccess / divisor;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
