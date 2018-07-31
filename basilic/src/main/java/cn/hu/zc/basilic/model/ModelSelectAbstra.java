//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.model;

public class ModelSelectAbstra extends ModelSrlzb {
    private boolean isSelected;
    private String name;
    private int index;
    private float textWidth;
    private float textHeight;
    private int unCauseSelectListenerTimes;

    public ModelSelectAbstra() {
        this("", false);
    }

    public ModelSelectAbstra(String name) {
        this(name, false);
    }

    public ModelSelectAbstra(boolean isSelected) {
        this("", isSelected);
    }

    public ModelSelectAbstra(String name, boolean isSelected) {
        this.isSelected = false;
        this.setName(name);
        this.setSelected(isSelected);
    }

    public int getUnCauseSelectListenerTimes() {
        return this.unCauseSelectListenerTimes;
    }

    public void setUnCauseSelectListenerTimes(int unCauseSelectListenerTimes) {
        this.unCauseSelectListenerTimes = unCauseSelectListenerTimes;
    }

    public float getTextWidth() {
        return this.textWidth;
    }

    public void setTextWidth(float textWidth) {
        this.textWidth = textWidth;
    }

    public float getTextHeight() {
        return this.textHeight;
    }

    public void setTextHeight(float textHeight) {
        this.textHeight = textHeight;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
