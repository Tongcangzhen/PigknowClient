package com.example.ldjg.pigknowclient.dummy;

import com.example.ldjg.pigknowclient.DB.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldjg on 2018/1/18.
 */

public class RecordContent {
    public static final List<Record> ITEMS = new ArrayList<Record>();
    public static final Map<String, Record> ITEM_MAP = new HashMap<String, Record>();

    private static void addItem(Record item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getObjectId(), item);
    }

//    private static void getItem() {
//
//    }


}
