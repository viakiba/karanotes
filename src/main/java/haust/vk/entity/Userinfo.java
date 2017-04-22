package haust.vk.entity;

public class Userinfo {
    private String user_id;              
    private String user_headimg;        
    private String user_background_img;        
    private String user_email;           
    private String user_name;            
    private String user_password;        
    private String user_github;     
    private String user_sex;          
    private String user_path;          
    private String user_signature;    
   
   	public Userinfo(){}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_headimg() {
		return user_headimg;
	}

	public void setUser_headimg(String user_headimg) {
		this.user_headimg = user_headimg;
	}
	
	public String getUser_background_img() {
		return user_background_img;
	}
	
	public void setUser_background_img(String user_background_img) {
		this.user_background_img = user_background_img;
	}
	
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_github() {
		return user_github;
	}

	public void setUser_github(String user_github) {
		this.user_github = user_github;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_path() {
		return user_path;
	}

	public void setUser_path(String user_path) {
		this.user_path = user_path;
	}

	public String getUser_signature() {
		return user_signature;
	}

	public void setUser_signature(String user_signature) {
		this.user_signature = user_signature;
	}

	@Override
	public String toString() {
		return "Userinfo [user_id=" + user_id + ", user_headimg=" + user_headimg + ", user_background_img="
				+ user_background_img + ", user_email=" + user_email + ", user_name=" + user_name + ", user_password="
				+ user_password + ", user_github=" + user_github + ", user_sex=" + user_sex + ", user_path=" + user_path
				+ ", user_signature=" + user_signature + "]";
	}

}
