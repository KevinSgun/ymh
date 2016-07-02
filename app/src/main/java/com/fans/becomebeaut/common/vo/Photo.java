package com.fans.becomebeaut.common.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 */
public class Photo extends PhotoBase {
	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
		public Photo createFromParcel(Parcel in) {
			return readFromParcel(in, new Photo());
		}

		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || ((Object) this).getClass() != o.getClass()) {
			return false;
		}

		Photo photo = (Photo) o;
		if(isNetUrl==1||photo.isNet()) return false;
		return imgPath.equals(photo.imgPath);
	}

	@Override
	public int hashCode() {
		if(isNetUrl==1) return thumbPath.hashCode();
		return imgPath.hashCode();
	}

	public String getName() {
		if (imgPath == null)
			return null;
		int index = imgPath.lastIndexOf("/");
		if (index >= 0) {
			return imgPath.substring(index);
		}
		return null;
	}

	// public String
}
