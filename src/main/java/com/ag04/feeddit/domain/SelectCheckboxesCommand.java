package com.ag04.feeddit.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko on 28.03.17..
 */
public class SelectCheckboxesCommand {
    private List<Long> selectedCheckboxes = new ArrayList<>();

    public SelectCheckboxesCommand() {
    }

    public List<Long> getSelectedCheckboxes() {
        return selectedCheckboxes;
    }

    public void setSelectedCheckboxes(List<Long> selectedCheckboxes) {
        this.selectedCheckboxes = selectedCheckboxes;
    }
}
