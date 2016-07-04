package cn.kuailaimei.client.common.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.vo.Photo;
import cn.kuailaimei.client.common.widget.ContentViewHolder;
import cn.kuailaimei.client.common.widget.ToolBarHelper;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.utils.ToastMaster;
import com.zitech.framework.utils.FileDiskAllocator;
import com.zitech.framework.utils.ImageUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 浏览某一个目录下的所有图片
 *
 */
public class PhotoGridActivity extends AppBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
	public static final String PHOTO_DIR_ID = "photo_dir_id";
	public static final String PHOTO_DIR_NAME = "photo_dir_name";
	public static final String SELECTED_PHOTO_LIST = "selected_photo_list";
	public static final String RES_PHOTO_LIST = "res_photo_list";
	public static final String INTENT_CHOOSE_RECENTLY = "choose_recent_list";
	public static final String RECENT_LIST_SIZE = "rent_list_size";
	public static final String RECENT_LIST_PREVIEW = "rent_list_preview";
	public static final int MAX_SIZE = 9;
	public static final int CHOOSE_RESULT_CODE = 1001;
	private static final String SMALL_PHOTO_NAME = "_small.jpg";

	private GridView mGridView;
	protected ArrayList<Photo> selectedList;
	private boolean chooseRecent = false;
	private ImagesAdapter mAdapter;
	private ContentViewHolder contentHolder;

	private String mDirId;
	private String mDirName;
	private TextView mPreview;
	private TextView mCurrentPicNum;
	private Button mConfirm;
	private String recentPreview;
	private int recentSize;
	public static final String MAX_PHOTO_SIZE = "max_photo_size";

	@Override
	protected void initView() {
		setRightText(R.string.alubm_title);
		contentHolder=(ContentViewHolder) findViewById(R.id.content_holder);
		contentHolder.setErrorPrompt(R.string.album_error_prompt);
		contentHolder.showLoading();
		mPreview = (TextView) findViewById(R.id.preview);
		mPreview.setOnClickListener(this);
		mCurrentPicNum = (TextView) findViewById(R.id.current_pic_num);
		mConfirm = (Button) findViewById(R.id.confirm);
		mConfirm.setOnClickListener(this);
		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Photo photo = (Photo) parent.getItemAtPosition(position);
				if (photo == null) {
					return;
				}
				if (!selectedList.contains(photo)) {
					if (selectedList.size() >= MAX_SIZE) {
						ToastMaster.popToast(PhotoGridActivity.this, "最多选择" + MAX_SIZE + "张图片");
						return;
					}
				}
				mAdapter.setCheck(position, view);
			}
		});
//		mGridView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
		updateCheckedNum(selectedList.size());
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}

//	@Override
//	public void onNotifyClosed(Object data) {
//		super.onNotifyClosed(data);
//		if (Constants.ApplicationExtra.FINISH_SELECT_PHOTO.equals(data)) {
//			finish();
//		}
//	}

	private void handleIntent(Intent data) {
		String dirId = data.getStringExtra(PHOTO_DIR_ID);
		boolean chooseRecent = data.getBooleanExtra(INTENT_CHOOSE_RECENTLY, false);
		boolean restartLoader = false;
		if ((this.chooseRecent && !chooseRecent) || (mDirId != null && !mDirId.equals(dirId)) && mAdapter != null
				&& mAdapter.getCount() > 0) {
			mAdapter.clear();
			restartLoader = true;
		}
		mDirName = data.getStringExtra(PHOTO_DIR_NAME);
		mDirId = dirId;
		this.chooseRecent = chooseRecent;
		selectedList = data.getParcelableArrayListExtra(SELECTED_PHOTO_LIST);

		if (selectedList == null)
			selectedList = new ArrayList<Photo>();
		if (chooseRecent) {
			setTitle("最近照片");
		} else {
			setTitle(mDirName != null ? mDirName : "选择照片");
		}

		if (restartLoader){
			contentHolder.showLoading();
			getSupportLoaderManager().restartLoader(0, null, this);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mPreview.getId()) {
//			// if()
//			if (selectedList.size() > 0) {
//				ArrayList<String> uriPathList = new ArrayList<String>();
//				ArrayList<String> thumbnailList = new ArrayList<String>();
//				for (Photo photo : selectedList) {
//					uriPathList.add(Uri.fromFile(new File(photo.imgPath)).toString());
//					thumbnailList.add(Uri.fromFile(new File(photo.thumbPath != null ? photo.thumbPath : photo.imgPath))
//							.toString());
//				}
//				PhotoPagerActivity.launch(this, uriPathList, thumbnailList, 0);
//				// finish();
//			} else {
//				ToastMaster.popToast(this, "亲，选取照片才能预览哦~");
//			}
		} else if (v.getId() == mConfirm.getId()) {
			ArrayList<Photo> nSelectedList = new ArrayList<Photo>();
			int size = selectedList.size();
			for (int i = 0; i < size; i++) {
				Photo nPhoto = new Photo();
				nPhoto = compressPhoto(selectedList.get(i));
				nSelectedList.add(nPhoto);
			}
			selectedList = nSelectedList;
			Intent intent = new Intent();
			intent.putExtra(PhotoGridActivity.RES_PHOTO_LIST, selectedList);
			setResult(RESULT_OK, intent);
//			BeautApplication.getInstance().quickCache(RESULT_CANCELED, false);
//			notifyChange(ACTION_FINISH, Constants.ApplicationExtra.FINISH_SELECT_PHOTO);
		}
	}

	private File getSmallPhotoPath() {
		// if (!getUser().exist()) {
		// return FileDiskAllocator.creatFansDir(this);
		// }
		return new File(FileDiskAllocator.creatBbsPostDir(this, System.currentTimeMillis() + ""), SMALL_PHOTO_NAME);
	}

	private Photo compressPhoto(Photo photo) {
		if(photo.isNet()) return photo;
		photo.compressedPath = ImageUtils.compressNoLargePhoto(PhotoGridActivity.this, new File(photo.imgPath),
				getSmallPhotoPath(), true, ImageUtils.MAX_LIMIT);
		return photo;
	}

	@Override
	protected void onActionBarItemClick(int position) {
		if (position == ToolBarHelper.ITEM_RIGHT) {
			Intent intent = new Intent(PhotoGridActivity.this, PhotoDirActivity.class);
			intent.putExtra(PhotoGridActivity.SELECTED_PHOTO_LIST, selectedList);
			intent.putExtra(PhotoGridActivity.RECENT_LIST_PREVIEW, recentPreview);
			intent.putExtra(PhotoGridActivity.RECENT_LIST_SIZE, recentSize);
			startActivityForResult(intent, CHOOSE_RESULT_CODE);
		} else if (position == ToolBarHelper.ITEM_LEFT) {
			selectedList.clear();
			setResult(RESULT_CANCELED);
//			notifyChange(ACTION_FINISH, Constants.ApplicationExtra.FINISH_SELECT_PHOTO);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CHOOSE_RESULT_CODE && resultCode == RESULT_OK)
			handleIntent(data);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		if (chooseRecent) {
			return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
					MediaStore.Images.Media.DATE_ADDED + " DESC  LIMIT 100 OFFSET 0");
		} else {
			return new CursorLoader(this,
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					new String[] { MediaStore.Images.Media.DATA // 图片地址
					}, mDirId == null ? null : MediaStore.Images.Media.BUCKET_ID + "=" + mDirId, null,
					MediaStore.Images.Media.DATE_MODIFIED + " DESC");
		}
	}


	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		if (isFinishing())
			return;
		if (cursor != null && cursor.getCount() > 0) {
			ArrayList<Photo> list = new ArrayList<Photo>();

			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				Photo photo = new Photo();
				photo.imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
				String imageId = null;
				if (idIndex >= 0) {
					imageId = String.valueOf(cursor.getInt(idIndex));
				} else {
					imageId = String.valueOf(System.currentTimeMillis());
				}
				String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA };
				Cursor thumbCusor = getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, projection,
						Thumbnails.IMAGE_ID + "=" + imageId, null, null);
				if (thumbCusor.moveToFirst()) {
					int dataColumn = thumbCusor.getColumnIndex(Thumbnails.DATA);
					photo.thumbPath = thumbCusor.getString(dataColumn);
				}
				thumbCusor.close();
				list.add(photo);
			}
			cursor.close();
			if (chooseRecent) {
				if (list.size() > 0) {
					recentPreview = list.get(0).imgPath;
				}
				recentSize = list.size();
			}
			if (mAdapter == null) {
				mAdapter = new ImagesAdapter(this, list, selectedList);
				for (Photo pt : list) {
					// System.out.println("pt:" + pt.imgPath);
				}
				for (Photo pt : selectedList) {
					// System.out.println("selecetd:" + pt.imgPath);
				}
				mGridView.setAdapter(mAdapter);
			} else {
				mAdapter.setList(list);
				mAdapter.setCheckList(selectedList);
			}
			if(mAdapter.getList().size()>0){
				contentHolder.showContent();
			}else{
				contentHolder.showNoData();
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	public class ImagesAdapter extends ListAdapter<Photo> {
		private List<Photo> mCheckList;

		public ImagesAdapter(Context context, List<Photo> list, List<Photo> checkedList) {
			super(context);
			setList(list);
			mCheckList = checkedList;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = inflatView(R.layout.select_image_grid_item);
				holder = new Holder();
				holder.mImageView = (RemoteImageView) convertView.findViewById(R.id.iv);
				holder.mCheckImgaeView = (ImageView) convertView.findViewById(R.id.check);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Photo photo = (Photo) getItem(position);

			if (mCheckList != null && mCheckList.contains(photo)) {
				// holder.mCheckImgaeView.setVisibility(View.VISIBLE);
				holder.mCheckImgaeView.setImageResource(R.mipmap.checkbox_sel);
			} else {
				// holder.mCheckImgaeView.setVisibility(View.INVISIBLE);
				holder.mCheckImgaeView.setImageResource(R.mipmap.checkbox_nor);
			}
			holder.mImageView.setDefaultImageResource(R.mipmap.ic_shop_default);
			String thumb = photo.thumbPath;
			if (thumb != null) {
				holder.mImageView.setImageUri("file:///" + thumb);
			} else {
				holder.mImageView.setImageUri("file:///" + photo.imgPath);
			}
			return convertView;
		}

		public void setCheckList(List<Photo> checkList) {
			this.mCheckList = checkList;
		}

		private View inflatView(int layoutId) {
			return LayoutInflater.from(PhotoGridActivity.this).inflate(layoutId, null);
		}

		public void setCheck(int position, View view) {
			Photo photo = (Photo) getItem(position);

			boolean checked = mCheckList.contains(photo);

			Holder holder = (Holder) view.getTag();

			if (checked) {
				mCheckList.remove(photo);
				holder.mCheckImgaeView.setImageResource(R.mipmap.checkbox_nor);
			} else {
				mCheckList.add(photo);
				holder.mCheckImgaeView.setImageResource(R.mipmap.checkbox_sel);
			}
			updateCheckedNum(mCheckList.size());
		}

		private class Holder {
			private RemoteImageView mImageView;
			private ImageView mCheckImgaeView;
		}

		public List<Photo> getCheckedList() {
			return mCheckList;
		}

	}

	private void updateCheckedNum(int size) {
		mCurrentPicNum.setText(size + "/" + MAX_SIZE);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.select_image_grid;
	}

	@Override
	protected void onDestroy() {
		if (mAdapter != null) {
			mAdapter.getList().clear();
			mAdapter.getCheckedList().clear();
			mAdapter.notifyDataSetChanged();
		}
		mGridView.setOnItemClickListener(null);
		mGridView.setOnScrollListener(null);
		getSupportLoaderManager().destroyLoader(0);
		// mGridView.setAdapter(nul);
		super.onDestroy();
	}
}
