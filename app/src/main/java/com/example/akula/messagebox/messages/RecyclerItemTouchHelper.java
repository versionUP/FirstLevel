package com.example.akula.messagebox.messages;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;

public class RecyclerItemTouchHelper extends SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener =listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null && viewHolder instanceof MessagesAdapter.MessageItemViewHolder){
            final View frontView =((MessagesAdapter.MessageItemViewHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(frontView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder !=null && viewHolder instanceof MessagesAdapter.MessageItemViewHolder){
            final View frontView =((MessagesAdapter.MessageItemViewHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c,recyclerView,frontView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof MessagesAdapter.FooterViewHolder) return 1;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((MessagesAdapter.MessageItemViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }


    public interface RecyclerItemTouchHelperListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder,int direction,int position);
    }
}
