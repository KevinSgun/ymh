package cn.kuailaimei.client.api.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *  
* @Description:(地区实体类，解析城市实体用)
* @author ymh
 */
public class CityEntity {

	private String pname;
	private List<String> cname = new ArrayList<String>();
	private List<AreaEntity> aname = new ArrayList<AreaEntity>();
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public List<AreaEntity> getAname() {
		return aname;
	}
	public void setAname(List<AreaEntity> aname) {
		this.aname = aname;
	}
	public List<String> getCname() {
		return cname;
	}
	public void setCname(List<String> cname) {
		this.cname = cname;
	}

}
