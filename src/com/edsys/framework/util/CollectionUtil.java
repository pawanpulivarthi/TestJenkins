package com.edsys.framework.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

public final class CollectionUtil
{
  /*public static <T> T[] toArray(List<T> list, Class<T> elementType, boolean clearList)
  {
    T[] ts = (Object[])Array.newInstance(elementType, 0);
    if ((list != null) && (list.size() > 0))
    {
      ts = (Object[])Array.newInstance(elementType, list.size());
      ts = list.toArray(ts);
      if (clearList) {
        list.clear();
      }
    }
    return ts;
  }*/
  
  public static int[] toArray(List<Integer> list, boolean clearList)
  {
    int[] vals = new int[0];
    if ((list != null) && (list.size() > 0))
    {
      vals = new int[list.size()];
      int index = 0;
      for (Iterator i$ = list.iterator(); i$.hasNext();)
      {
        int val = ((Integer)i$.next()).intValue();
        vals[(index++)] = val;
      }
      if (clearList) {
        list.clear();
      }
    }
    return vals;
  }
}
