package com.alinesno.infra.ops.watcher.init;

import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.enums.AlertLevelEnum;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
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

    /**
     * 初始化数据的方法
     *
     * 本方法用于初始化相关数据，为后续操作做好准备。由于方法体内未包含具体实现代码，
     * 因此当前状态下该方法不执行任何操作。在后续开发中，应根据实际需求在此方法内添加
     * 相应的初始化逻辑。
     */
    public void initData() {

        if(messageService.count() == 0){
            initMultiScenarioPrometheusAlertData() ;
        }

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
