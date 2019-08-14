
		package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ConfigProperties;
import utils.PropertiesReader;

/**
		 * 操作数据库的方法
		 * 1.数据库的连接
		 * 2.数据的增删改
		 * 3.数据的查询
		 * 4.释放资源
		 * @author Administrator
		 *
		 */
		public class Main {

			public static void main(String[] args) {
				System.out.println("---测试连接数据库start");
				try {
					
					if(args.length<2) {
						return;
					}else {
						if(args[0].equals("1")) {
							ConfigProperties.PATH=args[1];
							System.out.println("初始化配置文件参数开始\t");
							initConfigProperties();
							System.out.println("初始化配置文件参数结束\t");
							
						}else {
							if(args.length<4) {//直接输入参数
								return;
							}else {
								ConfigProperties.CLASSNAME=args[0];
								ConfigProperties.URL=args[1];
								ConfigProperties.USERNAME=args[2];
								ConfigProperties.PASSWORD=args[3];
							}
						}
					}
				} catch (Exception e) {
					System.out.println("初49始化配置参数错误!\t");
					e.printStackTrace();
					return;
				}
			
				try {
					System.out.println("---开始连接数据库");
					getConnection();
				} catch (Exception e) {
					System.out.println("===yichang");
					e.printStackTrace();
				}
				
				
				System.out.println("---测试连接数据库end");

			}

			//private Connection conn;
			
			public static void getConnection(){
				try {

					//读取配置文件
						Connection conn=null;
						PreparedStatement pstmt=null;
						ResultSet rs=null;
						
						try {
							//1加载驱动类Class.forName();
							Class.forName(ConfigProperties.CLASSNAME);
							//2使用DriverManager类获得数据库连接对象
//							conn=DriverManager.getConnection(ConfigProperties.URL, ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
							conn=DriverManager.getConnection(ConfigProperties.URL, ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
							
							 if(!conn.isClosed()) {
								System.out.println("++++成功连接数据库++++");
								
							}else {
								System.out.println("---失败连接数据库，不知名的原因！！！");
							}
							
						} catch (ClassNotFoundException e) {
							System.out.println("===找不到驱动类！！！");

							e.printStackTrace();
						} catch (SQLException e) {
							System.out.println("===数据库连接异常！！！\t，有可能是以下原因。 1.用户名或密码或url不正确  2.网络不通");

							e.printStackTrace();
						}finally{
							//5关闭资源
							if(rs!=null){
								rs.close();
							}
							if(pstmt!=null){
								pstmt.close();
							}
							if(conn!=null){
								conn.close();
							}
						}
						
						
				} catch (Exception e) {
					System.out.println("===关闭连接异常！！！");
					e.printStackTrace();
				}
			}
			
			
			/**
			 * 初始化配置，读取参数
			 * @author JBL
			 * @date 2019年5月13日 下午1:06:33
			 * @since 1.7
			 * @version 1.0
			 */
			private static void initConfigProperties() {

				String path = ConfigProperties.PATH;
//				String path = FileUtils.getJarFile().getParent()+"/config/config.properties";
				PropertiesReader reader = PropertiesReader.getInstance(path);//固定路径下配置文件
				ConfigProperties.CLASSNAME = reader.getProperty("jdbc.driverClassName").trim();
				System.out.println("数据库驱动类:"+ConfigProperties.CLASSNAME);
				ConfigProperties.URL = reader.getProperty("jdbc.url").trim();
				System.out.println("数据库URL:"+ConfigProperties.URL);
				ConfigProperties.USERNAME = reader.getProperty("jdbc.username").trim();
				System.out.println("数据库用户名:"+ConfigProperties.USERNAME);
				ConfigProperties.PASSWORD = reader.getProperty("jdbc.password").trim();
				System.out.println("数据库密码:"+ConfigProperties.PASSWORD);
				
			}
}

	
