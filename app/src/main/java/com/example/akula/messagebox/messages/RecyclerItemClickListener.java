package com.example.akula.messagebox.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {


    private onItemClickListener mListener;
    private GestureDetector mGestureDetector;


    public RecyclerItemClickListener(Context context,final  RecyclerView recyclerView,onItemClickListener listener){
        mListener = listener;
        mGestureDetector= new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null)
                {
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView  =view.findChildViewUnder(e.getX(),e.getY());
        if (childView !=null && mListener !=null && mGestureDetector.onTouchEvent(e)){
            mListener.onItemLongClick(childView,view.getChildPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static interface onItemClickListener{

        public void onItemClickListener(View view,int position);
        public void onItemLongClick(View view,int position);
    }

}
