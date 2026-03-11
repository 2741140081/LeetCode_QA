package com.marks.tools.kkplatform.service.impl;

import com.marks.tools.dbUtil.DatabasePool;
import com.marks.tools.kkplatform.entity.AEEInfoDAO;
import com.marks.tools.kkplatform.service.ArchiveEquipmentService;
import com.marks.utils.LogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArchiveEquipmentServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/11 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ArchiveEquipmentServiceImpl implements ArchiveEquipmentService {
    private static final String TABLE_NAME = "archive_equipment_enhancement_information";

    /**
     * 查询最小可强化装备
     * @return
     */
    @Override
    public AEEInfoDAO selectMinEnhanceableEquipment() {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE AE_Enhanced_Count < AE_Max_Enhancement_Count " +
                " ORDER BY AE_id ASC " +
                " LIMIT 1";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                AEEInfoDAO equipment = extractEquipmentFromResultSet(rs);
                LogUtil.info("查询到最小 ID 的可强化装备：ID=" + equipment.getAeId() +
                        ", 编号=" + equipment.getAeNumber() +
                        ", 名称=" + equipment.getAeName() +
                        ", 已强化=" + equipment.getAeEnhancedCount() +
                        "/" + equipment.getAeMaxEnhancementCount());
                return equipment;
            } else {
                LogUtil.warn("未找到可强化的装备（所有装备均已达到强化上限）");
                return null;
            }

        } catch (Exception e) {
            LogUtil.error("查询最小 ID 可强化装备失败：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AEEInfoDAO> selectAllEnhanceableEquipments() {
        List<AEEInfoDAO> equipments = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE AE_Enhanced_Count < AE_Max_Enhancement_Count " +
                " ORDER BY AE_id ASC";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                AEEInfoDAO equipment = extractEquipmentFromResultSet(rs);
                equipments.add(equipment);
            }

            LogUtil.info("查询到 " + equipments.size() + " 件可强化装备");

        } catch (Exception e) {
            LogUtil.error("查询可强化装备失败：" + e.getMessage());
            e.printStackTrace();
        }

        return equipments;
    }

    @Override
    public AEEInfoDAO findArchiveEquipmentById(int aeId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE AE_id = ?";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, aeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractEquipmentFromResultSet(rs);
                } else {
                    LogUtil.warn("未找到 ID 为 " + aeId + " 的装备记录");
                    return null;
                }
            }

        } catch (Exception e) {
            LogUtil.error("根据 ID 查询装备失败：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateArchiveEquipment(AEEInfoDAO aeeInfoDAO) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET AE_Number = ?, AE_Name = ?, AE_Img_Name = ?, " +
                " AE_Img_Path = ?, AE_Page_Number = ?, AE_Enhanced_Count = ?, " +
                " AE_Max_Enhancement_Count = ?, AE_Description = ? " +
                " WHERE AE_id = ?";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, aeeInfoDAO.getAeNumber());
            pstmt.setString(2, aeeInfoDAO.getAeName());
            pstmt.setString(3, aeeInfoDAO.getAeImgName());
            pstmt.setString(4, aeeInfoDAO.getAeImgPath());
            pstmt.setInt(5, aeeInfoDAO.getAePageNumber());
            pstmt.setInt(6, aeeInfoDAO.getAeEnhancedCount());
            pstmt.setInt(7, aeeInfoDAO.getAeMaxEnhancementCount());
            pstmt.setString(8, aeeInfoDAO.getAeDescription());
            pstmt.setInt(9, aeeInfoDAO.getAeId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                LogUtil.info("更新装备信息成功，影响行数：" + rows);
                return true;
            } else {
                LogUtil.error("更新装备信息失败，未找到对应记录");
                return false;
            }

        } catch (Exception e) {
            LogUtil.error("更新装备信息异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int insertArchiveEquipment(AEEInfoDAO aeeInfoDAO) {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (AE_Number, AE_Name, AE_Img_Name, AE_Img_Path, " +
                " AE_Page_Number, AE_Enhanced_Count, AE_Max_Enhancement_Count, " +
                " AE_Description) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, aeeInfoDAO.getAeNumber());
            pstmt.setString(2, aeeInfoDAO.getAeName());
            pstmt.setString(3, aeeInfoDAO.getAeImgName());
            pstmt.setString(4, aeeInfoDAO.getAeImgPath());
            pstmt.setInt(5, aeeInfoDAO.getAePageNumber());
            pstmt.setInt(6, aeeInfoDAO.getAeEnhancedCount());
            pstmt.setInt(7, aeeInfoDAO.getAeMaxEnhancementCount());
            pstmt.setString(8, aeeInfoDAO.getAeDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        LogUtil.info("插入装备信息成功，生成的 ID: " + generatedId);
                        return generatedId;
                    }
                }
            }

            LogUtil.error("插入装备信息失败");
            return -1;

        } catch (Exception e) {
            LogUtil.error("插入装备信息异常：" + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean deleteArchiveEquipment(int aeId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE AE_id = ?";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, aeId);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                LogUtil.info("删除装备信息成功，影响行数：" + rows);
                return true;
            } else {
                LogUtil.error("删除装备信息失败，未找到对应记录");
                return false;
            }

        } catch (Exception e) {
            LogUtil.error("删除装备信息异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新装备的强化次数
     * @param aeId 装备 ID
     * @param newCount 新的强化次数
     * @return 是否成功
     */
    @Override
    public boolean updateEnhancedCount(int aeId, int newCount) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET AE_Enhanced_Count = ?, Update_Time = NOW() " +
                " WHERE AE_id = ?";

        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newCount);
            pstmt.setInt(2, aeId);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                LogUtil.info("数据库更新成功，影响行数：" + rows);
                return true;
            } else {
                LogUtil.error("数据库更新失败，未找到对应记录");
                return false;
            }

        } catch (Exception e) {
            LogUtil.error("更新数据库异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从 ResultSet 中提取装备信息
     * @param rs 结果集
     * @return AEEInfoDAO 对象
     * @throws SQLException SQL 异常
     */
    private AEEInfoDAO extractEquipmentFromResultSet(ResultSet rs) throws SQLException {
        AEEInfoDAO equipment = new AEEInfoDAO();
        equipment.setAeId(rs.getInt("AE_id"));
        equipment.setAeNumber(rs.getInt("AE_Number"));
        equipment.setAeName(rs.getString("AE_Name"));
        equipment.setAeImgName(rs.getString("AE_Img_Name"));
        equipment.setAeImgPath(rs.getString("AE_Img_Path"));
        equipment.setAePageNumber(rs.getInt("AE_Page_Number"));
        equipment.setAeEnhancedCount(rs.getInt("AE_Enhanced_Count"));
        equipment.setAeMaxEnhancementCount(rs.getInt("AE_Max_Enhancement_Count"));
        equipment.setUpdateTime(rs.getTimestamp("Update_Time"));
        equipment.setAeDescription(rs.getString("AE_Description"));
        return equipment;
    }
}
