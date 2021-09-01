package com.example.wangbotian;

import static com.github.promeg.pinyinhelper.Pinyin.toPinyin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.hmspicker.HmsPickerDialogFragment;
import com.javier.filterview.FilterView;
import com.javier.filterview.OnFilterCanceled;
import com.javier.filterview.OnFilterViewResultListener;
import com.javier.filterview.extra.ExtraSection;
import com.javier.filterview.extra.cboolean.ExtraBoolean;
import com.javier.filterview.extra.cboolean.OnBooleanCheckedChangeListener;
import com.javier.filterview.extra.ccurrencytext.ExtraCurrencyEditText;
import com.javier.filterview.extra.cdate.ExtraDate;
import com.javier.filterview.extra.chms.ExtraHMS;
import com.javier.filterview.extra.clist.ExtraList;
import com.javier.filterview.extra.clist.TypeList;
import com.javier.filterview.extra.ctext.ExtraEditText;
import com.javier.filterview.extra.ctext.TextType;
import com.javier.filterview.single.OnSingleOptionListener;
import com.javier.filterview.single.SingleOption;
import com.javier.filterview.single.SingleSection;
import com.javier.filterview.slider.OnSliderValueChangeListener;
import com.javier.filterview.slider.SliderOption;
import com.javier.filterview.slider.SliderSection;
import com.javier.filterview.slider.SliderType;
import com.javier.filterview.tag.OnTagListener;
import com.javier.filterview.tag.TagGravity;
import com.javier.filterview.tag.TagMode;
import com.javier.filterview.tag.TagSection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResult extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView listView;
    Button backToSearch;
    TextView textView;
    String course;
    String searchKey = "";
    String[] labels;
    String[] categories;
    private Button btShowFilterView;
    String[] cateSelected;
    String[] cateAll;
    FilterView filterView;
    EntityItem[] items;
    EntityItem[] itemsDisplayed;
    ArrayList<EntityItem> items_list;
    TextView selectResult;
    Button recover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        int searchNum = Integer.parseInt(intent.getStringExtra("search_num"));
        labels = intent.getStringArrayExtra("search_labels");
        categories = intent.getStringArrayExtra("search_categories");
        course = intent.getStringExtra("search_course");
        searchKey = intent.getStringExtra("search_key");
        listView = findViewById(R.id.list_view);
        backToSearch = findViewById(R.id.textButton);
        backToSearch.setOnClickListener(this);
        textView = findViewById(R.id.txtnum);

        selectResult = findViewById(R.id.selectBtn);
        selectResult.setText("当前排序方式：默认");
        listView.setOnItemClickListener(this);
        recover = findViewById(R.id.newBtn);
        recover.setOnClickListener(this);


        ArrayList<EntityItem> tmp = new ArrayList<>();
        for(int i = 0; i < searchNum; i++) {
            if(labels[i].indexOf(" ") < 0)
                tmp.add(new EntityItem(labels[i], categories[i])) ;
        }
        items = tmp.toArray(new EntityItem[tmp.size()]);
        searchNum = tmp.size();
        textView.setText("共 " + searchNum + " 条结果");

        itemsDisplayed = items;

        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(itemsDisplayed));
        EntityListAdapter adapter = new EntityListAdapter(this, items_list);
        this.listView.setDivider(null);
        this.listView.setAdapter(adapter);
        this.listView.setClickable(true);

        List list = Arrays.asList(categories);
        Set set = new HashSet(list);
        cateSelected = (String[]) set.toArray(new String[0]);
        cateAll = cateSelected;

        btShowFilterView = (Button)findViewById(R.id.btShowFilterView);
        btShowFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSelected();
            }
        });

    }
    private void initSelected(){

        // String title, int titleColor, int icon, int borderWidth, int borderColor
        SingleSection singleSection = new SingleSection.Builder("排序", 1)
                .setSectionNameColor(R.color.sky_blue)
                .addOption(new SingleOption("名称长度", R.color.sky_blue,
                        R.drawable.outline_timeline_24, R.color.sky_blue,
                        R.color.sky_blue, 2, R.color.sky_blue))
                .addOption(new SingleOption("相关度高", R.color.sky_blue,
                        R.drawable.baseline_link_white_48, R.color.sky_blue,
                        R.color.sky_blue, 2, R.color.sky_blue))
                .addOption(new SingleOption("字典顺序", R.color.sky_blue,
                        R.drawable.outline_bookmarks_24, R.color.sky_blue,
                        R.color.sky_blue, 2, R.color.sky_blue))
                .build().setOnSingleOptionListener(new OnSingleOptionListener() {
                    @Override
                    public void onClick(SingleOption option) {
                    }
                });
        SliderSection sliderSectionRange = new SliderSection.Builder("实体数量限制", SliderType.TYPE_RANGE, 2)
                .setSectionNameColor(R.color.sky_blue)
                .setSlider(new SliderOption(6, R.color.sky_blue,
                        1, labels.length, R.color.pressedColor,
                        R.color.sky_blue,  R.color.sky_blue))
                .build();

        sliderSectionRange.setOnSliderValueChangeListener(new OnSliderValueChangeListener() {
            @Override
            public void onValue(SliderOption option, int min, int max) {
            }
        });

        String[] data = cateAll;

        TagSection tagSection = new TagSection.Builder("按实体类别筛选", 3)
                .setSectionNameColor(R.color.sky_blue)
                .setSelectedColor(R.color.sky_blue)
                .setDeselectedColor(R.color.colorBackground)
                .setSelectedFontColor(R.color.colorBackground)
                .setDeselectedFontColor(R.color.light_sky_blue)
                .setGravity(TagGravity.LEFT)
                .setMode(TagMode.MULTI)
                .setLabels(data)
                .build();
        tagSection.setOnTagListener(new OnTagListener() {
            @Override
            public void onTagSelected(ArrayList<String> tags) {
            }
        });



        final ExtraBoolean extraBoolean1 = new ExtraBoolean(1, "实体名包含中文", R.color.sky_blue, R.color.sky_blue);
        extraBoolean1.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean1.getTitle());
                System.out.println(isChecked);
            }
        });

        final ExtraBoolean extraBoolean2 = new ExtraBoolean(2, "实体名包含英文", R.color.sky_blue, R.color.sky_blue);
        extraBoolean2.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean2.getTitle());
                System.out.println(isChecked);
            }
        });

        final ExtraBoolean extraBoolean3 = new ExtraBoolean(3, "实体名包含数字", R.color.sky_blue, R.color.sky_blue);
        extraBoolean3.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean3.getTitle());
                System.out.println(isChecked);
            }
        });



        ExtraSection extraSection = new ExtraSection.Builder("其他筛选条件", 4)
                .setSectionNameColor(R.color.sky_blue)
                .addOption(extraBoolean1)
                .addOption(extraBoolean2)
                .addOption(extraBoolean3)
                .build();


        filterView =  new FilterView.Builder(this)
                .withTitle("确定")
                .setToolbarVisible(true)
                .withTitleColor(R.color.sky_blue)
                .withDivisorColor(R.color.sky_blue)
                .setCloseIconColor(R.color.sky_blue)
                .addSection(singleSection)
                .addSection(sliderSectionRange)
                .addSection(tagSection)
                .addSection(extraSection)
                .build()
                .setOnFilterViewResultListener(new OnFilterViewResultListener() {
                    @Override
                    public void onResult(JSONArray data) {
                        Log.i("debug", data.toString());

                        setSearchResult(data);
                        filterView.setOnFilterCanceled(new OnFilterCanceled() {
                            @Override
                            public void onCancel() {
                            }
                        });
                    }
                });
        filterView.show();
    }
    private void setSearchResult(JSONArray setting) {
        try {
            List<EntityItem> itmesTmp = new ArrayList<EntityItem>();
            for(EntityItem e : items) {
                itmesTmp.add(e);
            }

            JSONObject cateChoose = setting.getJSONObject(2);
            if(cateChoose.isNull("value")) {
                Log.i("p", "pass");
            } else {
                String cateSelect = cateChoose.getString("value");
                Log.i("cs", items.length+"");
                for(int i = itmesTmp.size() - 1; i >= 0; i--) {
                    if(!cateSelect.contains(items[i].getCategory())) {
                        Log.i("gc", items[i].getCategory());
                        itmesTmp.remove(i);
                    }
                }
            }

            Boolean[] isSelect = new Boolean[itmesTmp.size()];
            for(int i = 0; i < isSelect.length; i++) {
                isSelect[i] = true;
            }
            JSONObject extraChoose = setting.getJSONObject(3);
            if(extraChoose.isNull("type")) {
                Log.i("p", "pass");
            } else {
                if(!extraChoose.isNull("1")) {
                    for(int i = 0; i < itmesTmp.size(); i++) {
                        if(!isContainChinese(itmesTmp.get(i).getLabel())) {
                            isSelect[i] = false;
                        }
                    }
                }
                if(!extraChoose.isNull("2")) {
                    for(int i = 0; i < itmesTmp.size(); i++) {
                        if(!isContainEnglish(itmesTmp.get(i).getLabel())) {
                            isSelect[i] = false;
                        }
                    }
                }
                if(!extraChoose.isNull("3")) {
                    for(int i = 0; i < itmesTmp.size(); i++) {
                        if(!isContainNumber(itmesTmp.get(i).getLabel())) {
                            isSelect[i] = false;
                        }
                    }
                }
            }
            for(int i = isSelect.length - 1; i >= 0; i--) {
                if(!isSelect[i]) {
                    itmesTmp.remove(i);
                }
            }

            JSONObject sortMethod = setting.getJSONObject(0);
            Log.i("sm", sortMethod.toString());
            if(sortMethod.isNull("value")) {
                Log.i("p", "pass");
                selectResult.setText("当前排序方式：默认");
            } else {
                String method;
                EntityItem[] tmp = itmesTmp.toArray(new EntityItem[itmesTmp.size()]);
                method = sortMethod.getString("value");
                if(method == "名称长度") {
                    selectResult.setText("当前排序方式：文本长度");
                    sortByLength(tmp);

                } else if(method == "相关度高") {
                    selectResult.setText("当前排序方式：相关度高");
                    sortByRelevance(tmp, searchKey);
                } else if(method == "字典顺序") {
                    selectResult.setText("当前排序方式：字典顺序");
                    sortByOrder(tmp);
                }
                itmesTmp = new ArrayList<EntityItem>();
                for(EntityItem e : tmp) {
                    itmesTmp.add(e);
                }
            }

            JSONObject entityNum = setting.getJSONObject(1);
            int valueMin = Integer.parseInt(entityNum.getString("value_min"));
            int valueMax = Integer.parseInt(entityNum.getString("value_max"));
            int value = valueMax - valueMin;
            Log.i("v", "" + value);
            if(itmesTmp.size() > value) {
                for(int i = itmesTmp.size() - 1; i >= value; i--) {
                    itmesTmp.remove(i);
                }
            }
            textView.setText("共 " + itmesTmp.size() + " 条结果");
            Log.i("length", ""+itmesTmp.size());
            itemsDisplayed = itmesTmp.toArray(new EntityItem[itmesTmp.size()]);
            items_list = new ArrayList<EntityItem>(Arrays.asList(itemsDisplayed));
            EntityListAdapter adapter = new EntityListAdapter(this, items_list);
            this.listView.setDivider(null);
            this.listView.setAdapter(adapter);
            this.listView.setClickable(true);
        } catch (Exception e) {
            Log.i("er", e.toString());
        }
    }

    public static boolean isContainChinese(String str)  {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    public static boolean isContainEnglish(String str)  {
        Pattern p = Pattern.compile(".*[a-zA-z].*");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    public static boolean isContainNumber(String str)  {
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textButton:
                Intent intent = new Intent();
                intent.setClass(SearchResult.this, SearchActivity.class);
                intent.putExtra("search_key", searchKey);
                intent.putExtra("search_course", course);
                this.startActivity(intent);
                this.finish();
                break;
            case R.id.newBtn:
                itemsDisplayed = items;
                textView.setText("共 " + items.length + " 条结果");
                selectResult.setText("当前排序方式：默认");
                ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(itemsDisplayed));
                EntityListAdapter adapter = new EntityListAdapter(this, items_list);
                this.listView.setDivider(null);
                this.listView.setAdapter(adapter);
                this.listView.setClickable(true);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long id){
        String result_label = items_list.get(position).getLabel();
        String course = items_list.get(position).getCategory();
        Intent intent = new Intent();
        intent.setClass(SearchResult.this, EntityActivity.class);
        intent.putExtra("label", result_label);
        intent.putExtra("course", course);
        this.startActivity(intent);
//        this.finish(); 试试不加finish？正常应该从实体详情点返回返回到实体列表
    }

    public static void sortByLength(EntityItem[] arrStr) {
        EntityItem temp;
        for (int i = 0; i < arrStr.length; i++) {
            for (int j = arrStr.length - 1; j > i; j--) {
                if (arrStr[i].getLabel().length() > arrStr[j].getLabel().length()) {
                    temp = arrStr[i];
                    arrStr[i] = arrStr[j];
                    arrStr[j] = temp;
                }
            }
        }
    }
    public static void sortByRelevance(EntityItem[] arrStr, String searchKey) {
        EntityItem temp;
        for (int i = 0; i < arrStr.length; i++) {
            for (int j = arrStr.length - 1; j > i; j--) {
                if (arrStr[i].getLabel().indexOf(searchKey) > arrStr[j].getLabel().indexOf(searchKey)) {
                    temp = arrStr[i];
                    arrStr[i] = arrStr[j];
                    arrStr[j] = temp;
                }
            }
        }
    }
    public static void sortByOrder(EntityItem[] arrStr) {
        EntityItem temp;
        for (int i = 0; i < arrStr.length; i++) {
            for (int j = arrStr.length - 1; j > i; j--) {
                if (toPinyin(arrStr[i].getLabel(), "").compareToIgnoreCase(toPinyin(arrStr[j].getLabel(), "")) > 0) {
                    temp = arrStr[i];
                    arrStr[i] = arrStr[j];
                    arrStr[j] = temp;
                }
            }
        }
    }
}
