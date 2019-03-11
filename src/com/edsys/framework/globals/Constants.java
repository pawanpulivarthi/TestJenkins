package com.edsys.framework.globals;

import com.edsys.framework.util.EdsysUtil;

public abstract interface Constants {
	public static final String EMPTY_STRING = "";
	public static final String CACHE_APP_CONFIG = "CACHE_APP_CONFIG";
	public static final String CACHE_ERROR_MESSAGE = "CACHE_ERROR_MESSAGE";
	public static final String CACHE_ROLE_APP_RESOURCES = "CACHE_ROLE_APP_RESOURCES";
	public static final String CACHE_SQL_QUERIES = "CACHE_SQL_QUERIES";
	public static final String PARCALA_HOME_DIR = EdsysUtil.getConfigDir();
	public static final String CONFIG_DIR = PARCALA_HOME_DIR + "//config//";
	public static final String TEMP_DIR = PARCALA_HOME_DIR + "//temp//";
	public static final String BACKUP_DIR = PARCALA_HOME_DIR + "//Dropbox//pc_DBData//";
	public static final String STATIC_DIR = PARCALA_HOME_DIR + "//static//";
	public static final String HELP_DIR = CONFIG_DIR + "//help//";
	public static final String FONT_DIR = CONFIG_DIR + "//font//";
	public static final String WORDS_FILE = CONFIG_DIR + "//baseConfig//" + "Dictionary.txt";
	public static final int DELETE_QUERY_TYPE = 3;
	public static final String PARCALA_APP_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "AppConfig.xml";
	public static final String PARCALA_RESOURCE_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "ResourceConfig.xml";
	public static final String PARCALA_JOB_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "JobConfig.xml";
	public static final String QUARTZ_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "quartz.properties";
	public static final String PARCALA_PAYMENT_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "PaymentConfig.xml";
	public static final String DATA_SOURCE_NAME = "DataSourceName";
	public static final String PARAM_DRIVER_CLASS = "JdbcDriver";
	public static final String PARAM_JDBC_URL = "JdbcUrl";
	public static final String PARAM_PASSWORD = "JdbcPassword";
	public static final String PARAM_USER_NAME = "JdbcUserName";
	public static final String PARAM_DATABASE_PORT = "DBPort";
	public static final String PARAM_PAGE_SIZE = "PageSize";
	public static final String PARAM_FETCH_ALL = "FetchAll";
	public static final String PARAM_APP_URL = "AppURL";
	public static final String PARAM_POSTGRE_HOME = "PostgreHome";
	public static final String HOME_URL = "HomeURL";
	public static final int SELECT_QUERY_TYPE = 1;
	public static final int QUESTION_PAGINATION_SIZE = 20;
	public static final String CRIC_HOME = "CRIC_HOME";
	public static final String PARCALA_LOG4J_CONFIG_FILE = CONFIG_DIR + "//baseConfig//" + "Log4jConfig.xml";
	public static final String STATUS_HOME = "home";
	public static final String STATUS_LOGIN = "login";
	public static final String GROUP_NAME = "GROUP_NAME";
	public static final int UPDATE_QUERY_TYPE = 2;
	public static final String TARGET_URL = "http:localhost:8080";
	
	public static final String MASTER_ADMIN = "100";
	
	public static final String INST_ADMIN = "1";
	
	public static final String STUDENT = "10";
	
	public static final String STAFF = "5";
}
