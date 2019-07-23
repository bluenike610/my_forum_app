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

package com.postingapp.android.main.post.editPost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.postingapp.android.R;
import com.postingapp.android.main.main.MainActivity;
import com.postingapp.android.main.post.BaseCreatePostActivity;
import com.postingapp.android.managers.PostManager;
import com.postingapp.android.model.Post;
import com.postingapp.android.utils.GlideApp;

import static com.postingapp.android.common.Common.currentActivity;

public class EditPostActivity extends BaseCreatePostActivity<EditPostView, EditPostPresenter> implements EditPostView {
    private static final String TAG = EditPostActivity.class.getSimpleName();
    public static final String POST_EXTRA_KEY = "EditPostActivity.POST_EXTRA_KEY";
    public static final int EDIT_POST_REQUEST = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;

        Post post = (Post) getIntent().getSerializableExtra(POST_EXTRA_KEY);
        presenter.setPost(post);
        showProgress();
        fillUIFields(post);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.addCheckIsPostChangedListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.closeListeners();
    }

    @NonNull
    @Override
    public EditPostPresenter createPresenter() {
        if (presenter == null) {
            return new EditPostPresenter(this);
        }
        return presenter;
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(EditPostActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void fillUIFields(Post post) {
//        titleEditText.setText(post.getTitle());
        descriptionEditText.setText(post.getDescription());
        textContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        textContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        imageContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        imageContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        audioContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        audioContentTxt.setTextColor(getResources().getColor(R.color.text_color));
        videoContentLayout.setBackground(getResources().getDrawable(R.drawable.border_black_empty));
        videoContentTxt.setTextColor(getResources().getColor(R.color.text_color));

        switch (post.getContentType()) {
            case "text":
                textContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                textContentTxt.setTextColor(getResources().getColor(R.color.follow));
                break;
            case "image":
                imageContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                imageContentTxt.setTextColor(getResources().getColor(R.color.follow));
                break;
            case "audio":
                audioContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                audioContentTxt.setTextColor(getResources().getColor(R.color.follow));
                break;
            case "video":
                videoContentLayout.setBackground(getResources().getDrawable(R.drawable.border_follow));
                videoContentTxt.setTextColor(getResources().getColor(R.color.follow));
                break;
        }
        loadPostDetailsImage(post.getImageTitle());
        hideProgress();
    }

    private void loadPostDetailsImage(String imageTitle) {
        if (imageTitle != null) {
            PostManager.getInstance(this.getApplicationContext()).loadImageMediumSize(GlideApp.with(this),
                    imageTitle,
                    imageView,
                    () -> progressBar.setVisibility(View.GONE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                presenter.doSavePost(imageUri);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
