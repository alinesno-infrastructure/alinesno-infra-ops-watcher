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

package com.alinesno.infra.ops.watcher.alert.plugins.feishu;

import java.util.Arrays;
import java.util.List;

import com.alinesno.infra.ops.watcher.alert.facade.AlertChannel;
import com.alinesno.infra.ops.watcher.alert.facade.AlertChannelFactory;
import com.alinesno.infra.ops.watcher.alert.facade.AlertInputTips;
import com.alinesno.infra.ops.watcher.alert.params.InputNumberParam;
import com.alinesno.infra.ops.watcher.alert.params.InputParam;
import com.alinesno.infra.ops.watcher.alert.params.PluginParams;
import com.alinesno.infra.ops.watcher.alert.params.RadioParam;
import com.alinesno.infra.ops.watcher.alert.params.base.DataType;
import com.alinesno.infra.ops.watcher.alert.params.base.ParamsOptions;
import com.alinesno.infra.ops.watcher.alert.params.base.Validate;

import static com.alinesno.infra.ops.watcher.alert.constants.Constants.*;

//@AutoService(AlertChannelFactory.class)
public final class FeiShuAlertChannelFactory implements AlertChannelFactory {

    @Override
    public String name() {
        return "Feishu";
    }

    @Override
    public List<PluginParams> params() {
        InputParam webHookParam =
                InputParam.newBuilder(FeiShuParamsConstants.NAME_WEB_HOOK, FeiShuParamsConstants.WEB_HOOK)
                        .addValidate(Validate.newBuilder()
                                .setRequired(true)
                                .build())
                        .build();
        RadioParam isEnableProxy =
                RadioParam
                        .newBuilder(FeiShuParamsConstants.NAME_FEI_SHU_PROXY_ENABLE,
                                FeiShuParamsConstants.FEI_SHU_PROXY_ENABLE)
                        .addParamsOptions(new ParamsOptions(STRING_YES, STRING_TRUE, false))
                        .addParamsOptions(new ParamsOptions(STRING_NO, STRING_FALSE, false))
                        .setValue(STRING_TRUE)
                        .addValidate(Validate.newBuilder()
                                .setRequired(false)
                                .build())
                        .build();
        InputParam proxyParam =
                InputParam.newBuilder(FeiShuParamsConstants.NAME_FEI_SHU_PROXY, FeiShuParamsConstants.FEI_SHU_PROXY)
                        .addValidate(Validate.newBuilder()
                                .setRequired(false).build())
                        .build();

        InputNumberParam portParam =
                InputNumberParam.newBuilder(FeiShuParamsConstants.NAME_FEI_SHU_PORT, FeiShuParamsConstants.FEI_SHU_PORT)
                        .addValidate(Validate.newBuilder()
                                .setRequired(false)
                                .setType(DataType.NUMBER.getDataType())
                                .build())
                        .build();

        InputParam userParam =
                InputParam.newBuilder(FeiShuParamsConstants.NAME_FEI_SHU_USER, FeiShuParamsConstants.FEI_SHU_USER)
                        .addValidate(Validate.newBuilder()
                                .setRequired(false).build())
                        .build();
        InputParam passwordParam = InputParam
                .newBuilder(FeiShuParamsConstants.NAME_FEI_SHU_PASSWORD, FeiShuParamsConstants.FEI_SHU_PASSWORD)
                .setPlaceholder(AlertInputTips.PASSWORD.getMsg())
                .setType("password")
                .build();

        return Arrays.asList(webHookParam, isEnableProxy, proxyParam, portParam, userParam, passwordParam);

    }

    @Override
    public AlertChannel create() {
        return new FeiShuAlertChannel();
    }
}
