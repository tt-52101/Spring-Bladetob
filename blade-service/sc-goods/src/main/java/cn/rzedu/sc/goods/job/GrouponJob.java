package cn.rzedu.sc.goods.job;

import cn.rzedu.sc.goods.service.IGrouponGroupService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
public class GrouponJob {

    private IGrouponGroupService grouponGroupService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

//    /**
//     * 每隔一小时1次
//     */
//    @Scheduled(cron = "0 0 * * * ?")
//    public void autoCompleteGroup() {
//        System.out.println("----------自动成团 START  " + sdf.format(new Date()) + "--------");
//        grouponGroupService.autoCompleteGroupOneHourLeft();
//        System.out.println("----------自动成团 END  " + sdf.format(new Date()) + "--------");
//    }

    /**
     * 每隔5小时1次
     */
    @Scheduled(cron = "0 0 */5 * * ?")
    public void sendLackMessage() {
        System.out.println("----------发送拼团人数不足提醒 START  " + sdf.format(new Date()) + "--------");
        grouponGroupService.sendMessageToTimeLackGroup(68400L, 18000L);
        System.out.println("----------发送拼团人数不足提醒 END  " + sdf.format(new Date()) + "--------");
    }

}
