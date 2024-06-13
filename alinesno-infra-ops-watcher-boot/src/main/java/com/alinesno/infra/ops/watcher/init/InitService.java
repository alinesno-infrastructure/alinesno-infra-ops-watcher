package com.alinesno.infra.ops.watcher.init;

import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.entity.AlertTemplateEntity;
import com.alinesno.infra.ops.watcher.enums.AlertChannelEnum;
import com.alinesno.infra.ops.watcher.enums.AlertLevelEnum;
import com.alinesno.infra.ops.watcher.service.IAlertChannelParamService;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import com.alinesno.infra.ops.watcher.service.IAlertTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 初始化数据
 */
@Slf4j
@Service
public class InitService {

    @Autowired
    private IAlertMessageService messageService ;

    @Autowired
    private IAlertTemplateService templateService ;

    /**
     * 初始化数据的方法
     *
     * 本方法用于初始化相关数据，为后续操作做好准备。由于方法体内未包含具体实现代码，
     * 因此当前状态下该方法不执行任何操作。在后续开发中，应根据实际需求在此方法内添加
     * 相应的初始化逻辑。
     */
    public void initData() {

        // 初始化模拟消息
        if(messageService.count() == 0){
            initMultiScenarioPrometheusAlertData() ;
        }

        // 初始化模板消息
        if(templateService.count() == 0){
            templateService.saveBatch(this.generateRandomDescription())  ;
        }

    }

    private List<AlertTemplateEntity> generateRandomDescription() {

        // 初始化包括多种异常类型的通知模板
        List<AlertTemplateEntity> alertTemplates = new ArrayList<>();

        // 内存异常通知模板
        AlertTemplateEntity memoryAlertTemplate = new AlertTemplateEntity();
        memoryAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        memoryAlertTemplate.setAlertContentTemplateEn("Memory Exception Alert");
        memoryAlertTemplate.setAlertContentTemplateCn("内存异常通知：系统检测到内存使用率异常，请及时处理。");
        memoryAlertTemplate.setAlertMethod(AlertChannelEnum.EMAIL.getCode());
        memoryAlertTemplate.setRecipient("admin@example.com");
        memoryAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(memoryAlertTemplate);

        // 应用运行异常通知模板
        AlertTemplateEntity appAlertTemplate = new AlertTemplateEntity();
        appAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        appAlertTemplate.setAlertContentTemplateEn("Application Exception Alert");
        appAlertTemplate.setAlertContentTemplateCn("应用运行异常通知：系统监测到应用出现异常，请立即处理。");
        appAlertTemplate.setAlertMethod(AlertChannelEnum.ALIYUN_SMS.getCode());
        appAlertTemplate.setRecipient("1234567890");
        appAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(appAlertTemplate);

        // 服务器异常通知模板
        AlertTemplateEntity serverAlertTemplate = new AlertTemplateEntity();
        serverAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        serverAlertTemplate.setAlertContentTemplateEn("Server Exception Alert");
        serverAlertTemplate.setAlertContentTemplateCn("服务器异常通知：系统监测到服务器出现异常，请尽快处理。");
        serverAlertTemplate.setAlertMethod(AlertChannelEnum.ALIYUN_SMS.getCode());
        serverAlertTemplate.setRecipient("555-123-4567");
        serverAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(serverAlertTemplate);

        // 网络异常通知模板
        AlertTemplateEntity networkAlertTemplate = new AlertTemplateEntity();
        networkAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        networkAlertTemplate.setAlertContentTemplateEn("Network Exception Alert");
        networkAlertTemplate.setAlertContentTemplateCn("网络异常通知：系统监测到网络连接异常，请查看并处理。");
        networkAlertTemplate.setAlertMethod(AlertChannelEnum.ALIYUN_SMS.getCode());
        networkAlertTemplate.setRecipient("9876543210");
        networkAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(networkAlertTemplate);

        // 安全攻击异常通知模板
        AlertTemplateEntity securityAlertTemplate = new AlertTemplateEntity();
        securityAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        securityAlertTemplate.setAlertContentTemplateEn("Security Attack Alert");
        securityAlertTemplate.setAlertContentTemplateCn("安全攻击异常通知：系统检测到安全攻击行为，请立即采取措施。");
        securityAlertTemplate.setAlertMethod(AlertChannelEnum.EMAIL.getCode());
        securityAlertTemplate.setRecipient("security@example.com");
        securityAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(securityAlertTemplate);

        // CPU负载过高异常通知模板
        AlertTemplateEntity cpuAlertTemplate = new AlertTemplateEntity();
        cpuAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        cpuAlertTemplate.setAlertContentTemplateEn("High CPU Load Alert");
        cpuAlertTemplate.setAlertContentTemplateCn("CPU负载过高通知：系统监测到CPU负载异常，请注意处理。");
        cpuAlertTemplate.setAlertMethod(AlertChannelEnum.ALIYUN_SMS.getCode());
        cpuAlertTemplate.setRecipient("1112223333");
        cpuAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(cpuAlertTemplate);

        // 数据库请求异常通知模板
        AlertTemplateEntity dbAlertTemplate = new AlertTemplateEntity();
        dbAlertTemplate.setAlertLevel(AlertLevelEnum.CRITICAL.getCode());
        dbAlertTemplate.setAlertContentTemplateEn("Database Request Exception Alert");
        dbAlertTemplate.setAlertContentTemplateCn("数据库请求异常通知：系统监测到数据库请求异常，请检查处理。");
        dbAlertTemplate.setAlertMethod(AlertChannelEnum.EMAIL.getCode());
        dbAlertTemplate.setRecipient("dba@example.com");
        dbAlertTemplate.setEffectiveTime(LocalDateTime.now());
        alertTemplates.add(dbAlertTemplate);

        // 打印所有消息模板信息
        for (AlertTemplateEntity alertTemplate : alertTemplates) {
            System.out.println("告警级别: " + alertTemplate.getAlertLevel());
            System.out.println("英文通知模板: " + alertTemplate.getAlertContentTemplateEn());
            System.out.println("中文通知模板: " + alertTemplate.getAlertContentTemplateCn());
            System.out.println("告警方式: " + alertTemplate.getAlertMethod());
            System.out.println("接收人: " + alertTemplate.getRecipient());
            System.out.println("生效时间: " + alertTemplate.getEffectiveTime());
            System.out.println("--------------------------------------");
        }
        
       return alertTemplates ; 

    }

    public void initMultiScenarioPrometheusAlertData() {
        List<AlertMessageEntity> messageList = new ArrayList<>();

        Random random = new Random();

        Set<String> usedDescriptions = new HashSet<>();

        for (int i = 1; i <= 20; i++) {
            AlertMessageEntity alert = new AlertMessageEntity();
            alert.setSource("实例_" + i);

            // 随机选择告警级别
            int levelIndex = random.nextInt(3); // 0: 严重, 1: 警告, 2: 信息
            switch (levelIndex) {
                case 0 -> alert.setLevel(AlertLevelEnum.CRITICAL.getCode());
                case 1 -> alert.setLevel(AlertLevelEnum.EMERGENCY.getCode());
                case 2 -> alert.setLevel(AlertLevelEnum.IMPORTANT.getCode());
            }

            // 生成唯一的描述信息
            String description = generateRandomDescription(random.nextInt(7)); // 0-6 对应不同的异常场景
            usedDescriptions.add(description);

            alert.setDescription(description);
            alert.setTimestamp(LocalDateTime.now().minusMinutes(random.nextInt(60))); // 随机时间戳，范围在过去一小时内
            messageList.add(alert);
        }

        // 保存模拟的异常信息到数据库
        messageService.saveBatch(messageList);

        // 打印异常信息
        messageList.forEach(System.out::println);
    }

    private String generateRandomDescription(int scenario) {
        // 根据不同的异常场景生成描述信息
        return switch (scenario) {
            case 0 -> "服务器CPU负载过高，可能导致系统性能下降";
            case 1 -> "内存使用超出阈值，需及时释放资源";
            case 2 -> "应用程序发生Java空指针异常，需要排查代码";
            case 3 -> "处理过程中抛出一般异常，可能影响业务流程";
            case 4 -> "系统中检测到安全漏洞，可能存在安全风险";
            case 5 -> "网络连接超时，影响数据传输";
            case 6 -> "数据库连接异常，需检查数据库配置";
            default -> "在指标中检测到异常情况，需进一步分析处理：" + UUID.randomUUID().toString().substring(0, 6);
        };
    }


}
