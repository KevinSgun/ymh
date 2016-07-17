package cn.kuailaimei.client.api.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**<pre>
 * 用来封装地区数据的实体类，实现{@link Parcelable}接口
 * 通过城市名字关联相应地区，地区用ArrayList<String>存储
 * @author ymh
 *
 */
@SuppressLint("ParcelCreator")
public class AreaEntity implements Parcelable {
	/**
	 * 城市名字
	 */
	private String cname;
	private List<String> aname = new ArrayList<String>();
	
	public static final Parcelable.Creator<AreaEntity> CREATOR = new Creator<AreaEntity>(){

		@SuppressWarnings("unchecked")
		@Override
		public AreaEntity createFromParcel(Parcel source) {
			AreaEntity area = new AreaEntity();
			area.setCname(source.readString());
			area.setAname(source.readArrayList(String.class.getClassLoader()));
			return area;
		}

		@Override
		public AreaEntity[] newArray(int size){
			return new AreaEntity[size];
		}
		
	};
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List<String> getAname() {
		return aname;
	}
	public void setAname(List<String> aname) {
		this.aname = aname;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cname);
		dest.writeList(aname);
	}
}
