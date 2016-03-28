/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.setupwizardlib.SetupWizardLayout;
import com.android.setupwizardlib.util.SystemBarHelper;
import com.android.setupwizardlib.view.NavigationBar;

/**
 * Setup Wizard's version of ChooseLockPassword screen. It inherits the logic and basic structure
 * from ChooseLockPassword class, and should remain similar to that behaviorally. This class should
 * only overload base methods for minor theme and behavior differences specific to Setup Wizard.
 * Other changes should be done to ChooseLockPassword class instead and let this class inherit
 * those changes.
 */
public class SetupChooseLockPassword extends ChooseLockPassword {

    public static Intent createIntent(Context context, int quality,
            int minLength, final int maxLength, boolean requirePasswordToDecrypt,
            boolean confirmCredentials) {
        Intent intent = ChooseLockPassword.createIntent(context, quality, minLength,
                maxLength, requirePasswordToDecrypt, confirmCredentials);
        intent.setClass(context, SetupChooseLockPassword.class);
        intent.putExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false);
        return intent;
    }

    public static Intent createIntent(Context context, int quality,
            int minLength, final int maxLength, boolean requirePasswordToDecrypt, String password) {
        Intent intent = ChooseLockPassword.createIntent(context, quality, minLength, maxLength,
                requirePasswordToDecrypt, password);
        intent.setClass(context, SetupChooseLockPassword.class);
        intent.putExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false);
        return intent;
    }

    public static Intent createIntent(Context context, int quality,
            int minLength, final int maxLength, boolean requirePasswordToDecrypt, long challenge) {
        Intent intent = ChooseLockPassword.createIntent(context, quality, minLength, maxLength,
                requirePasswordToDecrypt, challenge);
        intent.setClass(context, SetupChooseLockPassword.class);
        intent.putExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false);
        return intent;
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SetupChooseLockPasswordFragment.class.getName().equals(fragmentName);
    }

    @Override
    /* package */ Class<? extends Fragment> getFragmentClass() {
        return SetupChooseLockPasswordFragment.class;
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        resid = SetupWizardUtils.getTheme(getIntent());
        super.onApplyThemeResource(theme, resid, first);
    }

    public static class SetupChooseLockPasswordFragment extends ChooseLockPasswordFragment
            implements NavigationBar.NavigationBarListener {

        private SetupWizardLayout mLayout;
        private NavigationBar mNavigationBar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mLayout = (SetupWizardLayout) inflater.inflate(
                    R.layout.setup_choose_lock_password, container, false);
            mNavigationBar = mLayout.getNavigationBar();
            mNavigationBar.setNavigationBarListener(this);
            return mLayout;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            SystemBarHelper.setImeInsetView(mLayout);
            SetupWizardUtils.setImmersiveMode(getActivity());
            mLayout.setHeaderText(getActivity().getTitle());
        }

        @Override
        protected Intent getRedactionInterstitialIntent(Context context) {
            return null;
        }

        @Override
        protected void setNextEnabled(boolean enabled) {
            mNavigationBar.getNextButton().setEnabled(enabled);
        }

        @Override
        protected void setNextText(int text) {
            mNavigationBar.getNextButton().setText(text);
        }

        @Override
        public void onNavigateBack() {
            final Activity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        }

        @Override
        public void onNavigateNext() {
            handleNext();
        }
    }
}
