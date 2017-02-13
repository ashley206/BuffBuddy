package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;

import java.util.List;

/**
 * Created by Ashley H on 1/19/2017.
 */

public interface IDAO<T> {
    public void Create(T obj, Context c);
    public void Update(T obj, Context c);
    public void Delete(T obj, Context c);
    public T FindByName(String name, Context c);
    public List<T> FetchAll(Context context);
}
