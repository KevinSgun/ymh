package com.fans.becomebeaut.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 */
public class PhotoBase implements Parcelable
{
    public String id;
    public String text;
    public String imgPath;
    public String compressedPath;
    public String thumbPath;
    public int isNetUrl;//0是本地url,1是网络url 

	@Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(imgPath);
        dest.writeString(compressedPath);
        dest.writeString(thumbPath);
        dest.writeInt(isNetUrl);
    }

    public static <T extends PhotoBase> T readFromParcel(Parcel in, T t)
    {
        if (t == null)
        {
            return null;
        }

        if (in == null)
        {
            return null;
        }

        t.id = in.readString();
        t.text = in.readString();
        t.imgPath = in.readString();
        t.compressedPath=in.readString();
        t.thumbPath=in.readString();
        t.isNetUrl=in.readInt();
        return t;
    }

    public static final Creator<PhotoBase> CREATOR
            = new Creator<PhotoBase>()
    {
        public PhotoBase createFromParcel(Parcel in)
        {
            return readFromParcel(in, new PhotoBase());
        }

        public PhotoBase[] newArray(int size)
        {
            return new PhotoBase[size];
        }
    };
    

	private String getImageUri() {
		return !TextUtils.isEmpty(imgPath) ? "file:///" + imgPath : null;
	}

	public String getThumbUrl() {
		if(isNet()) return thumbPath;
		String url = !TextUtils.isEmpty(thumbPath) ? "file:///" + thumbPath : null;
		if (url == null) {
			url = getComressedUrl();
		}
		return url != null ? url : getImageUri();
	}

	private String getComressedUrl() {
		return !TextUtils.isEmpty(compressedPath) ? "file:///" + compressedPath : null;
	}
	
	public boolean isNet(){
		return isNetUrl == 1;
	}
}
