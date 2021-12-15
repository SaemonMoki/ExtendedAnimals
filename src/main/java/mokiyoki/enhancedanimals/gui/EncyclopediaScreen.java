package mokiyoki.enhancedanimals.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.GeneticsEncyclopedia;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class EncyclopediaScreen extends Screen {
    public static EncyclopediaScreen currentEncyclopedia = new EncyclopediaScreen();
    public static ItemStack encyclopedia = ItemStack.EMPTY;

    private final int bookImageHeight = 192;
    private final int bookImageWidth = 192;
    private int currPage = 0;
    private int minimumPage = -1;
    private static final int bookTotalPages = 4;
    private static ResourceLocation[] bookPageTextures =
            new ResourceLocation[bookTotalPages];
    private List<FormattedCharSequence> pageText = Collections.emptyList();
    private PageButton buttonNextPage;
    private PageButton buttonPreviousPage;

    public static final ResourceLocation encyclopediaTexture = new ResourceLocation("textures/gui/book.png");
    
    private EncyclopediaScreen.IBookInfo bookInfo;

    public EncyclopediaScreen() {
        this(new TranslatableComponent(ModItems.GENETICS_ENCYCLOPEDIA.getDescriptionId()));
    }

    protected EncyclopediaScreen(Component titleIn) {
        super(titleIn);
//        this.bookInfo = bookInfo;
    }

    @Override
    public void init() {
        super.init();

        this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, CommonComponents.GUI_DONE, (p_214161_1_) -> {
            this.minecraft.setScreen((Screen)null);
        }));

        int i = (this.width - 192) / 2;
        this.buttonNextPage = this.addButton(new PageButton(i + 116, 159, true, (button) -> {
            this.nextPage();
        }, true));
        this.buttonPreviousPage = this.addButton(new PageButton(i + 43, 159, false, (button) -> {
            this.previousPage();
        }, true));
        this.resetButtons();

    }

    protected void nextPage() {
        if (this.currPage < this.getPageAmount() - 1) {
            ++this.currPage;
        }

        this.resetButtons();
    }

    protected void previousPage() {
        if (this.currPage > 0) {
            --this.currPage;
        }

        this.resetButtons();
    }

    private int getPageAmount() {
        return this.bookInfo.getStringSize();
    }

    private void resetButtons() {
        this.buttonNextPage.visible = this.currPage < this.getPageAmount() - 1;
        this.buttonPreviousPage.visible = this.currPage > 0;
    }

    public void render(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(encyclopediaTexture);
        int i = (this.width - 192) / 2;
        int j = 2;
        this.blit(matrixStack, i, 2, 0, 0, 192, 192);
        String s = I18n.get("book.pageIndicator", this.currPage + 1, Math.max(this.getPageAmount(), 1));
        if (this.minimumPage != this.currPage) {
            FormattedText itextproperties = this.bookInfo.getPageDetails(this.currPage);
            this.pageText = this.font.split(itextproperties, 114);
        }

        this.minimumPage = this.currPage;
        int i1 = this.getFontWidth(s);
        this.font.draw(matrixStack, s, (float)(i - i1 + 192 - 44), 18.0F, 0);
        int k = Math.min(128 / 9, this.pageText.size());

        for(int l = 0; l < k; ++l) {
            FormattedCharSequence ireorderingprocessor = this.pageText.get(l);
            this.font.draw(matrixStack, ireorderingprocessor, (float)(i + 36), (float)(32 + l * 9), 0);
        }

        Style style = this.func_214154_c((double)p_render_1_, (double)p_render_2_);
        if (style != null) {
            this.renderComponentHoverEffect(matrixStack, style, p_render_1_, p_render_2_);
        }

        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
    }

    private int getFontWidth(String textLine) {
        return this.font.width(this.font.isBidirectional() ? this.font.bidirectionalShaping(textLine) : textLine);
    }

    public static List<String> readFromNBT(CompoundTag compoundNBT) {
        ListTag listnbt = compoundNBT.getList("pages", 8).copy();
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for(int i = 0; i < listnbt.size(); ++i) {
            builder.add(listnbt.getString(i));
        }

        return builder.build();
    }

    @Nullable
    public Style func_214154_c(double p_214154_1_, double p_214154_3_) {
        if (this.pageText == null) {
            return null;
        } else {
            int i = Mth.floor(p_214154_1_ - (double)((this.width - 192) / 2) - 36.0D);
            int j = Mth.floor(p_214154_3_ - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.pageText.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.pageText.size()) {
                        FormattedCharSequence ireorderingprocessor = this.pageText.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(ireorderingprocessor, i);
                    }
                    return null;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface IBookInfo {
        int getStringSize();

        Component resolvePage(int pageNumber);

        default FormattedText getPageDetails(int p_238806_1_) {
            return p_238806_1_ >= 0 && p_238806_1_ < this.getStringSize() ? this.resolvePage(p_238806_1_) : FormattedText.EMPTY;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class WrittenBookInfo implements EncyclopediaScreen.IBookInfo {
        private final List<String> encylopediaStringList;

        public WrittenBookInfo(ItemStack encyclopedia) {
            this.encylopediaStringList = transformIntoStrings(encyclopedia);
        }

        private static List<String> transformIntoStrings(ItemStack encyclopedia) {
            CompoundTag compoundnbt = encyclopedia.getTag();
            return (List<String>)(compoundnbt != null && GeneticsEncyclopedia.validBookTagContents(compoundnbt) ? EncyclopediaScreen.readFromNBT(compoundnbt) : ImmutableList.of((new TranslatableComponent("book.invalid.tag")).withStyle(ChatFormatting.DARK_RED)));
        }

        public int getStringSize() {
            return this.encylopediaStringList.size();
        }

        public Component resolvePage(int pageNumber) {
            String s = this.encylopediaStringList.get(pageNumber);

            try {
                Component itextcomponent = Component.Serializer.fromJson(s);
                if (itextcomponent != null) {
                    return itextcomponent;
                }
            } catch (Exception var4) {
                ;
            }

            return new TextComponent(s);
        }
    }

}
