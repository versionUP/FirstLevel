package com.example.akula.messagebox.messages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.akula.messagebox.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter  extends RecyclerView.Adapter{

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    public  static  final String DATE_TIME_FORMAT_HOUR_MONTH_YEAR= "h:mma EEE, MMM d, yyyy";
    private List<MOBInboxMessage> inbox_list;
    private  Context mContext;

    public boolean isActionModeEnabled() {
        return mActionModeEnabled;
    }

    public void setActionModeEnabled(boolean mActionModeEnabled) {
        this.mActionModeEnabled = mActionModeEnabled;
    }

    private boolean mActionModeEnabled;



    public List<MOBInboxMessage> getInbox_list() {
        return inbox_list;
    }

    public void setInbox_list(List<MOBInboxMessage> inbox_list) {
        this.inbox_list = inbox_list;
    }




    public MessagesAdapter(Context context,List<MOBInboxMessage> inbox_list){
        this.mContext = context;
        this.inbox_list = inbox_list;
    }




    public class MessageItemViewHolder extends ViewHolder {

        private TextView  message_body;
        private RelativeLayout iconContainer;
        public RelativeLayout viewForeground, viewBackground;
        private FrameLayout inbox_container;
        private LinearLayout messageContainer;
        private View iconBack, iconFront;


        public MessageItemViewHolder(View view) {
            super(view);

            viewForeground = view.findViewById(R.id.view_foreground);
            viewBackground = view.findViewById(R.id.view_background);
            message_body = view.findViewById(R.id.tview_body);
            messageContainer = view.findViewById(R.id.rl_notifications);
            inbox_container = view.findViewById(R.id.inbox_items_parent);
            iconContainer = view.findViewById(R.id.icon_container);
            iconFront = view.findViewById(R.id.iv_clear_state);
            iconBack = view.findViewById(R.id.iv_selected_state);

        }


       public void bindData(MOBInboxMessage message){
           message_body.setText(message.getBody());

           if (message.isSelected() && isActionModeEnabled()) {
               iconContainer.setVisibility(View.VISIBLE);
               iconFront.setVisibility(View.GONE);
               iconBack.setVisibility(View.VISIBLE);
           } else if (!message.isSelected() && isActionModeEnabled()) {
               iconContainer.setVisibility(View.VISIBLE);
               iconBack.setVisibility(View.GONE);
               iconFront.setVisibility(View.VISIBLE);
           } else {
               iconContainer.setVisibility(View.GONE);
               iconBack.setVisibility(View.GONE);
               iconFront.setVisibility(View.GONE);
           }

        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
            return new MessageItemViewHolder(itemView);
            } else if (viewType ==TYPE_FOOTER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(holder instanceof MessageItemViewHolder){
            MessageItemViewHolder itemViewHolder = (MessageItemViewHolder)holder;
            MOBInboxMessage inboxMessage = inbox_list.get(position);
            itemViewHolder.bindData(inboxMessage);
        } else if(holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder)holder;
            getLastRefreshedTime(footerViewHolder);

        }

    }

    @Override
    public int getItemCount() {
        return inbox_list==null ? 0 :inbox_list.size() +1;
    }

    @Override
    public int getItemViewType(int position) {
        return position ==inbox_list.size() ? TYPE_FOOTER:TYPE_ITEM;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView footerText;

        public FooterViewHolder(View itemView) {
            super(itemView);
            footerText = itemView.findViewById(R.id.homescreen_inbox_last_refreshed);
        }
    }


    public void getLastRefreshedTime(FooterViewHolder footerViewHolder) {
        Date mytime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_HOUR_MONTH_YEAR, Locale.US);
        footerViewHolder.footerText.setText("Last refreshed " + dateFormat.format(mytime));
    }


    public void removeData(int position) {
        inbox_list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(MOBInboxMessage item, int position) {
        inbox_list.add(position, item);
        notifyItemInserted(position);
    }
}
