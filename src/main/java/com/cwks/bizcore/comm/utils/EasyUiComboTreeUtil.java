package com.cwks.bizcore.comm.utils;

import com.cwks.bizcore.comm.vo.ComboTreePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyUiComboTreeUtil {
    public EasyUiComboTreeUtil() {
    }

    public static List createComboTreeTree(List<ComboTreePojo> list, String fid) {
        List<Map<String, Object>> comboTreeList = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = null;
            ComboTreePojo pojo = (ComboTreePojo)list.get(i);
            if (pojo.getParentId().equals(fid)) {
                map = new HashMap();
                map.put("id", ((ComboTreePojo)list.get(i)).getId());
                map.put("text", ((ComboTreePojo)list.get(i)).getText());
                map.put("children", createComboTreeChildren(list, pojo.getId()));
            }

            if (map != null) {
                comboTreeList.add(map);
            }
        }

        return comboTreeList;
    }

    private static List<Map<String, Object>> createComboTreeChildren(List<ComboTreePojo> list, String fid) {
        List<Map<String, Object>> childList = new ArrayList();

        for(int j = 0; j < list.size(); ++j) {
            Map<String, Object> map = null;
            ComboTreePojo treeChild = (ComboTreePojo)list.get(j);
            if (treeChild.getParentId().equals(fid)) {
                map = new HashMap();
                map.put("id", ((ComboTreePojo)list.get(j)).getId());
                map.put("text", ((ComboTreePojo)list.get(j)).getText());
                map.put("children", createComboTreeChildren(list, treeChild.getId()));
            }

            if (map != null) {
                childList.add(map);
            }
        }

        return childList;
    }
}
