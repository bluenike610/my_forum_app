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

package com.postingapp.android.main.editProfile.createProfile;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.postingapp.android.main.editProfile.EditProfilePresenter;
import com.postingapp.android.model.Profile;
import com.postingapp.android.utils.PreferencesUtil;

import static com.postingapp.android.common.Common.cm;

/**
 * Created by Alexey on 03.05.18.
 */

class CreateProfilePresenter extends EditProfilePresenter<CreateProfileView> {

    CreateProfilePresenter(Context context) {
        super(context);
    }

    public void buildProfile(String largeAvatarURL) {
        Profile profile = cm.getMyInfo();

        ifViewAttached(view -> {
            view.setName(profile.getUsername());

            if (profile.getPhotoUrl() != null) {
                view.setProfilePhoto(profile.getPhotoUrl());
            } else {
                view.hideLocalProgress();
                view.setDefaultProfilePhoto();
            }
        });
    }

    @Override
    protected void onProfileUpdatedSuccessfully() {
        super.onProfileUpdatedSuccessfully();
        PreferencesUtil.setProfileCreated(context, true);
        profileManager.addRegistrationToken(FirebaseInstanceId.getInstance().getToken(), profile.getId());
    }
}
