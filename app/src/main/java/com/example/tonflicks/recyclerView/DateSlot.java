package com.example.tonflicks.recyclerView;

public class DateSlot {
    private String label;
    private boolean isSelected;

    public DateSlot(String label) {
        this.label = label;
        this.isSelected = false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}