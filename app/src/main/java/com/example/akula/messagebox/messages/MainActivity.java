package com.example.akula.messagebox.messages;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akula.messagebox.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView  recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout emptyMsgContainerLayout, recyclerViewContainerLayout;
    private TextView lastRefreshedViewBelowEmptyMsg;
    MessagesAdapter adapter ;
    List<MOBInboxMessage> inbox_list ;
    public ActionMode mActionMode = null;
    private boolean isMultiSelect =false;
    private boolean isSelectAll = false;
    private int selectedCount = 0;
    List<MOBInboxMessage> deletedMessages ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_inbox_fragment);
        emptyMsgContainerLayout = (LinearLayout) findViewById(R.id.emptyMsgContainer);
        recyclerViewContainerLayout = (LinearLayout) findViewById(R.id.recyclerViewContainer);
        recyclerViewContainerLayout.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.inbox_recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        inbox_list = new ArrayList<>();
        for (int i =0;i<10;i++){
            MOBInboxMessage m = new MOBInboxMessage();
            m.set_id("some id "+i);
            m.setBody("message "+i );
            if(i % 2 == 0) {
                m.setSelected(true);
            } else{
                m.setSelected(false);
            }
            inbox_list.add(m);
        }

        adapter = new MessagesAdapter(getApplicationContext(),inbox_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (inbox_list == null || inbox_list.isEmpty() || inbox_list.size() == position) {
                    return;
                }

                if(isMultiSelect){
                    enableActionMode();
                    handleMessageClickEvent(position);
                }

                if(isSelectAll){
                  handleMessageClickEvent(position);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (inbox_list == null || inbox_list.isEmpty() || inbox_list.size() == position) {
                    return;
                }
                if (!isMultiSelect) {
                    enableActionMode();
                    refreshAdapter();
                    handleListSelection(inbox_list, position);
                    isMultiSelect = true;
                    updateHeaderForMultiSelect();
                }

            }
        }));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void handleMessageClickEvent(int position) {
        handleToggleForListSelection(inbox_list,position);
        updateHeaderForMultiSelect();
        dismissActionMode();
    }

    public void handleListSelection(List<MOBInboxMessage> inbox_list, int position) {
        selectedCount = 0;
        if (inbox_list ==null || inbox_list.isEmpty() || position < 0 || position >= inbox_list.size()) {
            return;
        }
        inbox_list.get(position).setSelected(true);
        selectedCount++;
        refreshAdapter();
    }


    private void handleToggleForListSelection(List<MOBInboxMessage> inbox_list, int position) {
        if (inbox_list==null || inbox_list.isEmpty() || position < 0 || position >= inbox_list.size()) {
            return;
        }

        MOBInboxMessage inboxMessage = inbox_list.get(position);
        if (inboxMessage.isSelected()) {
            inboxMessage.setSelected(false);
            selectedCount--;
        } else {
            inboxMessage.setSelected(true);
            selectedCount++;
        }
        refreshAdapter();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

    }


    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.message_long_click, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_delete_hs:
                    if (isMultiSelect || isSelectAll){
                        updateDeletedMessagesList();
                        MessagesHelper.markAsReadOrDeleted();
                        updateContainerView();
                        if(mActionMode!=null){
                            mActionMode.finish();
                        }
                        refreshAdapter();
                        Toast.makeText(getApplicationContext(), mode.getTitle() + " Items Deleted !", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.action_select_hs:
                    isSelectAll = true;
                    isMultiSelect = false;
                    updateHeaderForSelectAll();
                    refreshAdapter();
                    deletedMessages =inbox_list;
                    return true;
                default:
                        return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            isSelectAll = false;
            selectedCount = 0;
            resetInboxListSelectState();
            if (adapter!=null) adapter.setActionModeEnabled(false);
            refreshAdapter();


        }
    };

    private void updateHeaderForSelectAll() {
        selectedCount = 0;
        if (mActionMode != null) {
            if (inbox_list!=null && !inbox_list.isEmpty()) {
                for (int i = 0; i < inbox_list.size(); i++) {
                    MOBInboxMessage mobInboxMessage = inbox_list.get(i);
                    if (mobInboxMessage == null) continue;
                    mobInboxMessage.setSelected(true);
                    selectedCount++;
                }
            }
            updateHeaderForMultiSelect();
        }
    }

    private void resetInboxListSelectState() {
        if (inbox_list ==null ||inbox_list.isEmpty()) {
            return;
        }
        for (int i = 0; i < inbox_list.size(); i++) {
            MOBInboxMessage mobInboxMessage = inbox_list.get(i);
            if (mobInboxMessage == null) {
                continue;
            }
            mobInboxMessage.setSelected(false);
        }
    }

    private void updateHeaderForMultiSelect() {
        if (mActionMode != null && inbox_list != null && !inbox_list.isEmpty() && selectedCount != 0) {
            mActionMode.setTitle("" + selectedCount + " Selected ");
        }
    }


    public void enableActionMode() {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(mActionModeCallBack);
            if (adapter != null) adapter.setActionModeEnabled(true);
        }
    }


    private void dismissActionMode() {
        if (selectedCount == 0 && mActionMode != null) {
            mActionMode.finish();
            if (adapter != null) adapter.setActionModeEnabled(false);
        }
    }



    private void updateContainerView() {
        if (inbox_list!=null && !inbox_list.isEmpty()) {
            recyclerViewContainerLayout.setVisibility(View.VISIBLE);
            emptyMsgContainerLayout.setVisibility(View.GONE);
        } else {
            recyclerViewContainerLayout.setVisibility(View.GONE);
            emptyMsgContainerLayout.setVisibility(View.VISIBLE);
        }
    }

    public void refreshAdapter() {
        adapter.setInbox_list(inbox_list);
        adapter.notifyDataSetChanged();
    }


    private void updateDeletedMessagesList() {

        if (inbox_list!=null && !inbox_list.isEmpty()) {
            deletedMessages = new ArrayList<>();
            for (int i = 0; i < inbox_list.size(); i++) {
                MOBInboxMessage mobInboxMessage = inbox_list.get(i);
                if (mobInboxMessage == null || !mobInboxMessage.isSelected()) {
                    continue;
                }
                deletedMessages.add(mobInboxMessage);
            }
            for (MOBInboxMessage msg : deletedMessages) {
                if (inbox_list.contains(msg)) {
                    inbox_list.remove(msg);
                }
            }
        }
    }
}
