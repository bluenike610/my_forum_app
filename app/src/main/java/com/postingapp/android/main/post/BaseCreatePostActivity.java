/*
 * Copyright 2018 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.postingapp.android.main.post;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.postingapp.android.R;
import com.postingapp.android.main.pickImageBase.PickImageActivity;

import static com.postingapp.android.common.Common.currentActivity;

/**
 * Created by Alexey on 03.05.18.
 */
public abstract class BaseCreatePostActivity<V extends BaseCreatePostView, P extends BaseCreatePostPresenter<V>>
        extends PickImageActivity<V, P> implements BaseCreatePostView, View.OnClickListener {

    protected ImageView imageView;
    protected ProgressBar progressBar;
//    protected EditText titleEditText;
//    protected EditText descriptionEditText;

//    protected LinearLayout addPostBtn;
    protected LinearLayout textContentLayout;
    protected LinearLayout imageContentLayout;
    protected LinearLayout audioContentLayout;
    protected LinearLayout videoContentLayout;
    protected TextView textContentTxt;
    protected TextView imageContentTxt;
    protected TextView audioContentTxt;
    protected TextView videoContentTxt;
    protected Spinner postTypeSpin;
    protected EditText descriptionEditText;
    protected CheckBox nicknameRadio;
    protected String postType;
    protected String contentType = "text";
    protected String content;
    protected boolean isNickname = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        setContentView(R.layout.base_create_post_activity);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        titleEditText = findViewById(R.id.titleEditText);
//        descriptionEditText = findViewById(R.id.descriptionEditText);
        progressBar = findViewById(R.id.progressBar);

        imageView = findViewById(R.id.imageView);
//
        imageView.setOnClickListener(v -> onSelectImageClick(v));

//        titleEditText.setOnTouchListener((v, event) -> {
//            if (titleEditText.hasFocus() && titleEditText.getError() != null) {
//                titleEditText.setError(null);
//                return true;
//            }
//
//            return false;
//        });


//        addPostBtn = findViewById(R.id.postSubmitLayout);
//        addPostBtn.setOnClickListener(this);

        final String[] typeList = currentActivity.getResources().getStringArray(R.array.post_types);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, typeList);
        postTypeSpin = findViewById(R.id.postTypeSpin);
        postTypeSpin.setAdapter(dataAdapter);
        postType = typeList[0];
        postTypeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                postType = typeList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textContentLayout = findViewById(R.id.textContentLayout);
        textContentLayout.setOnClickListener(this);
        imageContentLayout = findViewById(R.id.imageContentLayout);
        imageContentLayout.setOnClickListener(this);
        audioContentLayout = findViewById(R.id.audioContentLayout);
        audioContentLayout.setOnClickListener(this);
        videoContentLayout = findViewById(R.id.videoContentLayout);
        videoContentLayout.setOnClickListener(this);

        textContentTxt = findViewById(R.id.textContentTxt);
        imageContentTxt = findViewById(R.id.imageContentTxt);
        audioContentTxt = findViewById(R.id.audioContentTxt);
        videoContentTxt = findViewById(R.id.videoContentTxt);

        descriptionEditText = findViewById(R.id.contentTxt);

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(1000);
        descriptionEditText.setFilters(FilterArray);

        nicknameRadio = findViewById(R.id.nicknameRadio);
        nicknameRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isNickname = b;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeContentType(int type) {
        textContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        textContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        imageContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        imageContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        audioContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        audioContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        videoContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        videoContentTxt.setTextColor(getResources().getColor(R.color.text_color));

        switch (type) {
            case 0:
                textContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                textContentTxt.setTextColor(getResources().getColor(R.color.follow));
                contentType = "text";
                break;
            case 1:
                imageContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                imageContentTxt.setTextColor(getResources().getColor(R.color.follow));
                contentType = "image";
                break;
            case 2:
                audioContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                audioContentTxt.setTextColor(getResources().getColor(R.color.follow));
                contentType = "audio";
                break;
            case 3:
                videoContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                videoContentTxt.setTextColor(getResources().getColor(R.color.follow));
                contentType = "video";
                break;
        }
    }

    @NonNull
    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    protected ProgressBar getProgressView() {
        return progressBar;
    }

    @Override
    protected ImageView getImageView() {
        return imageView;
    }

    @Override
    protected void onImagePikedAction() {
        loadImageToImageView(imageUri);
    }

    @Override
    public void setDescriptionError(String error) {
        descriptionEditText.setError(error);
        descriptionEditText.requestFocus();
    }

    @Override
    public void setTitleError(String error) {
//        titleEditText.setError(error);
//        titleEditText.requestFocus();
    }

    @Override
    public String getTitleText() {
//        return titleEditText.getText().toString();
        return "";
    }

    @Override
    public String getDescriptionText() {
        return descriptionEditText.getText().toString();
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean getNicknameUsed() {
        return isNickname;
    }

    @Override
    public void requestImageViewFocus() {
        imageView.requestFocus();
    }

    @Override
    public void onPostSavedSuccess() {
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textContentLayout:
                changeContentType(0);
                break;
            case R.id.imageContentLayout:
                changeContentType(1);
                break;
            case R.id.audioContentLayout:
                changeContentType(2);
                break;
            case R.id.videoContentLayout:
                changeContentType(3);
                break;
        }
    }
}
