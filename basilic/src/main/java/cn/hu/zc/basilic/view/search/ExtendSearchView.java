package cn.hu.zc.basilic.view.search;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import cn.hu.zc.basilic.R;
import cn.hu.zc.tools.ReflectTools;

public class ExtendSearchView extends SearchView {
    private LinearLayout searchBar;//最底部的linearlayout
    private TextView searchBadge;//搜索icon前文字
    private ImageView searchButton;//搜索icon在框内（iconifiedByDefault=true时显示）
    private LinearLayout searchEditFrame;//编辑区域底部
    private ImageView searchIcon; //搜索icon在框外（iconifiedByDefault=false时显示）
    private LinearLayout searchPlate;//搜索区域底部
    private AutoCompleteTextView searchSrcText;//搜索框
    private ImageView searchCloseBtn;//清除图标
    private LinearLayout submitArea;
    private ImageView searchGoBtn;
    private ImageView searchVoiceBtn;

    public ExtendSearchView(Context context) {
        this(context, null);
    }

    public ExtendSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        set();
    }


    private void init() {
        //最底部的linearlayout
        int search_barId = getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        searchBar = findViewById(search_barId);
        //TODO 咱不知
        int search_badgeId = getContext().getResources().getIdentifier("android:id/search_badge", null, null);
        searchBadge = findViewById(search_badgeId);
        //TODO
        int search_buttonId = getContext().getResources().getIdentifier("android:id/search_button", null, null);
        searchButton = findViewById(search_buttonId);

        ////《——编辑区域
        int search_edit_frameId = getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        searchEditFrame = findViewById(search_edit_frameId);
        //搜索图标
        int search_mag_iconId = getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        searchIcon = findViewById(search_mag_iconId);
        //《搜索区域
        int search_plateId = getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        searchPlate = findViewById(search_plateId);
        //搜索框
        int search_src_textId = getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        searchSrcText = findViewById(search_src_textId);
        //清除图标
        int search_close_btnId = getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        searchCloseBtn = findViewById(search_close_btnId);
        //搜索区域》
        ////编辑区域——》

        ////《——提交区域
        int submit_areaId = getContext().getResources().getIdentifier("android:id/submit_area", null, null);
        submitArea = findViewById(submit_areaId);
        //TODO
        int search_go_btnId = getContext().getResources().getIdentifier("android:id/search_go_btn", null, null);
        searchGoBtn = findViewById(search_go_btnId);
        //TODO
        int search_voice_btnId = getContext().getResources().getIdentifier("android:id/search_voice_btn", null, null);
        searchVoiceBtn = findViewById(search_voice_btnId);
        ////提交区域——》

    }

    private void set() {
        searchBar.setBackgroundResource(R.drawable.corner_gray_ffd6d3d3);
        searchSrcText.setThreshold(1);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * @param res 设置光标样式
     */
    public void setCursorDrawableRes(@DrawableRes int res) {
        if (searchSrcText == null) return;
        ReflectTools.setField(searchSrcText, TextView.class, "mCursorDrawableRes", res);
    }
    public AutoCompleteTextView getSearchSrcText() {
        return searchSrcText;
    }
}
