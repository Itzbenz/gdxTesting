package io.itzbenz.reactive;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class ScrollableDialog extends AtomicDialog{
    protected ScrollPane scrollPane;
    protected Table scrollTable, cont;
    @Override
    protected void init() {
        float scrollX = 0;
        if(scrollPane == null) {
            scrollPane = new ScrollPane(getContentTable());
        }
        cont.add(scrollPane).growX().growY().row();

        scrollX = scrollPane.getScrollX();
        super.init();
        scrollPane.setScrollX(scrollX);

    }

    @Override
    public Table getContentTable() {
        cont = super.getContentTable();
        if(scrollTable == null) {
            scrollTable = new Table();
            scrollTable.setSkin(getSkin());
        }
        return scrollTable;
    }
}
