package haust.vk.entity;

public class Userlogin {
	private String token_id;
	private String user_id;
	private String login_device_type;
	private String user_login_time;
	
	public String getToken_id() {
		return token_id;
	}
	
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getLogin_device_type() {
		return login_device_type;
	}
	
	public void setLogin_device_type(String login_device_type) {
		this.login_device_type = login_device_type;
	}
	
	public String getUser_login_time() {
		return user_login_time;
	}
	
	public void setUser_login_time(String user_login_time) {
		this.user_login_time = user_login_time;
	}
	
	@Override
	public String toString() {
		return "Userlogin [token_id=" + token_id + ", user_id=" + user_id
				+ ", login_device_type=" + login_device_type
				+ ", user_login_time=" + user_login_time + "]";
	}
	
}
