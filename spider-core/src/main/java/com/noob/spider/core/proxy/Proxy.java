package com.noob.spider.core.proxy;

/**
 * 
 */

public class Proxy {

	private String host;
	private int port;
	private String username;
	private String password;

	public Proxy(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Proxy(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Proxy proxy = (Proxy) o;

		if (port != proxy.port) return false;
		if (host != null ? !host.equals(proxy.host) : proxy.host != null) return false;
		if (username != null ? !username.equals(proxy.username) : proxy.username != null) return false;
		return password != null ? password.equals(proxy.password) : proxy.password == null;
	}

	@Override
	public int hashCode() {
		int result = host != null ? host.hashCode() : 0;
		result = 31 * result + port;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Proxy{" +
				"host='" + host + '\'' +
				", port=" + port +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}

// 2019-07-03 8：45 看起来 Proxy 果然是代理
// 包含了登录 vps 的 ip，端口，用户名，密码
