package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.KPIMaster;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class KPIMasterConnector {

    public static KPIMaster getKPIMaster(Integer masterID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        KPIMaster kpiMaster = new KPIMaster();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpimaster WHERE KPI_MASTER_ID=?");
            preparedStatement.setInt(1, masterID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("KPI class not found.");
            } else {
                while (resultSet.next()) {
                    Integer recKPIId = resultSet.getInt("kpi_master_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    String recDescription = resultSet.getString("description");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCalcInstructions = resultSet.getInt("calc_instructions");
                    BigDecimal recScoreExtraordinary = resultSet.getBigDecimal("score_extraordinary");
                    BigDecimal recScoreGreat = resultSet.getBigDecimal("score_great");
                    BigDecimal recScoreWell = resultSet.getBigDecimal("score_well");
                    BigDecimal recScoreNeedsImprovement = resultSet.getBigDecimal("score_needs_improvement");
                    BigDecimal recScoreNotAcceptable = resultSet.getBigDecimal("score_not_acceptable");
                    BigDecimal recScorePoor = resultSet.getBigDecimal("score_poor");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    Boolean recRevScores = resultSet.getBoolean("reverse_scores");

                    kpiMaster = new KPIMaster(recKPIId, recKPICode, recDescription, recKPIClass, recCalcInstructions,
                            recScoreExtraordinary, recScoreGreat, recScoreWell, recScoreNeedsImprovement,
                            recScoreNotAcceptable, recScorePoor, recF1Name, recF2Name, recF3Name, recF4Name, recRevScores);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            kpiMaster = new KPIMaster();
        }

        return kpiMaster.getKPIMaster();
    }

    public static ArrayList<KPIMaster> getKPIMasters() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<KPIMaster> kpiMasterList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpimaster");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No KPIs found.");
            } else {
                while (resultSet.next()) {
                    Integer recKPIId = resultSet.getInt("kpi_master_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    String recDescription = resultSet.getString("description");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCalcInstructions = resultSet.getInt("calc_instructions");
                    BigDecimal recScoreExtraordinary = resultSet.getBigDecimal("score_extraordinary");
                    BigDecimal recScoreGreat = resultSet.getBigDecimal("score_great");
                    BigDecimal recScoreWell = resultSet.getBigDecimal("score_well");
                    BigDecimal recScoreNeedsImprovement = resultSet.getBigDecimal("score_needs_improvement");
                    BigDecimal recScoreNotAcceptable = resultSet.getBigDecimal("score_not_acceptable");
                    BigDecimal recScorePoor = resultSet.getBigDecimal("score_poor");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    Boolean recRevScores = resultSet.getBoolean("reverse_scores");

                    kpiMasterList.add(new KPIMaster(recKPIId, recKPICode, recDescription, recKPIClass, recCalcInstructions,
                            recScoreExtraordinary, recScoreGreat, recScoreWell, recScoreNeedsImprovement,
                            recScoreNotAcceptable, recScorePoor, recF1Name, recF2Name, recF3Name, recF4Name, recRevScores));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            kpiMasterList.add(new KPIMaster());
        }

        return kpiMasterList;
    }

    public static ArrayList<KPIMaster> getKPIMasters(Integer classID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<KPIMaster> kpiMasterList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpimaster WHERE KPI_CLASS=?");
            preparedStatement.setInt(1, classID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No KPIs found.");
            } else {
                while (resultSet.next()) {
                    Integer recKPIId = resultSet.getInt("kpi_master_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    String recDescription = resultSet.getString("description");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCalcInstructions = resultSet.getInt("calc_instructions");
                    BigDecimal recScoreExtraordinary = resultSet.getBigDecimal("score_extraordinary");
                    BigDecimal recScoreGreat = resultSet.getBigDecimal("score_great");
                    BigDecimal recScoreWell = resultSet.getBigDecimal("score_well");
                    BigDecimal recScoreNeedsImprovement = resultSet.getBigDecimal("score_needs_improvement");
                    BigDecimal recScoreNotAcceptable = resultSet.getBigDecimal("score_not_acceptable");
                    BigDecimal recScorePoor = resultSet.getBigDecimal("score_poor");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    Boolean recRevScores = resultSet.getBoolean("reverse_scores");

                    kpiMasterList.add(new KPIMaster(recKPIId, recKPICode, recDescription, recKPIClass, recCalcInstructions,
                            recScoreExtraordinary, recScoreGreat, recScoreWell, recScoreNeedsImprovement,
                            recScoreNotAcceptable, recScorePoor, recF1Name, recF2Name, recF3Name, recF4Name, recRevScores));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            kpiMasterList.add(new KPIMaster());
        }

        return kpiMasterList;
    }

    public static KPIMaster insertKPIMaster(KPIMaster kpiMaster) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement latestAdditionStatement = null;
        ResultSet resultSet = null;
        KPIMaster newKPIMaster = new KPIMaster();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO tblkpimaster " +
                    "SET kpi_code=?, description=?, kpi_class=?, calc_instructions=?, " +
                    "SCORE_EXTRAORDINARY=?, SCORE_GREAT=?, SCORE_WELL=?, SCORE_NEEDS_IMPROVEMENT=?, " +
                    "SCORE_NOT_ACCEPTABLE=?, SCORE_POOR=?, F1_NAME=?, F2_NAME=?, F3_NAME=?, F4_NAME=?, REVERSE_SCORES=?");
            preparedStatement.setString(1, kpiMaster.getKpi_code());
            preparedStatement.setString(2, kpiMaster.getDescription());
            preparedStatement.setInt(3, kpiMaster.getKpi_class());
            preparedStatement.setInt(4, kpiMaster.getCalc_instructions());
            preparedStatement.setBigDecimal(5, kpiMaster.getScore_extraordinary());
            preparedStatement.setBigDecimal(6, kpiMaster.getScore_great());
            preparedStatement.setBigDecimal(7, kpiMaster.getScore_well());
            preparedStatement.setBigDecimal(8, kpiMaster.getScore_needs_improvement());
            preparedStatement.setBigDecimal(9, kpiMaster.getScore_not_acceptable());
            preparedStatement.setBigDecimal(10, kpiMaster.getScore_poor());
            preparedStatement.setString(11, kpiMaster.getF1_name());
            preparedStatement.setString(12, kpiMaster.getF2_name());
            preparedStatement.setString(13, kpiMaster.getF3_name());
            preparedStatement.setString(14, kpiMaster.getF4_name());
            preparedStatement.setBoolean(15, kpiMaster.getReverse_scores());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpimaster ORDER BY KPI_MASTER_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("KPI class not found.");
            } else {
                while (resultSet.next()) {
                    Integer recKPIId = resultSet.getInt("kpi_master_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    String recDescription = resultSet.getString("description");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCalcInstructions = resultSet.getInt("calc_instructions");
                    BigDecimal recScoreExtraordinary = resultSet.getBigDecimal("score_extraordinary");
                    BigDecimal recScoreGreat = resultSet.getBigDecimal("score_great");
                    BigDecimal recScoreWell = resultSet.getBigDecimal("score_well");
                    BigDecimal recScoreNeedsImprovement = resultSet.getBigDecimal("score_needs_improvement");
                    BigDecimal recScoreNotAcceptable = resultSet.getBigDecimal("score_not_acceptable");
                    BigDecimal recScorePoor = resultSet.getBigDecimal("score_poor");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    Boolean recRevScores = resultSet.getBoolean("reverse_scores");

                    newKPIMaster = new KPIMaster(recKPIId, recKPICode, recDescription, recKPIClass, recCalcInstructions,
                            recScoreExtraordinary, recScoreGreat, recScoreWell, recScoreNeedsImprovement,
                            recScoreNotAcceptable, recScorePoor, recF1Name, recF2Name, recF3Name, recF4Name, recRevScores);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newKPIMaster = new KPIMaster();
        }

        return newKPIMaster.getKPIMaster();
    }

    public static Boolean updateKPIMaster(KPIMaster kpiMaster) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblkpimaster " +
                    "SET kpi_code=?, description=?, kpi_class=?, calc_instructions=?, " +
                    "SCORE_EXTRAORDINARY=?, SCORE_GREAT=?, SCORE_WELL=?, SCORE_NEEDS_IMPROVEMENT=?, " +
                    "SCORE_NOT_ACCEPTABLE=?, SCORE_POOR=?, F1_NAME=?, F2_NAME=?, " +
                    "F3_NAME=?, F4_NAME=?, REVERSE_SCORES=? WHERE kpi_master_id=?");
            preparedStatement.setString(1, kpiMaster.getKpi_code());
            preparedStatement.setString(2, kpiMaster.getDescription());
            preparedStatement.setInt(3, kpiMaster.getKpi_class());
            preparedStatement.setInt(4, kpiMaster.getCalc_instructions());
            preparedStatement.setBigDecimal(5, kpiMaster.getScore_extraordinary());
            preparedStatement.setBigDecimal(6, kpiMaster.getScore_great());
            preparedStatement.setBigDecimal(7, kpiMaster.getScore_well());
            preparedStatement.setBigDecimal(8, kpiMaster.getScore_needs_improvement());
            preparedStatement.setBigDecimal(9, kpiMaster.getScore_not_acceptable());
            preparedStatement.setBigDecimal(10, kpiMaster.getScore_poor());
            preparedStatement.setString(11, kpiMaster.getF1_name());
            preparedStatement.setString(12, kpiMaster.getF2_name());
            preparedStatement.setString(13, kpiMaster.getF3_name());
            preparedStatement.setString(14, kpiMaster.getF4_name());
            preparedStatement.setBoolean(15, kpiMaster.getReverse_scores());
            preparedStatement.setInt(16, kpiMaster.getKpi_master_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteKPIMaster(Integer masterID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM tblkpimaster WHERE kpi_master_id=?");
            preparedStatement.setInt(1, masterID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }
}
