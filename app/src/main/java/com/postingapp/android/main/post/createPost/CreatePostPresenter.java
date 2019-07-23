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

package com.postingapp.android.main.post.createPost;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.postingapp.android.R;
import com.postingapp.android.common.Common;
import com.postingapp.android.main.post.BaseCreatePostPresenter;
import com.postingapp.android.model.Post;
import com.postingapp.android.model.Profile;

/**
 * Created by Alexey on 03.05.18.
 */

public class CreatePostPresenter extends BaseCreatePostPresenter<CreatePostView> {

    public CreatePostPresenter(Context context) {
        super(context);
    }

    @Override
    protected int getSaveFailMessage() {
        return R.string.error_fail_create_post;
    }

    @Override
    protected void savePost(String description, final String postType, final String contentType, final boolean isNickname) {
        ifViewAttached(view -> {
            view.showProgress(R.string.message_creating_post);
            Profile me = Common.cm.getMyInfo();
            String userId = me.getId();
            Post post = new Post();
            post.setTitle("");
            post.setDescription(description);
            post.setAuthorId(userId);
            post.setPostType(postType);
            post.setContentType(contentType);
            post.setNickName(String.valueOf(isNickname));
            postManager.createOrUpdatePostWithImage(view.getImageUri(), this, post);
        });
    }

    @Override
    protected boolean isImageRequired() {
        return true;
    }
}
