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

package com.alinesno.infra.ops.watcher.alert.plugins.wechat;


import com.alinesno.infra.ops.watcher.alert.facade.AlertChannel;
import com.alinesno.infra.ops.watcher.alert.facade.AlertData;
import com.alinesno.infra.ops.watcher.alert.facade.AlertInfo;
import com.alinesno.infra.ops.watcher.alert.facade.AlertResult;

import java.util.Map;

public final class WeChatAlertChannel implements AlertChannel {

    @Override
    public AlertResult process(AlertInfo info) {
        AlertData alertData = info.getAlertData();
        Map<String, String> paramsMap = info.getAlertParams();
        if (null == paramsMap) {
            return new AlertResult("false", "we chat params is null");
        }
        return new WeChatSender(paramsMap).sendEnterpriseWeChat(alertData.getTitle(), alertData.getContent());

    }
}
