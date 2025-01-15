package com.alinesno.infra.ops.watcher.scheduler.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.scheduler.entity.SecretsEntity;

import java.util.Map;

/**
 * CredentialService接口
 */
public interface ISecretsService extends IBaseService<SecretsEntity> {

    /**
     * 查询返回密钥值
     * @return
     */
    Map<String, String> secretMap();

}
