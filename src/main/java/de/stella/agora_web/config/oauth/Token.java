package de.stella.agora_web.config.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token { 
	
	private String userId; 
	private String accessToken; 
	private String refreshToken; 
	public String getUserId() { 
		return userId; 
	} 
	public void setUserId(String userId) { 
		this.userId = userId; 
	} 
	public String getAccessToken() { 
		return accessToken; 
	} 
	public void setAccessToken(String accessToken) { 
		this.accessToken = accessToken; 
	} 
	public String getRefreshToken() { 
		return refreshToken; 
	} 
	public void setRefreshToken(String refreshToken) { 
		this.refreshToken = refreshToken; 
	}
public void setUserId(Object id) {
    if (id == null) {
        throw new NullPointerException("id cannot be null");
    }

    this.userId = String.valueOf(id);

} 
}