package com.marks.kkPlatformGameAuto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物品信息实体类
 * 用于存储物品名称和数量
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInfo {

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品数量
     * 默认值为 0，表示不修改数量
     */
    @Builder.Default
    private Integer quantity = 0;

    /**
     * 判断是否需要修改数量
     * @return true 如果需要修改数量
     */
    public boolean shouldModifyQuantity() {
        return quantity != null && quantity > 0;
    }
}
