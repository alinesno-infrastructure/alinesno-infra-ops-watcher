/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alinesno.infra.ops.watcher.alert.plugins.webhook;


import java.util.Arrays;
import java.util.List;

import com.alinesno.infra.ops.watcher.alert.facade.AlertChannel;
import com.alinesno.infra.ops.watcher.alert.facade.AlertChannelFactory;
import com.alinesno.infra.ops.watcher.alert.facade.AlertInputTips;
import com.alinesno.infra.ops.watcher.alert.params.InputNumberParam;
import com.alinesno.infra.ops.watcher.alert.params.InputParam;
import com.alinesno.infra.ops.watcher.alert.params.PluginParams;
import com.alinesno.infra.ops.watcher.alert.params.base.DataType;
import com.alinesno.infra.ops.watcher.alert.params.base.Validate;

//@AutoService(AlertChannelFactory.class)
public final class HttpAlertChannelFactory implements AlertChannelFactory {

    @Override
    public String name() {
        return "Http";
    }

    @Override
    public List<PluginParams> params() {

        InputParam url = InputParam.newBuilder(HttpAlertConstants.NAME_URL, HttpAlertConstants.URL)
                .setPlaceholder(AlertInputTips.URL.getMsg())
                .addValidate(Validate.newBuilder()
                        .setRequired(true)
                        .build())
                .build();

        InputParam headerParams =
                InputParam.newBuilder(HttpAlertConstants.NAME_HEADER_PARAMS, HttpAlertConstants.HEADER_PARAMS)
                        .setPlaceholder(AlertInputTips.HEADER.getMsg())
                        .addValidate(Validate.newBuilder()
                                .setRequired(true)
                                .build())
                        .build();

        InputParam bodyParams =
                InputParam.newBuilder(HttpAlertConstants.NAME_BODY_PARAMS, HttpAlertConstants.BODY_PARAMS)
                        .setPlaceholder(AlertInputTips.JSON_BODY.getMsg())
                        .addValidate(Validate.newBuilder()
                                .setRequired(false)
                                .build())
                        .build();

        InputParam contentField =
                InputParam.newBuilder(HttpAlertConstants.NAME_CONTENT_FIELD, HttpAlertConstants.CONTENT_FIELD)
                        .setPlaceholder(AlertInputTips.FIELD_NAME.getMsg())
                        .addValidate(Validate.newBuilder()
                                .setRequired(true)
                                .build())
                        .build();

        InputParam requestType =
                InputParam.newBuilder(HttpAlertConstants.NAME_REQUEST_TYPE, HttpAlertConstants.REQUEST_TYPE)
                        .setPlaceholder(AlertInputTips.HTTP_METHOD.getMsg())
                        .addValidate(Validate.newBuilder()
                                .setRequired(true)
                                .build())
                        .build();

        InputNumberParam timeout =
                InputNumberParam.newBuilder(HttpAlertConstants.NAME_TIMEOUT, HttpAlertConstants.TIMEOUT)
                        .setValue(HttpAlertConstants.DEFAULT_TIMEOUT)
                        .addValidate(Validate.newBuilder()
                                .setType(DataType.NUMBER.getDataType())
                                .setRequired(false)
                                .build())
                        .build();

        return Arrays.asList(url, requestType, headerParams, bodyParams, contentField, timeout);
    }

    @Override
    public AlertChannel create() {
        return new HttpAlertChannel();
    }
}
