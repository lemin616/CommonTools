/*
 * Created by zhsheng26 on 16-10-18 上午8:49
 * Copyright (C) 2016, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lemin.commontools.listener;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;


public interface IActionItem {
    void setItemTitle(@NonNull CharSequence itemTitle);

    void setItemIcon(@DrawableRes int drawableRes);

    @NonNull
    CharSequence getItemTitle();

    @DrawableRes
    int getItemIcon();

}
