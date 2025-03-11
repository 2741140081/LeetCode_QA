package com.marks.leetcode.daily_question;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/11 16:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LookUpCache {
    public static final String SYSTEM_ID_IMS = "IMS";
    public static final String SYSTEM_ID_DRG = "DRG";
    // [start]  TW MOB Cust 18/12/07
    public static final String SYSTEM_ID_MOB = "MOB";
// [end]
    public static final String VIDEO_INFOLINE = "VIDEO_INFOLINE";

    //BOM2009097 Andy Ting
    public static final String SYSTEM_ID_IMSLTS = "IMSLTS";

    public static final String OCC_TYPE = "OCC_TYPE";
    public static final String OCC = "OCC";
    public static final String IND_TYPE = "IND_TYPE";
    public static final String IND = "IND";
    public static final String BUSINESS = "BUSINESS";
    public static final String INCOME = "INCOME";
    public static final String EDUCATION = "EDUCATION";
    public static final String PC_MODEL = "PC_MODEL";
    public static final String PC_OS_TYPE = "PC_OS_TYPE";
    public static final String MODEM = "MODEM";
    public static final String MODEM_SPEED = "MODEM_SPEED";
    public static final String MODEM_BRAND = "MODEM_BRAND";
    public static final String BROWSER = "BROWSER";
    public static final String ACCESS_MTHD = "ACCESS_MTHD";
    public static final String SCHOOL = "SCHOOL";
    public static final String CLASS = "CLASS";
    public static final String VIO_CONTACT_END_MM = "VIO_CONTACT_END_MM";
    public static final String VIO_CONTACT_END_YY = "VIO_CONTACT_END_YY";
    public static final String VIO_SUB_CTV = "VIO_SUB_CTV";
    public static final String ISP = "ISP";
    public static final String VIM_YOUNGER = "VIM_YOUNGER";
    public static final String VIM_NO_OF_PPL = "VIM_NO_OF_PPL";
    public static final String VIM_PARENTS = "VIM_PARENTS";
    public static final String VIM_SUB_CTV = "VIM_SUB_CTV";

    public static final String AREA = "AREA";
    public static final String DISTRICT = "DISTRICT";
    public static final String STREET_CAT = "STREET_CAT";
    public static final String SECTION = "SECTION";
    public static final String SECTION_NORELATION = "SECTION_NORELATION";
    public static final String IMS_EQUIPMENT = "IMS_EQUIPMENT";

    public static final String SAM_LOB = "SAM_LOB";
    public static final String SAM_TEAM = "SAM_TEAM";
    public static final String SAM_MODULE = "SAM_MODULE";
    public static final String SAM_USER_GROUP = "SAM_USER_GROUP";
    public static final String SAM_MOD_FUNC = "SAM_MOD_FUNC";

    public static final String DELICATED_SERVICES_GROUP = "DEDICATED_SERVICES_GROUP";
    public static final String DELICATED_SERVICES = "DEDICATED_SERVICES";

    public static final String ORDER_CLASSIFICATION="ORDER_CLASSIFICATION";
    public static final String ORDER_REASON="ORDER_REASON";

    public static final String SALES_TYPE = "SALES_TYPE";

    public static final String APPLICATION_METHOD = "APPLICATION_METHOD";

    public static final String SOURCE_CODE = "SOURCE_CODE";

    public static final String PAY_METHOD="PAY_METHOD";
    public static final String IMS_NOR_PAYMTHD="IMS_NOR_PAYMTHD";
    public static final String BILL_FREQ="BILL_FREQ";
    public static final String BILL_MEDIA="BILL_MEDIA";
    public static final String BILL_LANG="BILL_LANG";
    public static final String BILL_DAY="BILL_DAY";
    public static final String BANK_NAME="BANK_NAME";
    public static final String BANK_CODE="BANK_CODE";
    public static final String BRANCH_CODE="BRANCH_CODE";
    public static final String BRANCH_CODE_DESC="BRANCH_CODE_DESC";
    public static final String CLR_BANK="CLR_BANK";
    public static final String ID_DOC_TYPE="IDDOCTYP";
    public static final String ID_DOC_TYPE_TRANS="IDDOCTYP_TRANS";
    public static final String CUSTCATG = "CUSTCATG";
    public static final String TITLE = "TITLE";
    public static final String INDUSTRY_TYPE = "INDUSTRY_TYPE";
    public static final String INDUSTRY_SUBTYPE = "INDUSTRY_SUBTYPE";
    public static final String AUTOPAY_STATEMENT="AUTOPAY_STATEMENT";
    public static final String CREDIT_CARD_TYPE="CREDIT_CARD_TYPE";
    public static final String WRITTEN_APP_REQ="WRITTEN_APP_REQ";
    public static final String OPTIND_VALID_PERD="OPTIND_VALID_PERD";
    public static final String REQ_RESRC="REQ_RESRC";
    public static final String NUM_NGFL_LINE = "NUM_NGFL_LINE";

    public static final String VAS = "VAS";
    public static final String REF_VAS = "REF_VAS";
    public static final String BMQ_POV = "BMQ_POV";

    public static final String PROJ_CODE = "PROJ_CODE";

    public static final String B_BY_HKBR_DOMN = "B_BY_HKBR_DOMN";
    public static final String EXIST_PROG_CODE = "EXIST_PROG_CODE";
    public static final String NEW_PROG_CODE = "NEW_PROG_CODE";
    public static final String EXIST_PROG_CODE_REVERSE = "EXIST_PROG_CODE_REVERSE";
    public static final String NEW_PROG_CODE_REVERSE = "NEW_PROG_CODE_REVERSE";
    public static final String ROUTER_TYPE = "ROUTER_TYPE";
    public static final String FIREWALL_MODEL = "FIREWALL_MODEL";
    public static final String ASSOC_DOMAIN_TYPE = "ASSOC_DOMAIN_TYPE";
    public static final String ONE_TIME = "ONE_TIME";

    public static final String IMS_DISCON_REASON = "IMS_DISCON_REASON";

    public static final String BILL_DETAIL_IND = "BILL_DETAIL_IND";
    public static final String CREDIT_CLASS = "CREDIT_CLASS";

    public static final String ACCT_TYPE = "ACCT_TYPE";
    public static final String ACCT_SUB_TYPE = "ACCT_SUB_TYPE";
    public static final String SMS_BILL = "SMS_BILL";
    public static final String BILL_SORT_OPT = "BILL_SORT_OPT";
    public static final String PRINT_LVL_IND = "PRINT_LVL_IND";
    public static final String ACCT_STATUS = "ACCT_STATUS";
    public static final String TARIFF_GRP = "TARIFF_GRP";

    public static final String VAS_LOGIN = "VAS_LOGIN";

    public static final String ACC_CUST_CAT = "ACC_CUST_CAT";
    public static final String ACC_ID_TYPE = "ACC_ID_TYPE";
    public static final String ACC_PAY_METHOD = "ACC_PAY_METHOD";
    public static final String ACC_BILL_LANG = "ACC_BILL_LANG";
    public static final String ACC_BILL_MEDIA = "ACC_BILL_MEDIA";
    public static final String OC_APPLICATION_METHOD = "OC_APPLICATION_METHOD";
    public static final String OC_SOURCE_CODE = "OC_SOURCE_CODE";
    public static final String PC_PROD_TYPE = "PC_PROD_TYPE";
    public static final String ACCESS_KIT = "ACCESS_KIT";
    public static final String COMIT_HANDLING = "COMIT_HANDLING";
    public static final String COMIT_WAIVE_PEN_CHG = "COMIT_WAIVE_PEN_CHG";
    public static final String COM_OPTION = "COM_OPTION";
    public static final String LTS_SPECIAL_SERVICE = "LTS_SPECIAL_SERVICE";
    public static final String DIY_OPTION = "DIY_OPTION";
    public static final String EMAIL_DOMAIN = "EMAIL_DOMAIN";
    public static final String SH_OS = "SH_OS";
    public static final String SH_PLAN = "SH_PLAN";

    public static final String SEARCH_SRVTYPE = "SEARCH_SRVTYPE";
    public static final String SEARCH_SECLNTYPE = "SEARCH_SECLNTYPE";
    public static final String WIP_MSG_GEN = "WIP_MSG_GEN";
    public static final String WIP_MSG_ALLOW = "WIP_MSG_ALLOW";

    public static final String AUTOPAY_TERM_DESC = "AUTOPAY_TERM_DESC";
    public static final String AUTOPAY_TERM_CODE = "AUTOPAY_TERM_CODE";
    public static final String CREDIT_CARD_TERM_DESC = "CREDIT_CARD_TERM_DESC";
    public static final String CREDIT_CARD_TERM_CODE = "CREDIT_CARD_TERM_CODE";

    public static final String DN_STATUS_CODE = "DN_STATUS_CODE";
    public static final String THIRD_PARTY_PROVIDER = "3_PARTY_PROVIDER";

    public static final String LTS_DELAY_REASON = "LTS_DELAY_REASON";

    //BOM2010066
    public static final String GIFT_ITEM_B2B = "GIFT_ITEM_B2B";
    public static final String GIFT_ITEM_B2C = "GIFT_ITEM_B2C";
    //BOM2010066 - end
    

    //for imsboss lookup
    private static final String STB_TYPE = "stb_type";
    private static final String VI_STB_TYPE = "vi_stb_type";
    private static final String VISTB_NOT_MANUAL = "VISTB_NOT_MANUAL";
    private static final String FIBCOIND = "fibcoind";
    private static final String DOMAIN = "domain";
    private static final String NOP_SRDATE_LOWER_LIMIT = "NOP_SRDATE_LOWER_LIMIT";
    private static final String PCD_SRDATE_UPPER_LIMIT = "PCD_SRDATE_UPPER_LIMIT";
    private static final String APPL_DATE_LIMIT = "APPL_DATE_LIMIT";
    private static final String PCD_SRDATE_APPDATE_GAP_M = "PCD_SRDATE_APPDATE_GAP_M";
    private static final String PCD_SRDATE_APPDATE_GAP_D = "PCD_SRDATE_APPDATE_GAP_D";
    private static final String NO_NEED_RESERVE_VI_STB_DATE = "NO_NEED_RESERVE_VI_STB_DATE_NOP";
    private static final String ON_SITE_VISIT_LOWER_LIMIT = "ON_SITE_VISIT_LOWER_LIMIT_CCS";
    private static final String BILLPRD = "billprd_CCS";
    private static final String B2B_RENEWAL_ENABLE = "B2B_RENEWAL_ENABLE_CCS";
    private static final String UAMS_IP_1 = "UAMS_IP_1_UAMS";
    private static final String UAMS_IP_2 = "UAMS_IP_2_UAMS";
    private static final String UAMS_URL_1 = "UAMS_URL_1_UAMS";
    private static final String UAMS_URL_2 = "UAMS_URL_2_UAMS";
    private static final String UAMS_BOSS_RETRY_CNT = "UAMS_BOSS_RETRY_CNT_UAMS";
    private static final String CVI_DEFAULT_CCC = "CVI_DEFAULT_CCC_NOP";
    private static final String VI_URL = "VI_URL_NOP";
    private static final String PENDING_REQUEST = "pendreq";
    private static final String PENDING_REQUEST_TEXT = "pendreq_text";
    private static final String IMS_ASSO_DOMAIN = "AssoDomnType";
    private static final String IMS_DOMN_FLEX_COM_IND = "FlexComInd";
    private static final String L2JOB_CODE_DESC = "L2JOB_CODE_DESC";
    //Mob customer Taylor 28/12/07
    private static final String Customer_Tier = "CUST_TIER";
    private static final String Customer_Tier_CCM = "CUST_TIER_CCM"; // Peter Wu 10/1/2012
    private static final String NATIONALITY = "NATIONALITY";
    private static final String Server_Ind = "SERVER_IND";
    private static final String Credit_Fcs = "CREDIT_FCS";
//end

    //Mob Service (Subscriber) 
    private static final String SMS_LANG = "SMS_LANG";
    private static final String IVRS_LANG = "IVRS_LANG";
    private static final String STAFF_SPONSOR = "STAFF_SPONSOR";
    private static final String EXTERNAL_SPONSOR = "EXTERNAL_SPONSOR";
    private static final String MNP_CUT_WINDOW = "MNP_CUT_WINDOW";
    private static final String SERVICE_STATUS = "SERVICE_STATUS";
    private static final String MOB_BRAND = "MOB_BRAND";
    private static final String MOB_SIM_TYPE = "MOB_SIM_TYPE";

    public static final String THIRD_PARTY_IND ="THIRD_PARTY_IND";
    public static final String MOB_OFFER_TYPE = "MOB_OFFER_TYPE";
    public static final String PPOS_PICKUP_STS = "PPOS_PICKUP_STS";

    //Mob Refund
    private static final String REFUND_OPT = "REFUND_OPT";
    private static final String REFUND_REASON = "REFUND_REASON";
    private static final String REFUND_REA_SUB = "REFUND_REA_SUB";
    private static final String REFUND_MODE = "REFUND_MODE";

    private static final String REFUND_CB_OPT = "REFUND_CB_OPT";
    private static final String REFUND_CB_MODE = "REFUND_CB_MODE";
    private static final String DEPOSIT_STATUS = "DEPOSIT_STATUS";

    //Fraud Management
    public static final String FRAUDMGT_ACTION = "FRAUDMGT_ACTION";
    public static final String FRAUDMGT_REASON = "FRAUDMGT_REASON";
    public static final String FRAUDMGT_MESSAGE = "FRAUDMGT_MESSAGE";
    public static final String FRAUDMGT_MESSAGECD = "FRAUDMGT_MESSAGECD";

    private static final String SMARTLOGIN_URL = "smartLoginUrl";
    private static final String SMARTLOGIN_UID = "smartLoginUid";
    private static final String SMARTLOGIN_PWD = "smartLoginPwd";
    private static final String SMARTLOGIN_QTY = "smartLoginQty";
    private static final String SL_QTY = "SLQty";
    private static final String DEVICE_VAS = "DEVICE_VAS";

    // Align credit class and limit from Cust Category
    private static final String CREDCLAS = "CREDCLAS";
    private static final String CREDLMT = "CREDLMT";

    // Lookup BOM_COPY_PARM product 
    private static final String BOM_COPY_PARM = "BOM_COPY_PARM";
    private static final String OVERRIDE_VAS_VALUE = "OVERRIDE_VAS_VALUE";
    private static final String PARM_A_MANDATORY = "PARM_A_MANDATORY";
    private static final String PARM_B_MANDATORY = "PARM_B_MANDATORY";
    private static final String PARM_C_MANDATORY = "PARM_C_MANDATORY";

    //Lookup SMS alert tickler description
    private static final String PROF_CHNG_TICKLR = "PROF_CHNG_TICKLR";

    // Lookup Octopus Device default amount 
    private static final String DEPOSIT_AMOUNT = "DEPOSIT_AMOUNT";

    private static final Map<String, String> lkgrcode_map = new HashMap<>();
    private static final Map<String, String> order_by_rowseqnb_map = new HashMap<>();

    public LookUpCache() {
        lkgrcode_map.put(OCC_TYPE, "occ_type");
        lkgrcode_map.put(OCC, "occupation");
        lkgrcode_map.put(IND_TYPE, "ind_type");
        lkgrcode_map.put(IND, "industry");
        lkgrcode_map.put(BUSINESS, "bus_nature");

        lkgrcode_map.put(INCOME, "income");
        lkgrcode_map.put(EDUCATION, "education");
        lkgrcode_map.put(PC_MODEL, "pc_model");
        lkgrcode_map.put(PC_OS_TYPE, "os_type");
        lkgrcode_map.put(MODEM, "mod_type");

        lkgrcode_map.put(MODEM_SPEED, "mod_speed");
        lkgrcode_map.put(MODEM_BRAND, "mod_brand");
        lkgrcode_map.put(BROWSER, "br_type");
        lkgrcode_map.put(ACCESS_MTHD, "acc_method");
        lkgrcode_map.put(SCHOOL, "school");

        lkgrcode_map.put(CLASS, "class");

        order_by_rowseqnb_map.put(VIO_CONTACT_END_MM, "vio_cont_end_mm");
        order_by_rowseqnb_map.put(VIO_CONTACT_END_YY, "vio_cont_end_yy");
        order_by_rowseqnb_map.put(VIO_SUB_CTV, "vio_sub_ctv");
        order_by_rowseqnb_map.put(ISP, "vio_isp");
        order_by_rowseqnb_map.put(VIM_YOUNGER, "vim_with_younger");

        order_by_rowseqnb_map.put(VIM_NO_OF_PPL, "vim_no_of_ppl");
        order_by_rowseqnb_map.put(VIM_PARENTS, "vim_with_parents");
        order_by_rowseqnb_map.put(VIM_SUB_CTV, "vim_sub_ctv");

    }

    private void getDBComboBoxItems(String pComboBoxType, String[] pInputKey, String systemId, boolean pAddCodeToDesc) {
        String sqlStatement = null;
        if (lkgrcode_map.containsKey(pComboBoxType)){
            sqlStatement = "SELECT lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" FROM DEMOLKUP WHERE obsolete = 'N' and lkgrcode = '" + lkgrcode_map.get(pComboBoxType) + "'";
        } else if (order_by_rowseqnb_map.containsKey(pComboBoxType)){
            sqlStatement = "SELECT lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" FROM DEMOLKUP WHERE obsolete = 'N' and lkgrcode = '"+ order_by_rowseqnb_map.get(pComboBoxType) + "' order by rowseqnb ";
        } else if (pComboBoxType.equals(AREA)){
            sqlStatement = "SELECT areacd \"CODE\", trim(areadsc) \"DESCRIPTION\" FROM area UNION SELECT ' ', ' ' from dual order by 2";
        } else if (pComboBoxType.equals(DISTRICT)){
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "SELECT distrnum \"CODE\", dist_desc \"DESCRIPTION\" FROM b_distlkup WHERE area_cd = ? order by dist_desc";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "SELECT distrnb \"CODE\", distdsc \"DESCRIPTION\" FROM b_distlkup_mob WHERE areacd = ? order by distdsc";
            } else {
                sqlStatement = "SELECT distrnb \"CODE\", distdsc \"DESCRIPTION\" FROM distlkup WHERE areacd = ? order by distdsc";
            }
        } else if (pComboBoxType.equals(STREET_CAT)){
            sqlStatement = "SELECT stcatgcd \"CODE\", stcatdsc \"DESCRIPTION\" FROM stcatlu order by 2";
        } else if (pComboBoxType.equals(SECTION)){
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "SELECT sect_cd \"CODE\", sect_desc \"DESCRIPTION\" FROM b_section WHERE distrnum = to_number(?) and sect_desc is not null order by sect_desc";
            } else if (SYSTEM_ID_IMS.equals(systemId)){
                sqlStatement = "SELECT sectcd \"CODE\", sectdsc \"DESCRIPTION\" FROM section WHERE distrnb = to_number(?) and sectdsc is not null order by sectdsc";
            } else if (SYSTEM_ID_MOB.equals(systemId)){
                sqlStatement = "SELECT sect_cd \"CODE\", sect_desc \"DESCRIPTION\" FROM b_section WHERE distrnum = to_number(?) and sect_desc is not null order by sect_desc";
            } else {
                sqlStatement = "SELECT sectcd \"CODE\", sectdsc \"DESCRIPTION\" FROM section WHERE distrnb = to_number(?) and sectdsc is not null order by sectdsc";
            }
        } else if (pComboBoxType.equals(SECTION_NORELATION)){
            sqlStatement = "SELECT Distinct sect_cd \"CODE\", sect_desc \"DESCRIPTION\" FROM b_section WHERE sect_desc is not null order by sect_desc";
        } else if (pComboBoxType.equals(IMS_EQUIPMENT)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from lookup where obsolete = 'N' and lkgrcode = 'equipment' order by lkupdesc";
        } else if (pComboBoxType.equals(SAM_LOB)){
            sqlStatement = "SELECT BUSINESS_NAME \"CODE\", DESCRIPTION \"DESCRIPTION\" FROM B_SEC_LOB WHERE ENABLE_FLAG = 'Y' ORDER BY BUSINESS_NAME";
        } else if (pComboBoxType.equals(SAM_TEAM)){
            pAddCodeToDesc = true;
            sqlStatement = "SELECT TEAM_NAME \"CODE\", DESCRIPTION \"DESCRIPTION\" FROM B_SEC_TEAM WHERE ENABLE_FLAG = 'Y' ORDER BY TEAM_NAME";
        } else if (pComboBoxType.equals(SAM_MODULE)){
            sqlStatement = "SELECT MODULE_NAME \"CODE\", DESCRIPTION \"DESCRIPTION\" FROM B_SEC_MODULE WHERE ENABLE_FLAG = 'Y' ORDER BY MODULE_NAME";
        } else if (pComboBoxType.equals(SAM_USER_GROUP)){
            pAddCodeToDesc = true;
            sqlStatement = "SELECT GROUP_NAME \"CODE\", DESCRIPTION \"DESCRIPTION\" FROM B_SEC_GROUP ORDER BY GROUP_NAME";
        } else if (pComboBoxType.equals(SAM_MOD_FUNC)){
            sqlStatement = "SELECT DISTINCT FUNCTION \"CODE\", FUNCTION \"DESCRIPTION\" FROM B_SEC_FUNC_LOOKUP WHERE M_ID = (SELECT M_ID FROM B_SEC_MODULE WHERE MODULE_NAME = ?) AND ENABLE_FLAG = 'Y'";
        } else if (pComboBoxType.equals(DELICATED_SERVICES_GROUP)){
            //sqlStatement = "SELECT DISTINCT SSRV_CD \"CODE\", SSRV_GRP \"DESCRIPTION\" FROM B_SSRVTBL_LKUP order by ssrv_grp";
            sqlStatement = "SELECT DISTINCT SSRV_GRP \"CODE\", SSRV_GRP \"DESCRIPTION\" FROM B_SSRVTBL_LKUP";
        } else if (pComboBoxType.equals(DELICATED_SERVICES)){
            sqlStatement = "SELECT DISTINCT SSRV_GRP \"CODE\", SSRV_CD \"DESCRIPTION\" FROM B_SSRVTBL_LKUP ORDER BY SSRV_GRP, SSRV_CD";
        } else if (pComboBoxType.equals(ORDER_CLASSIFICATION)) {
            sqlStatement = "SELECT CLASSIFICATION_CD \"CODE\", CLASSIFICATION_DESC \"DESCRIPTION\" FROM B_ORD_CLASSIFICATION_LKUP WHERE ((LOB=' ') or (LOB = ?)) and ACTION_CD = ?";
        } else if (pComboBoxType.equals(ORDER_REASON)) {
            sqlStatement = "SELECT REASON_CD \"CODE\", REASON_DESC \"DESCRIPTION\" FROM B_ORD_REASON_LKUP WHERE ((LOB=' ') or (LOB = ?)) and ACTION_CD = ?";
        } else if (pComboBoxType.equals(SALES_TYPE)) {
            sqlStatement = "SELECT DISTINCT CODE \"CODE\", DESCR \"DESCRIPTION\" FROM B_SALESTYPE WHERE OBSOLETE = 'N' and  DOMNTYPE = ? ";
        } else if (pComboBoxType.equals(APPLICATION_METHOD)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_STATUS= 'A' and BOM_GRP_ID = 'APPLMTHD' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
        } else if (pComboBoxType.equals(SOURCE_CODE)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP B, B_APPLN_SRC A WHERE B.BOM_STATUS= 'A' and B.BOM_GRP_ID = 'SOURCE' and B.BOM_CODE = A.SRCCODE and A.APPLCODE = (SELECT BOM_CODE FROM B_LOOKUP WHERE BOM_STATUS = 'A' and BOM_GRP_ID='APPLMTHD' and BOM_DESC = ?) UNION SELECT ' ' code, ' ' description FROM dual order by 2";
        } else if (pComboBoxType.equals(PAY_METHOD)) {
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'PAYMTHD' and bom_status = 'A' union select ' ' code, ' ' description from dual order by 2";
            } else if (SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'PAYMTHD' and bom_status = 'A' union select ' ' code, ' ' description from dual order by 2";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'MOB_PAYMTHD' and bom_status = 'A' union select ' ' code, ' ' description from dual order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'PAYMTHD' and bom_status = 'A' union select ' ' code, ' ' description from dual order by 2";
            }

        } else if (pComboBoxType.equals(IMS_NOR_PAYMTHD)) {
            sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IMS_NOR_PAYMTHD' and bom_status = 'A' union select ' ' code, ' ' description from dual order by 2";
        } else if (pComboBoxType.equals(BILL_FREQ)) {
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'LTS_BILL_FREQ' and bom_status = 'A' ";
            } else {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'BILL_FREQ' and bom_status = 'A' ";
            }
        } else if (pComboBoxType.equals(BILL_MEDIA)) {
            if (SYSTEM_ID_DRG.equals(systemId))
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='LTS_BILL_MEDIA' and bom_status = 'A' ";
            else
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='BILL_MEDIA' and bom_status = 'A' ";
        } else if (pComboBoxType.equals(BILL_LANG)) {
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='LTS_LANGCD' and bom_status = 'A' ";
            } else if (SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='LANGCD' and bom_status = 'A' ";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='MOB_LANGCD' and bom_status = 'A' ";
            } else {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='LANGCD' and bom_status = 'A' ";
            }

        } else if (pComboBoxType.equals(BANK_NAME)) {
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_MOB.equals(systemId)) {
                String condition = "";
                if (pInputKey != null && pInputKey.length == 1)
                    condition = " where bank_status = ? ";
                sqlStatement = "SELECT bank_code \"CODE\", bank_name \"DESCRIPTION\" FROM b_bank " + condition + " order by bank_name asc";
            } else {
                sqlStatement = "SELECT bankcode \"CODE\", bankname \"DESCRIPTION\" FROM bankcode order by bankname asc";
            }
        } else if (pComboBoxType.equals(BANK_CODE)) {
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_MOB.equals(systemId)) {
                String condition = "";
                if (pInputKey != null && pInputKey.length == 1)
                    condition = " where bank_status = ? ";
                sqlStatement = "SELECT bank_code \"CODE\", bank_code \"DESCRIPTION\" FROM b_bank " + condition + " order by bank_code asc";
            } else {
                sqlStatement = "SELECT bankcode \"CODE\", bankcode \"DESCRIPTION\" FROM bankcode order by bankcode asc";
            }
        } else if (pComboBoxType.equals(BRANCH_CODE)) {
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_MOB.equals(systemId)) {
                String condition = "";
                if (pInputKey != null && pInputKey.length == 2)
                    condition = " and branch_status = ? ";
                sqlStatement = "SELECT branch_code \"CODE\", branch_code \"DESCRIPTION\" FROM b_branch WHERE bank_code = ? " + condition + " order by branch_code asc";
            } else {
                sqlStatement = "SELECT brchcode \"CODE\", brchcode \"DESCRIPTION\" FROM bankbrch where bankcode = ? order by brchcode asc";
            }
        } else if (pComboBoxType.equals(BRANCH_CODE_DESC)) {
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_MOB.equals(systemId)) {
                String condition = "";
                if (pInputKey != null && pInputKey.length == 2)
                    condition = " and branch_status = ? ";
                sqlStatement = "SELECT branch_code \"CODE\", branch_name \"DESCRIPTION\" FROM b_branch WHERE bank_code = ? " + condition + " order by branch_name asc";
            } else {
                sqlStatement = "SELECT brchcode \"CODE\", brchname  \"DESCRIPTION\" FROM bankbrch where bankcode = ? order by brchname asc";
            }
        } else if (pComboBoxType.equals(NUM_NGFL_LINE)){
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id='LINE_AND_SESSION' and bom_status = 'A' and bom_code = ? ";
        } else if (pComboBoxType.equals(ID_DOC_TYPE)) {
            if (SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and ims_grp_id = 'iddoctyp' and bom_status = 'A' ";
            } else if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and lts_grp_id = 'IDDOCTYP' and bom_status = 'A' ";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and m3g_grp_id = 'IDDOCTYP' and bom_status = 'A' ";
            } else if (SYSTEM_ID_IMSLTS.equals(systemId)) {
                //Andy Ting BOM2009097
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and (ims_grp_id = 'iddoctyp' or lts_grp_id = 'IDDOCTYP')  and bom_status = 'A' ";
            }
            else {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_status = 'A' ";
            }
        } else if (pComboBoxType.equals(ID_DOC_TYPE_TRANS)) {
            sqlStatement = "select bom_code \"CODE\", lts_code \"DESCRIPTION\" from b_lookup where bom_grp_id = 'IDDOCTYP' and lts_grp_id = 'IDDOCTYP' and bom_status = 'A' ";
        } else if (pComboBoxType.equals(CUSTCATG)) {
            if (systemId == null || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'CUSTCATG' and ims_grp_id = 'custcatg' and bom_status = 'A' ";
            } else if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'CUSTCATG' and lts_grp_id = 'custcatg' and bom_status = 'A' ";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                //TW MOB 22/01/08 
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'CUSTCATG' and lts_grp_id = 'custcatg' and bom_status = 'A' ";
            }else if (SYSTEM_ID_IMSLTS.equals(systemId)) {
                // Andy Ting, HK FOR BOM2009097
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'CUSTCATG' and (ims_grp_id = 'custcatg' or lts_grp_id = 'custcatg') and bom_status = 'A' ";
            }
        } else if (pComboBoxType.equals(TITLE)) {
            if (systemId == null || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'TITLE' and ims_grp_id = 'title' and bom_status = 'A' ";
            } else if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'TITLE' and lts_grp_id = 'TITLE' and bom_status = 'A' ";
            } else if (SYSTEM_ID_MOB.equals(systemId)){
                //TW MOB 22/01/08
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'TITLE' and lts_grp_id = 'TITLE' and bom_status = 'A' ";
            }
            else if (SYSTEM_ID_IMSLTS.equals(systemId)){
                //Andy Ting BOM2009097
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'TITLE' and ims_grp_id = 'title' and bom_status = 'A' ";
            }
        } else if (pComboBoxType.equals(INDUSTRY_TYPE)) {
            if (systemId == null || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_TYPE' and ims_grp_id = 'INDUSTRY_TYPE' and bom_status = 'A' ";
            }
            else if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_TYPE' and lts_grp_id = 'INDUSTRY_TYPE' and bom_status = 'A' ";
            }
            else if (SYSTEM_ID_MOB.equals(systemId)) {
                //TW MOB 22/01/08 
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_TYPE' and lts_grp_id = 'INDUSTRY_TYPE' and bom_status = 'A' ";
            }
            else if (SYSTEM_ID_IMSLTS.equals(systemId)) {
                //Andy Ting BOM2009097
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_TYPE' and (ims_grp_id = 'INDUSTRY_TYPE' or lts_grp_id = 'INDUSTRY_TYPE') and bom_status = 'A' ";
            }
        } else if (pComboBoxType.equals(INDUSTRY_SUBTYPE)) {
            if (systemId == null || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_SUBTYPE' and bom_status = 'A' and ims_grp_id ";
            }
            else if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_SUBTYPE' and bom_status = 'A' and lts_grp_id ";
            }
            else if (SYSTEM_ID_MOB.equals(systemId)) {
                //TW MOB 22/01/08 
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_SUBTYPE' and bom_status = 'A' and lts_grp_id ";
            }
            else if (SYSTEM_ID_IMSLTS.equals(systemId)) {
                //Andy Ting BOM2009097
                sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_grp_id = 'INDUSTRY_SUBTYPE' and bom_status = 'A' and lts_grp_id ";
            }
            if (pInputKey != null && pInputKey.length > 0) {
                sqlStatement = sqlStatement + "= ?";
            } else {
                sqlStatement = sqlStatement + "= 'dummy'";
            }
        } else if (pComboBoxType.equals(WRITTEN_APP_REQ)) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'WRITTEN_APP_REQ' and bom_status = 'A' ORDER BY 2";
        } else if (pComboBoxType.equals(OPTIND_VALID_PERD)) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'OPTIND_VALID_PERD' and bom_status = 'A' ORDER BY 2";
        } else if (pComboBoxType.equals(REQ_RESRC)) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'REQ_RESRC' and bom_status = 'A' ORDER BY 2";
        } else if (pComboBoxType.equals(AUTOPAY_STATEMENT)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where ims_grp_id='autopay_stmt' and bom_status = 'A' ";
        } else if (pComboBoxType.equals(CREDIT_CARD_TYPE) && systemId.equals(SYSTEM_ID_IMS)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() == 1)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and ims_grp_id='ccardtyp' and ims_code is not null" + condition;
        } else if (pComboBoxType.equals(CREDIT_CARD_TYPE) && systemId.equals(SYSTEM_ID_DRG)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and lts_grp_id='ccardtyp' and lts_code is not null";
        } else if (pComboBoxType.equals(CREDIT_CARD_TYPE) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and m3g_grp_id='ccardtyp' and m3g_code is not null";
        } else if (pComboBoxType.equals(VAS)){
            sqlStatement = "SELECT vas_type code, vas_type_desc description FROM VASLKUP UNION SELECT 'ALL' code, 'ALL' description FROM DUAL ORDER BY 1 ";
        } else if (pComboBoxType.equals(REF_VAS)){
            sqlStatement = "SELECT vas_type code, referral_vas_type description FROM VASLKUP order by 1";
        } else if (pComboBoxType.equals(BMQ_POV)){
            sqlStatement = "select bom_code code, bom_desc description from b_lookup where bom_grp_id = 'BMQ_POV' order by to_number(bom_code)";
        } else if (pComboBoxType.equals(PROJ_CODE)){
            sqlStatement = "SELECT project_cd code, project_cd description FROM b_iwc_project WHERE cust_level = 0 AND cust_num = ? AND system_id = ? order by project_cd";
        } else if (pComboBoxType.equals(EMAIL_DOMAIN)) {
            sqlStatement = "select domntype code, servname description from domain where domntype not in ('I','B','M','X','Y') and servname is not null";
        } else if (pComboBoxType.equals(B_BY_HKBR_DOMN)){
            sqlStatement = "SELECT b.domntype code, a.lkupdesc description FROM nesoplup a, domain b WHERE LKGRCODE='b_by_hkbr_domn'";
        } else if (pComboBoxType.equals(EXIST_PROG_CODE)){
            sqlStatement = "select progid code, nvl(pengdesc,'  ') description from pgmlkup where (vi_ind!='Y' and domntype = ? and (termdate is null or termdate >= trunc(sysdate))) or (domntype = ? and progid = ?) order by 1";
        } else if (pComboBoxType.equals(NEW_PROG_CODE)){
            sqlStatement = "select progid code, nvl(pengdesc,'  ') description from pgmlkup where (vi_ind!='Y' and domntype = ? and allow_pendchg = 'Y' and (termdate is null or termdate >= trunc(sysdate))) or (domntype = ? and progid = ?) order by 1";
        }
        else if (pComboBoxType.equals(EXIST_PROG_CODE_REVERSE)){
            //adopt from R1 ims.boss.maintvisit.JdlgChgMaintVisit.java prepareExistProgCode()
            sqlStatement = "select progid description, nvl(pengdesc,'  ') code from pgmlkup where (vi_ind!='Y' and domntype = ? and (termdate is null or termdate >= trunc(sysdate))) or (domntype = ? and progid = ?) order by 1";
        } else if (pComboBoxType.equals(NEW_PROG_CODE_REVERSE)){
            //adopt from R1 ims.boss.maintvisit.JdlgChgMaintVisit.java prepareNewProgCode()
            sqlStatement = "select progid description, nvl(pengdesc,'  ') code from pgmlkup where (vi_ind!='Y' and domntype = ? and allow_pendchg = 'Y' and (termdate is null or termdate >= trunc(sysdate))) or (domntype = ? and progid = ?) order by 1";
        } else if (pComboBoxType.equals(ROUTER_TYPE)){
            sqlStatement = "SELECT bom_code code, bom_desc description from b_lookup where bom_status = 'A' and bom_grp_id = 'ROUTER_TYPE' UNION SELECT ' ' code, ' ' description FROM dual";
        } else if (pComboBoxType.equals(FIREWALL_MODEL)){
            sqlStatement = "SELECT bom_code code, bom_desc description FROM b_lookup WHERE bom_status = 'A' and ims_grp_id='firewall' UNION SELECT ' ' code, ' ' description FROM dual";
        } else if (pComboBoxType.equals(ASSOC_DOMAIN_TYPE)){
            //adopt from R1 ims.boss.comm.util.BOSSLookup.java loadDomainLookup()
            sqlStatement = "select d.domntype code, lk.lkupdesc description from domain d, nesoplup lk where lk.lkgrcode='domn_asso' and lk.lkupcode=d.domntype and d.domntype not in ('I','B','M') ";
        } else if (pComboBoxType.equals(ONE_TIME)){
            //adopt from R1 ims.boss.maintvisit.AllOneTime.java getChrgCodeDesc()
            //parm list: sPrdId, sTrfHdrId, sLayNo, sApplDate * 8
            sqlStatement = "select a.prd_sht_desc, b.trf_hdr_bill_eng_desc FROM ims_prd a, ims_trf b WHERE a.prd_id = b.prd_id and b.prd_id = ? and b.trf_hdr_id = ? and lay_no = ? and pt_eff_dt <= to_date(?,'dd/mm/yyyy') and ( pt_inact_dt is null or pt_inact_dt >= to_date(?,'dd/mm/yyyy') ) and tc_eff_dt <= to_date(?,'dd/mm/yyyy') and ( tc_inact_dt is null or tc_inact_dt >= to_date(?,'dd/mm/yyyy') ) and th_eff_dt <= to_date(?,'dd/mm/yyyy') and ( th_inact_dt is null or th_inact_dt >= to_date(?,'dd/mm/yyyy') ) and td_eff_dt <= to_date(?,'dd/mm/yyyy') and ( td_inact_dt is null or td_inact_dt >= to_date(?,'dd/mm/yyyy') )";
        } else if (pComboBoxType.equals(IMS_DISCON_REASON)) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID= 'DISCON_REASON' AND BOM_STATUS= 'A' AND IMS_CODE IS NOT NULL ORDER BY BOM_DESC ASC";
        } else if (pComboBoxType.equals(BILL_DETAIL_IND)) {
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and lts_grp_id='bill_format' order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and ims_grp_id='bill_format'";
            }
        } else if (pComboBoxType.equals(CREDIT_CLASS)) {
            sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and ims_grp_id = 'credclas'";
        } else if (pComboBoxType.equals(ACCT_TYPE)){
            if (SYSTEM_ID_DRG.equals(systemId)) {
                sqlStatement = "SELECT bom_code code, bom_code description FROM b_lookup WHERE bom_status = 'A' and lts_grp_id='accttype' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else {
                sqlStatement = "SELECT bom_code code, bom_code description FROM b_lookup WHERE bom_status = 'A' and ims_grp_id='accttype' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            }
        } else if (pComboBoxType.equals(VAS_LOGIN)){
            sqlStatement = "SELECT 'ALL' code, 'ALL' description FROM DUAL union select service_mem_num code, c.domnname || ' / ' || login_id description from b_service_member_details_ims bsmdi, domain c where bsmdi.domain_type = c.domntype and service_id = ?";
        } else if (pComboBoxType.equals(ACC_CUST_CAT)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from lookup where lkgrcode='custcatg'";
        } else if (pComboBoxType.equals(ACC_ID_TYPE)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from lookup where lkgrcode='iddoctyp'";
        } else if (pComboBoxType.equals(ACC_PAY_METHOD)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from lookup where obsolete='N' and lkgrcode='paymthd'";
        } else if (pComboBoxType.equals(ACC_BILL_LANG)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from nesoplup where obsolete='N' and lkgrcode='langcd' and lkupcode='ENG'";
        } else if (pComboBoxType.equals(ACC_BILL_MEDIA)) {
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from nesoplup where obsolete='N' and lkgrcode='bill_media'";
        } else if (pComboBoxType.equals(OC_APPLICATION_METHOD)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_STATUS= 'A' and BOM_GRP_ID = 'APPLMTHD' ORDER BY DESCRIPTION";
        } else if (pComboBoxType.equals(OC_SOURCE_CODE)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP B, B_APPLN_SRC A WHERE B.BOM_STATUS= 'A' and B.BOM_GRP_ID = 'SOURCE' and B.BOM_CODE = A.SRCCODE and A.APPLCODE = (SELECT BOM_CODE FROM B_LOOKUP WHERE BOM_STATUS = 'A' and BOM_GRP_ID='APPLMTHD' and BOM_CODE = ?) ORDER BY DESCRIPTION";
        } else if (pComboBoxType.equals(PC_PROD_TYPE)) {
            sqlStatement = "select lookup_code \"CODE\", lookup_value \"DESCRIPTION\" FROM b_pc_lookup B WHERE B.lookup_type = 'PROD_TYPE' ORDER BY 2";
        } else if (pComboBoxType.equals(SH_OS)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup where bom_grp_id = 'SH_OS' and bom_status = 'A'";
        } else if (pComboBoxType.equals(SH_PLAN)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup where bom_grp_id = 'SH_PLAN' and bom_status = 'A'";
        } else if (pComboBoxType.equalsIgnoreCase(ACCESS_KIT)) {
            //  sqlStatement = "select itemcode \"CODE\", itmcddsc \"DESCRIPTION\" from neitmlu where obsolete='N' order by itmcddsc asc";
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'ACCESS_KIT_TYPE' and BOM_STATUS = 'A'";
        } else if (pComboBoxType.equalsIgnoreCase(COMIT_HANDLING)){
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_CODE \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'COMITHANDLING' ORDER BY 1";
        } else if (pComboBoxType.equalsIgnoreCase(COMIT_WAIVE_PEN_CHG)){
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_CODE \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'COMITWAIVEPENCHG' ORDER BY 1";
        } else if (pComboBoxType.equalsIgnoreCase(COM_OPTION)){
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'COM_OPTION' ORDER BY 1";
        } else if (pComboBoxType.equalsIgnoreCase(LTS_SPECIAL_SERVICE)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" from b_lookup where bom_grp_id = 'SSL_IND' and bom_status = 'A'";
        } else if (pComboBoxType.equalsIgnoreCase(DIY_OPTION)) {
            sqlStatement = "select BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM b_lookup WHERE bom_grp_id='DIY_OPT' and bom_status = 'A'";
        } else if (pComboBoxType.equals(ACCT_SUB_TYPE)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_code \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='ACCTSUBTYPE' UNION SELECT ' ' code, ' ' description FROM dual order by 2";

        } else if (pComboBoxType.equals(SMS_BILL)){
            sqlStatement = "SELECT lts_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='LTS_BILL_ENQ' order by 2";

        } else if (pComboBoxType.equals(BILL_SORT_OPT)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='LTS_SORT_OPT' order by 2";

        } else if (pComboBoxType.equals(SEARCH_SRVTYPE)){
            sqlStatement = "SELECT bom_code code, bom_desc description FROM b_lookup WHERE bom_grp_id='SEARCH_SRVTYPE' and bom_status = 'A' order by 2";
        } else if (pComboBoxType.equals(SEARCH_SECLNTYPE)){
            sqlStatement = "SELECT bom_code code, bom_desc description FROM b_lookup WHERE bom_grp_id='SEARCH_SECLNTYPE' and bom_status = 'A' order by 2";
        } else if (pComboBoxType.equals(WIP_MSG_GEN)){
            sqlStatement = "SELECT bom_code code, bom_desc description FROM b_lookup WHERE bom_grp_id='WIP_MSG_GEN' and bom_status = 'A' order by 2";
        } else if (pComboBoxType.equals(WIP_MSG_ALLOW)){
            sqlStatement = "SELECT bom_code code, bom_desc description FROM b_lookup WHERE bom_grp_id='WIP_MSG_ALLOW' and bom_status = 'A' order by 2";
        } else if (pComboBoxType.equals(AUTOPAY_TERM_DESC)){
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            }

        } else if (pComboBoxType.equals(AUTOPAY_TERM_CODE)){
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", lts_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", m3g_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", lts_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_AUTOPAY_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            }

        } else if (pComboBoxType.equals(DN_STATUS_CODE)){
            sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='DN_STATUS_CODE' order by 1";
        } else if (pComboBoxType.equals(THIRD_PARTY_PROVIDER)){
            sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='3_PARTY_PROVIDER' order by 1";
        } else if (pComboBoxType.equals(CREDIT_CARD_TERM_DESC)){
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            }

        } else if (pComboBoxType.equals(CREDIT_CARD_TERM_CODE)){
            if (SYSTEM_ID_DRG.equals(systemId) || SYSTEM_ID_IMS.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", lts_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else if (SYSTEM_ID_MOB.equals(systemId)) {
                sqlStatement = "Select bom_code \"CODE\", m3g_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            } else {
                sqlStatement = "Select bom_code \"CODE\", lts_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='LTS_CCARD_TERM' UNION SELECT ' ' code, ' ' description FROM dual order by 2";
            }

        } else if (pComboBoxType.equals(CLR_BANK)){
            sqlStatement = "Select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='CLR_BANK' order by 2";
        } else if (pComboBoxType.equals(BILL_DAY)){
            sqlStatement = "Select billdate \"CODE\", billdate \"DESCRIPTION\" from b_sys_billdate where systemid  = \'" + systemId + "\' order by 1 ";
        } else if (pComboBoxType.equals(PRINT_LVL_IND)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='LTS_PRINT_LVL' order by 2";

        } else if (pComboBoxType.equals(LTS_DELAY_REASON)){
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'DELAY_REASON' AND BOM_STATUS = 'A' ORDER BY BOM_DESC";
        } else if (pComboBoxType.equals(ACCT_STATUS)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='ACCT_STATUS' order by 1";
        } else if (pComboBoxType.equals(TARIFF_GRP)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='TARIFF_GRP' order by 2";

        } else if (pComboBoxType.equals(STB_TYPE)){ //add for imsboss lookup by Angel start --
            sqlStatement = "SELECT lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" FROM nesoplup where lkgrcode='stb_type'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and lkupcode = ?";
            }
        } else if (pComboBoxType.equals(VI_STB_TYPE)){
            sqlStatement = "SELECT lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" FROM nesoplup where lkgrcode='vi_stb_type'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and lkupcode = ?";
            }
        } else if (pComboBoxType.equals(VISTB_NOT_MANUAL)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup where bom_grp_id = 'VI_STB_TYPE' and bom_status = 'N'";
        } else if (pComboBoxType.equals(L2JOB_CODE_DESC)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup where bom_grp_id = 'L2JOB_CODE'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and bom_code = ?";
            }
        } else if (pComboBoxType.equals(FIBCOIND)){
            sqlStatement = "SELECT lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from lookup where lkgrcode='fibcoind'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and lkupdesc = ?";
            }
        } else if (pComboBoxType.equals(DOMAIN)){
            sqlStatement = "SELECT domntype \"CODE\", domnname \"DESCRIPTION\" from domain where domntype not in ('I','B','M')";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and domntype = ?";
            }
        } else if (pComboBoxType.equals(NOP_SRDATE_LOWER_LIMIT)
                || pComboBoxType.equals(PCD_SRDATE_APPDATE_GAP_D)
                || pComboBoxType.equals(PCD_SRDATE_APPDATE_GAP_M)
                || pComboBoxType.equals(PCD_SRDATE_UPPER_LIMIT)
                || pComboBoxType.equals(APPL_DATE_LIMIT)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", numvalu \"DESCRIPTION\" from paraboss where "
                    + "paraname in ('NOP_SRDATE_LOWER_LIMIT','PCD_SRDATE_UPPER_LIMIT', 'APPL_DATE_LIMIT', "
                    + "'PCD_SRDATE_APPDATE_GAP_M', 'PCD_SRDATE_APPDATE_GAP_D') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(NO_NEED_RESERVE_VI_STB_DATE)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", trunc(sysdate+numvalu) \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'NOP' and paraname = 'NO_NEED_RESERVE_VI_STB_DATE' ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(B2B_RENEWAL_ENABLE)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", charvalu \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'CCS' and paraname in ('B2B_RENEWAL_ENABLE') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(ON_SITE_VISIT_LOWER_LIMIT) || pComboBoxType.equals(BILLPRD)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", numvalu \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'CCS' and paraname in ('ON_SITE_VISIT_LOWER_LIMIT', 'billprd') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(UAMS_IP_1) || pComboBoxType.equals(UAMS_IP_2)
                || pComboBoxType.equals(UAMS_URL_2) || pComboBoxType.equals(UAMS_URL_1)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", charvalu \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'UAMS' and paraname in ('UAMS_IP_1', 'UAMS_URL_1','UAMS_IP_2', 'UAMS_URL_2')";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(UAMS_BOSS_RETRY_CNT)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", numvalu \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'UAMS' and paraname in ('UAMS_BOSS_RETRY_CNT')";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(VI_URL) || pComboBoxType.equals(CVI_DEFAULT_CCC)) {
            sqlStatement = "select paraname || '_' || syscode \"CODE\", charvalu \"DESCRIPTION\" from paraboss where "
                    + "syscode = 'NOP' and paraname in ('VI_URL', 'CVI_DEFAULT_CCC') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and paraname = ?";
            }
        } else if (pComboBoxType.equals(PENDING_REQUEST)){
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from nesoplup where lkgrcode = 'pendreq'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and lkupdesc = ?";
            }
        } else if (pComboBoxType.equals(PENDING_REQUEST_TEXT)){
            sqlStatement = "select lkupcode \"CODE\", lkupdesc \"DESCRIPTION\" from nesoplup where lkgrcode = 'pendreq'";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and lkupcode = ?";
            }
        } else if (pComboBoxType.equals(IMS_ASSO_DOMAIN)){
            sqlStatement = " select d.domntype \"CODE\", lk.lkupdesc \"DESCRIPTION\" " +
                    " from domain d, nesoplup lk where lk.lkgrcode='domn_asso'" +
                    " and lk.lkupcode=d.domntype and d.domntype not in ('I','B','M') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and domntype = ?";
            }
        } else if (pComboBoxType.equals(IMS_DOMN_FLEX_COM_IND)){
            sqlStatement = " select d.domntype \"CODE\", d.flex_com_ind \"DESCRIPTION\" " +
                    " from domain d, nesoplup lk where lk.lkgrcode='domn_asso'" +
                    " and lk.lkupcode=d.domntype and d.domntype not in ('I','B','M') ";
            if (pInputKey != null && pInputKey[0].length() > 0) {
                sqlStatement += " and domntype = ?";
            }
        } else if (pComboBoxType.equals(Customer_Tier)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='CUSTTIER'";
        } else if (pComboBoxType.equals(Customer_Tier_CCM)){ //Peter Wu 10/01/2012
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and m3g_grp_id = ?";
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='CUSTTIER_CCM'" + condition;
        } else if (pComboBoxType.equals(NATIONALITY)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='NATIONALITY'";
        } else if (pComboBoxType.equals(Server_Ind)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc \"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='SERVERIND'";
        } else if (pComboBoxType.equals(Credit_Fcs)){
            sqlStatement = "SELECT bom_code \"CODE\", bom_desc\"DESCRIPTION\" FROM b_lookup WHERE bom_status = 'A' and bom_grp_id='CREDIT_FCS'";
        } else if (pComboBoxType.equals(THIRD_PARTY_IND) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_TPARTYIND'";
        } else if (pComboBoxType.equals(SMS_LANG)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='SMS_LANG'" + condition;
        } else if (pComboBoxType.equals(IVRS_LANG)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='IVRS_LANG'" + condition;
        } else if (pComboBoxType.equals(REFUND_OPT)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_OPT'" + condition;
        } else if (pComboBoxType.equals(REFUND_REASON)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_REASON'" + condition + "order by 1";
        } else if (pComboBoxType.equals(REFUND_REA_SUB)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_REA_SUB'" + condition + "order by 1";
        } else if (pComboBoxType.equals(REFUND_MODE)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_MODE'" + condition;
        } else if (pComboBoxType.equals(REFUND_CB_OPT)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_CB_OPT'" + condition;
        } else if (pComboBoxType.equals(REFUND_CB_MODE)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='REFUND_CB_MODE'" + condition;
        } else if (pComboBoxType.equals(FRAUDMGT_ACTION) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id = 'FRAUDMGT_ACTION' and m3g_code is not null";
            if (pInputKey != null && pInputKey.length > 0) {
                sqlStatement = sqlStatement + " and bom_code = ?";
            }
        } else if (pComboBoxType.equals(FRAUDMGT_REASON) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id = 'FRAUDMGT_REASON' and m3g_code is not null and m3g_grp_id ";
            if (pInputKey != null && pInputKey.length > 0) {
                sqlStatement = sqlStatement + "= ?";
            } else {
                sqlStatement = sqlStatement + "= 'dummy'";
            }
        } else if (pComboBoxType.equals(FRAUDMGT_MESSAGE) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id = 'FRAUDMGT_MESSAGE' and m3g_code is not null";
        } else if (pComboBoxType.equals(FRAUDMGT_MESSAGECD) && systemId.equals(SYSTEM_ID_MOB)) {
            sqlStatement = "select bom_code \"CODE\", m3g_code \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id = 'FRAUDMGT_MESSAGE' and m3g_code is not null";
        } else if (pComboBoxType.equals(DEPOSIT_STATUS)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='DEPOSIT_STATUS'" + condition;
        } else if (pComboBoxType.equals(MOB_OFFER_TYPE)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_OFFER_TYPE'" + condition;
        } else if (pComboBoxType.equals(PPOS_PICKUP_STS)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='PPOS_PICKUP_STS'" + condition;
        } else if (pComboBoxType.equals(STAFF_SPONSOR)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='STAFF_SPONSOR'" + condition;
        } else if (pComboBoxType.equals(EXTERNAL_SPONSOR)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='EXTERNAL_SPONSOR'" + condition;
        } else if (pComboBoxType.equals(MNP_CUT_WINDOW)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MNP_CUT_WINDOW'" + condition;
        } else if (pComboBoxType.equals(SERVICE_STATUS)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='SERVICE_STATUS'" + condition;
        } else if (pComboBoxType.equals(MOB_BRAND)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_BRAND'" + condition;
        } else if (pComboBoxType.equals(MOB_SIM_TYPE)) {
            String condition = "";
            if (pInputKey != null && pInputKey[0].length() > 0)
                condition = " and bom_code = ?";
            sqlStatement = "select bom_code \"CODE\", bom_desc \"DESCRIPTION\" from b_lookup where bom_status = 'A' and bom_grp_id='MOB_SIM_TYPE'" + condition;
        } else if (pComboBoxType.equals(VIDEO_INFOLINE)) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = '" + pComboBoxType + "' ORDER BY 1";
        } else if (pComboBoxType.equals(SMARTLOGIN_URL) || pComboBoxType.equals(SMARTLOGIN_UID) ||
                pComboBoxType.equals(SMARTLOGIN_PWD) || pComboBoxType.equals(SMARTLOGIN_QTY) ) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = 'SMART_LOGIN' AND BOM_CODE = '" + pComboBoxType + "' ORDER BY 1";
        } else if (pComboBoxType.equals(SL_QTY)) {
            System.out.println("Get SL_QTY...");
            sqlStatement = "SELECT b.REL_PRD_ID \"CODE\", a.PARM_VAL \"DESCRIPTION\" FROM IMS_PARM a, IMS_PRDASSOC b WHERE a.parm_typ_upr_desc = 'SMART LOGIN QUANTITY' AND a.inact_dt is NULL AND b.prd_id = a.prd_id ORDER BY 1";
        } else if (pComboBoxType.equals(CREDCLAS)) {
            System.out.println("Get CREDCLAS...");
            sqlStatement = "SELECT CUSTCATG \"CODE\", CREDCLAS \"DESCRIPTION\" FROM CCCRIFLU";
        } else if (pComboBoxType.equals(CREDLMT)) {
            System.out.println("Get CREDLMT...");
            sqlStatement = "SELECT CUSTCATG \"CODE\", CREDLMT \"DESCRIPTION\" FROM CCCRIFLU";
        } else if (pComboBoxType.equals(BOM_COPY_PARM)) {
            System.out.println("Get BOM_COPY_PARM ...");
            sqlStatement = "SELECT VAS_TYPE \"CODE\", VAS_TYPE_DESC \"DESCRIPTION\" FROM VASLKUP WHERE BOM_COPY_PARM = 'Y' ORDER BY 1";
        } else if (pComboBoxType.equals(OVERRIDE_VAS_VALUE)) {
            System.out.println("Get OVERRIDE_VAS_VALUE ...");
            sqlStatement = "SELECT VAS_TYPE \"CODE\", VAS_TYPE_DESC \"DESCRIPTION\" FROM VASLKUP WHERE OVERRIDE_VAS_VALUE = 'Y' ORDER BY 1";
        } else if (pComboBoxType.equals(PARM_A_MANDATORY)) {
            System.out.println("Get PARM_A_MANDATORY ...");
            sqlStatement = "SELECT VAS_TYPE \"CODE\", VAS_TYPE_DESC \"DESCRIPTION\" FROM VASLKUP WHERE PARM_A_MANDATORY = 'Y' ";
        } else if (pComboBoxType.equals(PARM_B_MANDATORY)) {
            System.out.println("Get PARM_B_MANDATORY ...");
            sqlStatement = "SELECT VAS_TYPE \"CODE\", VAS_TYPE_DESC \"DESCRIPTION\" FROM VASLKUP WHERE PARM_B_MANDATORY = 'Y' ";
        } else if (pComboBoxType.equals(PARM_C_MANDATORY)) {
            System.out.println("Get PARM_C_MANDATORY ...");
            sqlStatement = "SELECT VAS_TYPE \"CODE\", VAS_TYPE_DESC \"DESCRIPTION\" FROM VASLKUP WHERE PARM_C_MANDATORY = 'Y' ";
        } else if (pComboBoxType.equals(DEVICE_VAS)) {
            System.out.println("Get DEVICE_VAS ...");
            sqlStatement = "SELECT x.prd_id \"CODE\", z.parm_val \"DESCRIPTION\" FROM ims_prd x, ims_prdassoc y, ims_parm z  WHERE z.parm_val like 'DEVICE_%' AND y.prd_id = z.prd_id and x.prd_id = y.rel_prd_id ORDER BY 1";
        } else if (pComboBoxType.equals(DEPOSIT_AMOUNT)) {
            System.out.println("Get DEPOSIT_AMOUNT ...");
            sqlStatement = "SELECT b.rel_prd_id  \"CODE\", a.parm_val \"DESCRIPTION\" FROM ims_parm a, ims_prdassoc b WHERE a.parm_desc LIKE 'VAS PARM_A %' AND a.prd_id IN (SELECT prd_id FROM ims_parm WHERE parm_typ_upr_desc LIKE 'VAS TYPE %' AND parm_val LIKE 'DEPOSIT%') AND b.prd_id = a.prd_id ORDER BY 1";
        } else if (pComboBoxType.equals(GIFT_ITEM_B2B)) {
            System.out.println("Get GIFT_ITEM_B2B ...");
            sqlStatement = "SELECT DISTINCT GIFT_CODE  \"CODE\", DESC_ENG \"DESCRIPTION\" FROM B_GIFT_INFO WHERE B2B = 'Y' ORDER BY 2";
        } else if (pComboBoxType.equals(GIFT_ITEM_B2C)) {
            System.out.println("Get GIFT_ITEM_B2C ...");
            //BOM2015057 by Eric(01481159)--Start
            //sqlStatement = "SELECT DISTINCT GIFT_CODE  \"CODE\", DESC_ENG \"DESCRIPTION\" FROM B_GIFT_INFO WHERE B2C = 'Y' AND (EXPIRY_DATE >= SYSDATE or EXPIRY_DATE IS NULL) ORDER BY 2";
            sqlStatement = "SELECT DISTINCT GIFT_CODE  \"CODE\", DESC_ENG \"DESCRIPTION\" FROM B_GIFT_INFO WHERE B2C = 'Y' ORDER BY 2";
            //--End
        } else if (pComboBoxType.equals(PROF_CHNG_TICKLR) && pInputKey.length>0) {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = '" + pComboBoxType + "' and BOM_CODE = ?";
        } else {
            sqlStatement = "SELECT BOM_CODE \"CODE\", BOM_DESC \"DESCRIPTION\" FROM B_LOOKUP WHERE BOM_GRP_ID = '" + pComboBoxType + "' ORDER BY 2";
        }
        
    }

}
