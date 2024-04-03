package com.gradu.infou.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document(indexName = "reward")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RewardDocument{
    @Id
    private String id;
    @Field(name = "@timestamp",  format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss", type = FieldType.Date)
    private LocalDateTime date;
    @Field(name="user_id", type = FieldType.Text)
    private String userId;
    @Field(name="message", type = FieldType.Text)
    private String message;
    @Field(name="reward", type = FieldType.Text)
    private Long reward;
    @Field(name="total_reward", type = FieldType.Text)
    private Long totalReward;

    public static RewardDocument toRewardDocument(String userId, String message, Long reward, Long totalReward){
        return RewardDocument.builder()
                .userId(userId)
                .message(message)
                .reward(reward)
                .totalReward(totalReward)
                .date(LocalDateTime.now(ZoneId.of("GMT")))
                .build();
    }
}
