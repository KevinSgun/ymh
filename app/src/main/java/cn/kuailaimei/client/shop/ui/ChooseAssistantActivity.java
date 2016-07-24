package cn.kuailaimei.client.shop.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.CommitOrderInfo;
import cn.kuailaimei.client.api.entity.Designer;
import cn.kuailaimei.client.api.entity.DesignerService;
import cn.kuailaimei.client.api.entity.Employee;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SIDRequest;
import cn.kuailaimei.client.api.response.AssistantListResposne;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.shop.adapter.AssistantRecycleViewAdapter;
import cn.kuailaimei.client.common.utils.ToastMaster;

/**
 * Created by lu on 2016/7/5.
 */
public class ChooseAssistantActivity extends AppBarActivity {
    private RecyclerView assistantList;
    private DesignerService designerService;
    private AssistantRecycleViewAdapter adapter;
    private RippleButton confirm;
    private Employee choosedEmployee;
    private Designer designer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_assistant;
    }

    @Override
    protected void initView() {
        setTitle("选择助理");
        assistantList = (RecyclerView) findViewById(R.id.assistant_list);
        assistantList.setLayoutManager(new GridLayoutManager(this, 2));
        confirm = (RippleButton) findViewById(R.id.confirm);
        designer = getIntent().getParcelableExtra(Constants.ActivityExtra.DESIGNER);

        confirm.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
//                ConfirmOrderActivity.launch(thi,COnfir);
                if (choosedEmployee != null) {
                    CommitOrderInfo info = new CommitOrderInfo();
                    info.setsId(String.valueOf(designerService.getSid()));
                    info.setAmount(Integer.parseInt(designerService.getPrice()));
                    info.setAssistantName(choosedEmployee.getAlias());
                    info.setContent(designerService.getContent());
                    info.setmId(String.valueOf(designer.getId()));
                    info.setmId1(String.valueOf(choosedEmployee.getUid()));
                    info.setcId(String.valueOf(designerService.getCid()));
                    info.setDesignName(designer.getAlias());
                    info.setName(designerService.getName());
                    info.setId(designerService.getId());
//                    info.setAmount(designerService.getPrice());
//                    info.setmId(designerService.get);
                    ConfirmOrderActivity.launch((Activity) getContext(), info);
                }else {
                    ToastMaster.popToast(getContext(),"请选择助理");
                }
            }
        });
    }

    @Override
    protected void initData() {
        designerService = getIntent().getParcelableExtra(Constants.ActivityExtra.DESIGNER_SERVICE);
        SIDRequest data = new SIDRequest();
        data.setsId(String.valueOf(designerService.getSid()));
        Request request = new Request(data);
        ApiFactory.getAssistantList(request).subscribe(new ProgressSubscriber<ApiResponse<AssistantListResposne>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<AssistantListResposne> listApiResponse) {
                List<Employee> assistants = listApiResponse.getData().getEmployeeList();
                adapter = new AssistantRecycleViewAdapter(getContext(), assistants);
                assistantList.setAdapter(adapter);

                adapter.setOnAssistantChoosedListener(new AssistantRecycleViewAdapter.OnAssistantChoosedListener() {
                    @Override
                    public void onAssistantChoosed(Employee employee) {
                        choosedEmployee = employee;
                    }
                });
            }
        });
    }

    public static void launch(Context context, Designer designer, DesignerService service) {
        Intent intent = new Intent(context, ChooseAssistantActivity.class);
        intent.putExtra(Constants.ActivityExtra.DESIGNER_SERVICE, service);
        intent.putExtra(Constants.ActivityExtra.DESIGNER, designer);
        context.startActivity(intent);
    }
}
