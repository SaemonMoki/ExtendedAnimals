package mokiyoki.enhancedanimals.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.GeneticsEncyclopedia;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
    private List<ITextComponent> pageText = Collections.emptyList();
    private ChangePageButton buttonNextPage;
    private ChangePageButton buttonPreviousPage;

    public static final ResourceLocation encyclopediaTexture = new ResourceLocation("textures/gui/book.png");
    
    private EncyclopediaScreen.IBookInfo bookInfo;

    public EncyclopediaScreen() {
        this(new TranslationTextComponent(ModItems.Genetics_Encyclopedia.getTranslationKey()));
    }

    protected EncyclopediaScreen(ITextComponent titleIn) {
        super(titleIn);
//        this.bookInfo = bookInfo;
    }

    @Override
    public void init() {
        super.init();

        this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done"), (p_214161_1_) -> {
            this.minecraft.displayGuiScreen((Screen)null);
        }));

        int i = (this.width - 192) / 2;
        this.buttonNextPage = this.addButton(new ChangePageButton(i + 116, 159, true, (button) -> {
            this.nextPage();
        }, true));
        this.buttonPreviousPage = this.addButton(new ChangePageButton(i + 43, 159, false, (button) -> {
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

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(encyclopediaTexture);
        int i = (this.width - 192) / 2;
        int j = 2;
        this.blit(i, 2, 0, 0, 192, 192);
        String s = I18n.format("book.pageIndicator", this.currPage + 1, Math.max(this.getPageAmount(), 1));
        if (this.minimumPage != this.currPage) {
            ITextComponent itextcomponent = this.bookInfo.getPageDetails(this.currPage);
            this.pageText = RenderComponentsUtil.splitText(itextcomponent, 114, this.font, true, true);
        }

        this.minimumPage = this.currPage;
        int i1 = this.getFontWidth(s);
        this.font.drawString(s, (float)(i - i1 + 192 - 44), 18.0F, 0);
        int k = Math.min(128 / 9, this.pageText.size());

        for(int l = 0; l < k; ++l) {
            ITextComponent itextcomponent1 = this.pageText.get(l);
            this.font.drawString(itextcomponent1.getFormattedText(), (float)(i + 36), (float)(32 + l * 9), 0);
        }

        ITextComponent itextcomponent2 = this.func_214154_c((double)p_render_1_, (double)p_render_2_);
        if (itextcomponent2 != null) {
            this.renderComponentHoverEffect(itextcomponent2, p_render_1_, p_render_2_);
        }

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private int getFontWidth(String textLine) {
        return this.font.getStringWidth(this.font.getBidiFlag() ? this.font.bidiReorder(textLine) : textLine);
    }

    public static List<String> readFromNBT(CompoundNBT compoundNBT) {
        ListNBT listnbt = compoundNBT.getList("pages", 8).copy();
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for(int i = 0; i < listnbt.size(); ++i) {
            builder.add(listnbt.getString(i));
        }

        return builder.build();
    }

    @Nullable
    public ITextComponent func_214154_c(double p_214154_1_, double p_214154_3_) {
        if (this.pageText == null) {
            return null;
        } else {
            int i = MathHelper.floor(p_214154_1_ - (double)((this.width - 192) / 2) - 36.0D);
            int j = MathHelper.floor(p_214154_3_ - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.pageText.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.pageText.size()) {
                        ITextComponent itextcomponent = this.pageText.get(l);
                        int i1 = 0;

                        for(ITextComponent itextcomponent1 : itextcomponent) {
                            if (itextcomponent1 instanceof StringTextComponent) {
                                i1 += this.minecraft.fontRenderer.getStringWidth(itextcomponent1.getFormattedText());
                                if (i1 > i) {
                                    return itextcomponent1;
                                }
                            }
                        }
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

        ITextComponent resolvePage(int pageNumber);

        default ITextComponent getPageDetails(int pageNumber) {
            return (ITextComponent)(pageNumber >= 0 && pageNumber < this.getStringSize() ? this.resolvePage(pageNumber) : new StringTextComponent(""));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class WrittenBookInfo implements EncyclopediaScreen.IBookInfo {
        private final List<String> encylopediaStringList;

        public WrittenBookInfo(ItemStack encyclopedia) {
            this.encylopediaStringList = transformIntoStrings(encyclopedia);
        }

        private static List<String> transformIntoStrings(ItemStack encyclopedia) {
            CompoundNBT compoundnbt = encyclopedia.getTag();
            return (List<String>)(compoundnbt != null && GeneticsEncyclopedia.validBookTagContents(compoundnbt) ? EncyclopediaScreen.readFromNBT(compoundnbt) : ImmutableList.of((new TranslationTextComponent("book.invalid.tag")).applyTextStyle(TextFormatting.DARK_RED).getFormattedText()));
        }

        public int getStringSize() {
            return this.encylopediaStringList.size();
        }

        public ITextComponent resolvePage(int pageNumber) {
            String s = this.encylopediaStringList.get(pageNumber);

            try {
                ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s);
                if (itextcomponent != null) {
                    return itextcomponent;
                }
            } catch (Exception var4) {
                ;
            }

            return new StringTextComponent(s);
        }
    }

}
