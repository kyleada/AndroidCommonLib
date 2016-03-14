package me.kkwang.commonlib.view;

import java.util.List;

/**
 * Created by kw on 2016/3/1.
 */
public interface CommonListView<T> {

    void refresh(List<T> entries);

    void refreshNoContent();

    void refreshError();

    void addMoreData(List<T> moreItems);

    void addMoreError();

    void addMoreNoContent();
}
